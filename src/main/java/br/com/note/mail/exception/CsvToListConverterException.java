package br.com.note.mail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CsvToListConverterException extends RuntimeException {

    public CsvToListConverterException(IOException e) {
        super("Falha ao extrair dados da planilha", e);
    }
}
