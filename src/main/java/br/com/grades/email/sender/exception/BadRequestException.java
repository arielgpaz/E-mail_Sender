package br.com.grades.email.sender.exception;

import java.io.IOException;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, IOException e) {
        super(message, e);
    }
}
