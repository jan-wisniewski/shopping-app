package com.app.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import persistence.enums.Category;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateProductDto {
    private String name;
    private BigDecimal price;
    private Category category;
}
