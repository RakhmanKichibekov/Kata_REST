package ru.kata.spring.boot_security.demo.exception;

public class NotUpdatedException extends RuntimeException{
    public NotUpdatedException(String message) {
        super(message);
    }
}
