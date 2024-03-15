package com.jpa_audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class JpaAuditApplication {
    public static void main(String[] args) {
        SpringApplication.run(JpaAuditApplication.class, args);
    }
}
