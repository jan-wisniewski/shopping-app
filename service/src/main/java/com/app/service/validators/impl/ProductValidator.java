package com.app.service.validators.impl;

import com.app.service.dto.CreateProductDto;
import com.app.service.exceptions.ServiceException;
import com.app.service.validators.generic.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductValidator implements Validator<CreateProductDto> {
    @Override
    public Map<String, String> validate(CreateProductDto item) {
        Map<String, String> errors = new HashMap<>();
        if (!isNameUppercase(item.getName())) {
            errors.put("Field", "Name should be uppercase");
        }
        if (!isPricePositive(item.getPrice())) {
            errors.put("Field", "Price should be positive");
        }
        return errors;
    }

    private boolean isNameUppercase(String name) {
        if (name == null) {
            throw new ServiceException("Name is null");
        }
        return name.matches("[A-Z]+");
    }

    private boolean isPricePositive(BigDecimal price) {
        if (price == null) {
            throw new ServiceException("Price is null");
        }
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

}
