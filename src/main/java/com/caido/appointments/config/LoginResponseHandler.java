package com.caido.appointments.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * I no longer use this class, I needed it to implement custom error messages over Basic Auth when I got error 401 but I used instead a custom filter before authentication, I also don't think this is possible using this approach
 * Class could be deleted but I keep it to remind myself what I tried
 */
//@Component
public class LoginResponseHandler implements AuthenticationFailureHandler { 

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("Start onAuthenticationFailure");
    }
}
