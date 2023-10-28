package br.com.grades.email.sender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSenderException extends RuntimeException {

    public EmailSenderException(String message, IOException e) {
        super(message, e);
    }
}
