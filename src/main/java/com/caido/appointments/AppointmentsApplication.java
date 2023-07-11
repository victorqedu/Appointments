package com.caido.appointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.caido.appointments.entity"})
public class AppointmentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppointmentsApplication.class, args);
    }
}
