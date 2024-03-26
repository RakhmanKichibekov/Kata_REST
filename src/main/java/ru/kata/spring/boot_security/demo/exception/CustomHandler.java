package ru.kata.spring.boot_security.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomHandler {

    @ExceptionHandler
    public ResponseEntity<InfoHandler> handleException(NotCreatedException exception) {
        InfoHandler infoHandler = new InfoHandler(exception.getMessage());
        return new ResponseEntity<>(infoHandler, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler ResponseEntity<InfoHandler> handleException(NotUpdatedException exception) {
        InfoHandler infoHandler = new InfoHandler(exception.getMessage());
        return new ResponseEntity<>(infoHandler, HttpStatus.BAD_REQUEST);
    }
}
