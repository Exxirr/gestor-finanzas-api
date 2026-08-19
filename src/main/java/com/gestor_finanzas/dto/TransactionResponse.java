package com.gestor_finanzas.dto;

import com.gestor_finanzas.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;

    private String name;

    private String description;

    private TransactionType type;

    private BigDecimal amount;

    private LocalDateTime date;

    private CategoryResponse category;

}
