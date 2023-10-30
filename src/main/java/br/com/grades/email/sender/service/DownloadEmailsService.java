package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.exception.NotFoundException;
import br.com.grades.email.sender.repository.EmailRepository;
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

    private final EmailRepository emailRepository;

    public byte[] getCsvFile(String status, LocalDateTime startDate, LocalDateTime endDate) {

        log.info("Buscando emails com status {}, enviados entre {} e {}.", status, startDate, endDate);
        List<EmailModel> emails = emailRepository.findByStatus(status, startDate, endDate);

        if (emails.isEmpty()) {
            log.error("Nenhum email encontrado");
            throw new NotFoundException("Nenhum email encontrado.");
        }

        return CsvConverter.convertListToCsv(emails);
    }
}
