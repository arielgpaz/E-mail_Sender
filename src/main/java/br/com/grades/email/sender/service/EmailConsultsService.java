package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailConsultsService {

    private final EmailRepository emailRepository;

    public List<EmailModel> getEmailsByStatus(String status, LocalDateTime startDate, LocalDateTime endDate) {
        return emailRepository.findByStatus(
                status, startDate, endDate);
    }

    public List<EmailModel> getEmailsTo(String to, LocalDateTime startDate, LocalDateTime endDate) {
        return emailRepository.findByEmailTo(
                to, startDate, endDate);
    }
}
