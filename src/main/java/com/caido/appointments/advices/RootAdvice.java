package com.caido.appointments.advices;

import com.caido.appointments.Util.Exceptions.RootException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RootAdvice  {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception e, WebRequest request) {
        System.out.println("handlingRootException "+e);
        RootException re = new RootException(
                e.getMessage()+"["+e.toString()+"]",
                e,
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}