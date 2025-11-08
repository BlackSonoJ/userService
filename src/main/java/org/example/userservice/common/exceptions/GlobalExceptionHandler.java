package org.example.userservice.common.exceptions;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GlobalExceptionHandler {
    public void handle(Exception e) {
        if (e instanceof AppException) {
            System.err.println("App Exception: " + e.getMessage());
        } else {
            System.err.println("Unexpected Exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
