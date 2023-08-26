package com.caido.appointments.config;

import com.caido.appointments.Util.Exceptions.InvalidCredentialsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * I no longer use this class, I needed it to implement custom error messages over Basic Auth when I got error 401 but I used instead a custom filter before authentication, I also don't think this is possible using this approach
 * Class could be deleted but I keep it to remind myself what I tried
 * AuthenticationEntryPoint is used to send an HTTP response that requests credentials from a client.
 * The AuthenticationEntryPoint will be called if the user requests a secure HTTP resource but they are not authenticated. 
 * An appropriate AuthenticationException or AccessDeniedException will be thrown by a security interceptor further down the call stack, triggering the commence method on the entry point.
 */
//@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("Commence "+authException.getClass());
        String message = "";
        if (authException instanceof InvalidCredentialsException) {
            message = "Invalid pass";
            System.out.println("Commence "+authException.getClass());
        } else if (authException instanceof BadCredentialsException) {
            message = "Invalid email";
        }         
        System.out.println("Commence: "+message);
        
        RestMessage re = new RestMessage(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed "+message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }
}