package ru.kata.spring.boot_security.demo.exception;

import org.springframework.stereotype.Component;

@Component
public class InfoHandler {
    private String info;

    public InfoHandler(String info) {
        this.info = info;
    }

    public InfoHandler() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
