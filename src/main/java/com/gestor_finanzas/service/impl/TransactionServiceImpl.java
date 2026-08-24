package com.gestor_finanzas.service.impl;

import com.gestor_finanzas.dto.*;
import com.gestor_finanzas.exception.ResourceNotFoundException;
import com.gestor_finanzas.model.Category;
import com.gestor_finanzas.model.Transaction;
import com.gestor_finanzas.repository.CategoryRepository;
import com.gestor_finanzas.repository.TransactionRepository;
import com.gestor_finanzas.service.TransactionService;
import com.gestor_finanzas.specification.TransactionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PagedResponse<TransactionResponse> findAllTransactions(int page, int size, String sortBy, String sortDir, TransactionFilter filter) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Transaction> spec = TransactionSpecification.getSpecifications(filter);

        Page<Transaction> transactionsPage = transactionRepository.findAll(spec, pageable);

        List<TransactionResponse> content = transactionsPage
                .getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return PagedResponse
                .<TransactionResponse>builder()
                .content(content)
                .pageNumber(transactionsPage.getNumber())
                .pageSize(transactionsPage.getSize())
                .totalElements(transactionsPage.getTotalElements())
                .totalPages(transactionsPage.getTotalPages())
                .last(transactionsPage.isLast())
                .build();

    }

    @Override
    public TransactionResponse getTransactionById(Long id) {

        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        return mapToResponse(transaction);
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        Transaction transactionEntity = mapToEntity(transactionRequest);

        Transaction saveTransaction = transactionRepository.save(transactionEntity);

        return mapToResponse(saveTransaction);
    }

    @Override
    public TransactionResponse updateTransaction(Long id, TransactionRequest updateTransactionRequest) {

        Transaction transactionExists = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        updateEntityFromRequest(transactionExists, updateTransactionRequest);

        Transaction updateTransaction = transactionRepository.save(transactionExists);

        return mapToResponse(updateTransaction);
    }

    @Override
    public void deleteTransactionById(Long id) {

        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);

    }

    private Transaction mapToEntity(TransactionRequest transactionRequest) {

        Category category = categoryRepository.findById(transactionRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + transactionRequest.getCategoryId()));

        Transaction transaction = new Transaction();
        transaction.setName(transactionRequest.getName());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());
        transaction.setCategory(category);

        return transaction;

    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setId(transaction.getId());
        transactionResponse.setName(transaction.getName());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setDate(transaction.getDate());

        if (transaction.getCategory() != null) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(transaction.getCategory().getId());
            categoryResponse.setName(transaction.getCategory().getName());
            categoryResponse.setDescription(transaction.getCategory().getDescription());

            transactionResponse.setCategory(categoryResponse);
        }


        return transactionResponse;
    }

    private void updateEntityFromRequest(Transaction entity, TransactionRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + request.getCategoryId()));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setType(request.getType());
        entity.setAmount(request.getAmount());
        entity.setDate(request.getDate());
        entity.setCategory(category);

    }
}
