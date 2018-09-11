package com.drprado.pontointeligente.domain.exceptions;

public class MyCustomException extends Error {
    public MyCustomException(String message) {
        super(message);
    }
}
