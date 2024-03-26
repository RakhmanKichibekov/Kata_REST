package ru.kata.spring.boot_security.demo.exception;

public class NotCreatedException extends RuntimeException{
    public NotCreatedException(String message) {
        super(message);
    }
}
