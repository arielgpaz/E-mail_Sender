package br.com.grades.email.sender.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEmail {

    SENT("Sent"),
    ERROR("Error");

    private final String value;

}
