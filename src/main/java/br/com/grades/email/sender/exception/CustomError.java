package br.com.grades.email.sender.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {

    private String message;

    private Integer status;

    private LocalDateTime timestamp;
}
