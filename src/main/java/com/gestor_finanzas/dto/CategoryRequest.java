package com.gestor_finanzas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "The category name is mandatory")
    private String name;

    private String description;

}
