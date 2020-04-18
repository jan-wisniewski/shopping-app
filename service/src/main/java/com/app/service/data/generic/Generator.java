package com.app.service.data.generic;

public interface Generator<T> {
    T generate(int size);
}
