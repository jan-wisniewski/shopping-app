package com.app.service.validators.impl;

import com.app.service.dto.CreateCustomerDto;
import com.app.service.validators.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class CustomerValidator implements Validator<CreateCustomerDto> {

    @Override
    public Map<String, String> validate(CreateCustomerDto item) {
        Map<String, String> errors = new HashMap<>();
        if (isFieldUppercase(item.getName())) {
            errors.put("Field", "Name is not uppercase");
        }
        if (isFieldUppercase(item.getSurname())) {
            errors.put("Field", "Surname is not uppercase");
        }
        if (!isAgeHigherOrEqual18(item.getAge())) {
            errors.put("Field", "Age is < 18");
        }
        if (!isValidEmail(item.getEmail())) {
            errors.put("Field", "E-mail is not valid");
        }
        return errors;
    }

    private boolean isFieldUppercase(String name) {
        return !name.matches("([A-Z]+ ?)+");
    }

    private boolean isAgeHigherOrEqual18(int age) {
        return age >= 18;
    }

    private boolean isValidEmail(String email) {
        return EmailValidator.validate(email);
    }

}
