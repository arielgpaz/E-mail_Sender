package br.com.grades.email.sender.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class EmailsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<CustomError> handleInternalServerErrorsExceptions(RuntimeException e) {

        CustomError customError = buildCustomError(e, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customError);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomError> handleNotFoundExceptions(RuntimeException e) {

        CustomError customError = buildCustomError(e, HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomError> handleExceptions(RuntimeException e) {

        CustomError customError = buildCustomError(e, HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError);
    }

    private static CustomError buildCustomError(RuntimeException e, int status) {
        return CustomError.builder()
                .message(e.getMessage())
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
