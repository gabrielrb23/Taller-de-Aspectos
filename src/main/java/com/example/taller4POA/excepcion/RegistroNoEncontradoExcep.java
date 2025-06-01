package com.example.taller4POA.excepcion;


public class RegistroNoEncontradoExcep extends RuntimeException{
    private final String userMessage;
    private final String errorCode;

    public RegistroNoEncontradoExcep(String userMessage, String errorCode, Throwable cause) {
        super(userMessage, cause);
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }


    // Getters
    public String getUserMessage() { return userMessage; }
    public String getErrorCode() { return errorCode; }
}
