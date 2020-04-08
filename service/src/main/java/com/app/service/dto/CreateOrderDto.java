package com.app.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import persistence.models.Customer;
import persistence.models.Product;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateOrderDto {
    private Customer customer;
    private Product product;
    private int quantity;
    private LocalDate orderDate;
}
