package com.example.demo.Exception;

public class CustomizeException extends RuntimeException{
    private int code;
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public CustomizeException(CustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
