package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.domain.EmailResponse;
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

    public List<EmailResponse> getEmails(List<Long> ids, String status, String to, LocalDateTime startDate, LocalDateTime endDate) {
        List<EmailModel> emails = emailRepository.findAll(EmailSpecifications.orderByEmailTo(
                EmailSpecifications
                        .byIds(ids)
                        .and(EmailSpecifications.byStatus(status))
                        .and(EmailSpecifications.byTo(to))
                        .and(EmailSpecifications.byStartDate(startDate))
                        .and(EmailSpecifications.byEndDate(endDate))
        ));

        return emails.stream()
                .map(EmailResponse::modelToResopnse)
                .toList();
    }
}
