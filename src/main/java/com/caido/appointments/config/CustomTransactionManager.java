package com.caido.appointments.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

@Component
@Primary
@Qualifier(value = "transactionManager")
public class CustomTransactionManager extends JpaTransactionManager {
    @Autowired
    private EntityManager entityManager;

    @Override
    protected void prepareSynchronization(DefaultTransactionStatus status, TransactionDefinition definition) {
        super.prepareSynchronization(status, definition);
        if (status.isNewTransaction()) {
            entityManager.createNativeQuery("SET caido.user_id=1;").executeUpdate();
        }
    }
}