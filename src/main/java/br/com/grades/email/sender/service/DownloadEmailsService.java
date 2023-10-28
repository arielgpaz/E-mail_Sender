package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.exception.EmailsNotFoundException;
import br.com.grades.email.sender.repository.EmailRepository;
import br.com.grades.email.sender.util.CsvConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadEmailsService {

    private final EmailRepository emailRepository;

    public byte[] getCsvFile(String status, LocalDateTime startDate, LocalDateTime endDate) {

        List<EmailModel> emails = emailRepository.findByStatus(status, startDate, endDate);

        if (emails.isEmpty()) {
            throw new EmailsNotFoundException("Nenhum email encontrado");
        }

        return CsvConverter.convertListToCsv(emails);
    }
}
