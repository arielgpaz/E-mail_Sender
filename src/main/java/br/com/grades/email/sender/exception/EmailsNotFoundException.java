package br.com.grades.email.sender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailsNotFoundException extends RuntimeException {

    public EmailsNotFoundException(String message) {
        super(message);
    }
}
