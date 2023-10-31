package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.repository.EmailRepository;
import br.com.grades.email.sender.specifications.EmailSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailConsultsService {

    private final EmailRepository emailRepository;

    public List<EmailModel> getEmails(String status, String to, LocalDateTime startDate, LocalDateTime endDate) {
        return emailRepository.findAll(EmailSpecifications.orderByEmailTo(
                EmailSpecifications.byStatus(status)
                        .and(EmailSpecifications.byTo(to))
                        .and(EmailSpecifications.byStartDate(startDate))
                        .and(EmailSpecifications.byEndDate(endDate))
        ));
    }
}
