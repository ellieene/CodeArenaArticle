package com.example.article.exception;

/**
 * Исключение, для обозначения повторения логина
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
