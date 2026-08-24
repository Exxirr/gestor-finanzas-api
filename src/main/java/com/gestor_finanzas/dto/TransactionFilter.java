package com.gestor_finanzas.dto;

import com.gestor_finanzas.model.TransactionType;
import lombok.Data;

@Data
public class TransactionFilter {

    private TransactionType type;
    private Long categoryId;

}
