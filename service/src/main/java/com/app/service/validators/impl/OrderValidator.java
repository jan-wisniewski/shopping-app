package com.app.service.validators.impl;

import com.app.service.dto.CreateOrderDto;
import com.app.service.validators.generic.Validator;
import persistence.models.Customer;
import persistence.models.Product;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderValidator implements Validator<CreateOrderDto> {
    @Override
    public Map<String, String> validate(CreateOrderDto item) {
        Map<String, String> errors = new HashMap<>();

        if (!isCustomerNotNull(item.getCustomer())){
            errors.put("Field", "Customer is null");
        }
        if (!isProductNotNull(item.getProduct())){
            errors.put("Field", "Product is null");
        }
        if (!isCorrectDate(item.getOrderDate())){
            errors.put("Field", "Order date is from the past");
        }
        if (!isCorrectQuantity(item.getQuantity())){
            errors.put("Field", "Quantity is negative or is equal zero");
        }
        return errors;
    }

    private boolean isCustomerNotNull(Customer customer) {
        return customer != null;
    }

    private boolean isProductNotNull(Product product) {
        return product != null;
    }

    private boolean isCorrectQuantity(int quantity) {
        return quantity > 0;
    }

    private boolean isCorrectDate(LocalDate localDate) {
        return localDate.isAfter(LocalDate.now()) ||
                localDate.isEqual(LocalDate.now());
    }

}
