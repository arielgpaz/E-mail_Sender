package br.com.grades.email.sender.exception;

import java.io.IOException;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message, IOException e) {
        super(message, e);
    }
}
