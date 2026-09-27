package com.example.CafeManagement.Exception;

public class InvalidDateException extends RuntimeException {
    String message;
    String fieldName;
    public InvalidDateException(String fieldName,String message) {
        super(message);
        this.message = message;
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getFieldName() {
        return fieldName;
    }
}
