package com.gestor_finanzas.dto;

import com.gestor_finanzas.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "The name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "The type is mandatory")
    private TransactionType type;

    @Schema(description = "Amount of the transaction", example = "150.50")
    @NotNull(message = "The amount is mandatory")
    @Positive(message = "The amount must be greater than zero ")
    private BigDecimal amount;

    @Schema(description = "Type of transaction", example = "EXPENSE")
    @NotNull(message = "The date is mandatory")
    @PastOrPresent(message = "The date cannot be in the future")
    private LocalDateTime date;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

}
