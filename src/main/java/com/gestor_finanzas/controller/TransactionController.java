package com.gestor_finanzas.controller;

import com.gestor_finanzas.dto.*;
import com.gestor_finanzas.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transactions", description = "Controller for managing income and expense transactions")
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TransactionResponse>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            TransactionFilter filter
    ) {

        PagedResponse<TransactionResponse> response = transactionService.findAllTransactions(page, size, sortBy, sortDir, filter);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {

        TransactionResponse transaction = transactionService.getTransactionById(id);

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/summary")
    public ResponseEntity<TransactionSummaryResponse> getTransactionSummary() {
        return ResponseEntity.ok(transactionService.getTransactionSummary());
    }

    @Operation(summary = "Create a new transaction", description = "Saves a new transaction into the system with validation")
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {

        TransactionResponse newTransaction = transactionService.createTransaction(transactionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionRequest transactionRequest) {

        TransactionResponse updateTransaction = transactionService.updateTransaction(id, transactionRequest);

        return ResponseEntity.ok(updateTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {

        transactionService.deleteTransactionById(id);

        return ResponseEntity.noContent().build();
    }

}
