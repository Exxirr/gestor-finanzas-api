package com.gestor_finanzas.service;

import com.gestor_finanzas.dto.TransactionRequest;
import com.gestor_finanzas.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {

    //CRUD
    //TODAS LAS TRANSACCIONES
    List<TransactionResponse> findAllTransactions();

    //BUSCO UNA  TRANSACCION
    TransactionResponse getTransactionById(Long id);

    //CREAR UNA  TRANSACCION
    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    //ACTUALIZO UNA  TRANSACCION
    TransactionResponse updateTransaction(Long id, TransactionRequest updateTransactionRequest);

    //ELIMINO UNA  TRANSACCION
    void deleteTransactionById(Long id);

}
