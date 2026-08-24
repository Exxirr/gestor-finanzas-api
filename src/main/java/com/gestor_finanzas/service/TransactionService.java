package com.gestor_finanzas.service;

import com.gestor_finanzas.dto.PagedResponse;
import com.gestor_finanzas.dto.TransactionRequest;
import com.gestor_finanzas.dto.TransactionResponse;

public interface TransactionService {


    PagedResponse<TransactionResponse> findAllTransactions(int page, int size);


    TransactionResponse getTransactionById(Long id);


    TransactionResponse createTransaction(TransactionRequest transactionRequest);


    TransactionResponse updateTransaction(Long id, TransactionRequest updateTransactionRequest);


    void deleteTransactionById(Long id);

}
