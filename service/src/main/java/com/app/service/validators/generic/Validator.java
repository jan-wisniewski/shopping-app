package com.app.service.validators.generic;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T item);
}
