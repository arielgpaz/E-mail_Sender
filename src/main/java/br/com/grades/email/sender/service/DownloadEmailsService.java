package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.exception.NotFoundException;
import br.com.grades.email.sender.util.CsvConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DownloadEmailsService {

    private final EmailConsultsService emailConsultsService;

    public byte[] getCsvFile(String status, String to, LocalDateTime startDate, LocalDateTime endDate) {

        log.info("Buscando emails.");
        List<EmailModel> emails = emailConsultsService.getEmails(status, to, startDate, endDate);

        if (emails.isEmpty()) {
            log.error("Nenhum email encontrado");
            throw new NotFoundException("Nenhum email encontrado.");
        }

        return CsvConverter.convertListToCsv(emails);
    }
}
