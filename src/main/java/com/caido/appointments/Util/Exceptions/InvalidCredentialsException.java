package com.caido.appointments.Util.Exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException  {

    public InvalidCredentialsException(String parola_invalida) {
        super(parola_invalida);
    }
    
}
