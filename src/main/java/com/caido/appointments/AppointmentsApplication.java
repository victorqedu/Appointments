package com.caido.appointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.caido.appointments.*")
public class AppointmentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppointmentsApplication.class, args);
    }
}
