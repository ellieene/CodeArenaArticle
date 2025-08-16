package com.example.article.exception;

/**
 * Исключение, дубликатов.
 */
public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
