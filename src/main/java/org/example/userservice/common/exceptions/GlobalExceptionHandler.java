package org.example.userservice.common.exceptions;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public void handle(Exception e) {
        if (e instanceof AppException) {
            logger.error("App Exception: {}", e.getMessage());
        } else {
            logger.error("Unexpected Exception: {}: {} {}", e.getClass().getSimpleName(), e.getMessage(), e.getStackTrace());
        }
    }
}
