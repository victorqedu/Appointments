package com.caido.appointments.Util.Exceptions;

public class RootExceptionHandler extends RuntimeException {

    public RootExceptionHandler(String message) {
        super(message);
    }
    
    public RootExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    } 
    
} 