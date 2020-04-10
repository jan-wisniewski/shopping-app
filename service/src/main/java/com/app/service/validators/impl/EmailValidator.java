package com.app.service.validators.impl;

import com.app.service.exceptions.ServiceException;

public class EmailValidator {
    private EmailValidator() {}

    public static boolean validate(String email) {
        if (email == null) {
            throw new ServiceException("Email string is null");
        }
        return email.matches("[A-Za-z0-9]+@[a-z0-9]+.(pl|com|net|com.pl)");
    }
}
