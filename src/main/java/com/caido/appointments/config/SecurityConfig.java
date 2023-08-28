package com.caido.appointments.config;

import com.caido.appointments.filters.CustomAccountChecksFilter;
import com.caido.appointments.filters.JWTTokenValidatorFilter;
import com.caido.appointments.filters.JWTTokenGeneratorFilter;
import com.caido.appointments.repositories.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {
    //I will no longer use the loginResponseHandler as it seems is not working with simple basic auth, I need to enable the formLogin that I don't need
    //@Autowired
    //LoginResponseHandler loginResponseHandler;

    @Autowired
    PersonRepository personRepository;

    //I will no loger use the authEntryPoint, I will create a new filter to check the account before BasicAuthentication
    //@Autowired
    //@Qualifier("customAuthenticationEntryPoint")
    //AuthenticationEntryPoint authEntryPoint;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource((HttpServletRequest request) -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://192.168.88.105:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(Arrays.asList("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                    }))
                .authorizeHttpRequests(
                    (requests)->requests
                        .requestMatchers("/api/localcompany", "/api/register", "/api/login", "/api/termsAndConditions", "/api/policyOfConfidentiality").permitAll()
                        .anyRequest().authenticated()
                )
                
                .addFilterBefore(new CustomAccountChecksFilter(personRepository), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(personRepository), BasicAuthenticationFilter.class)
//                .formLogin().successHandler(loginResponseHandler)
//                .formLogin(Customizer.withDefaults())
//                .formLogin(formLogin -> formLogin
//                    .permitAll()
//                    .successHandler(loginResponseHandler)
//                    .failureHandler(loginResponseHandler)
//                )
                //.exceptionHandling(attemptAuthentication -> attemptAuthentication.authenticationEntryPoint(authEntryPoint))
                .httpBasic(Customizer.withDefaults())
                ;
        return http.build();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
