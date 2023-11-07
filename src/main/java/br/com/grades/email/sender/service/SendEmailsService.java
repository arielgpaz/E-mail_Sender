package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailInfo;
import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.domain.EmailStatusCounter;
import br.com.grades.email.sender.domain.StatusEmail;
import br.com.grades.email.sender.repository.EmailRepository;
import br.com.grades.email.sender.util.CsvConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendEmailsService {

    private final JavaMailSender emailSender;
    private final EmailRepository emailRepository;

    private static final String FROM = "emailnotas10@gmail.com";

    public EmailStatusCounter send(InputStream inputStream, String emailSubject, String additionalMessage) {

        log.info("Convertendo arquivo csv em uma lista.");
        List<EmailInfo> emailInfos = CsvConverter.convertCsvToList(inputStream);

        log.info("Construindo objetos de emails.");
        List<EmailModel> emailsModel = this.buildEmailsModels(emailSubject, additionalMessage, emailInfos);

        return this.sendEmails(emailsModel);
    }

    private List<EmailModel> buildEmailsModels(String emailSubject, String additionalMessage, List<EmailInfo> emailInfos) {

        List<EmailModel> emailsModel = new ArrayList<>();

        List<String> emailHeaders = this.getEmaiHeadersAndRemoveFromList(emailInfos);

        emailInfos.forEach(emailInfo -> {
            EmailModel emailModel = EmailModel.builder()
                    .sendFrom(FROM)
                    .sendTo(emailInfo.getStudent().getEmail())
                    .subject(emailSubject + " - " + emailInfo.getStudent().getName())
                    .text(this.createEmailMessage(emailInfo.getGrades(), emailHeaders, additionalMessage))
                    .sendDate(LocalDateTime.now())
                    .build();
            emailsModel.add(emailModel);
        });

        return emailsModel;
    }

    private List<String> getEmaiHeadersAndRemoveFromList(List<EmailInfo> emailInfos) {
        List<String> emailHeaders = emailInfos.get(0).getGrades();
        emailInfos.remove(0);
        return emailHeaders;
    }

    private String createEmailMessage(List<String> grades, List<String> emailHeaders, String additionalMessage) {

        var body = nonNull(additionalMessage) ? new StringBuilder(additionalMessage + "\n\n") : new StringBuilder();

        IntStream.range(0, grades.size())
                .forEach(i -> body.append(emailHeaders.get(i))
                        .append(": ")
                        .append(grades.get(i))
                        .append("\n"));

        return body.toString();
    }

    private EmailStatusCounter sendEmails(List<EmailModel> emails) {
        log.info("Enviando emails.");

        EmailStatusCounter counter = new EmailStatusCounter();
        counter.setTotal(emails.size());

        emails.forEach(email -> {

            SimpleMailMessage message = getMessage(email);

            try {
                emailSender.send(message);
                email.setStatus(StatusEmail.SENT.name());
                counter.setSent(counter.getSent() + 1);
            } catch (MailException e) {
                log.error("Não foi possível enviar o email para {}.", email.getSendTo(), e);
                email.setStatus(StatusEmail.ERROR.name());
                counter.setError(counter.getError() + 1);
            } finally {
                emailRepository.save(email);
            }
        });

        return counter;
    }

    private static SimpleMailMessage getMessage(EmailModel email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getSendFrom());
        message.setTo(email.getSendTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());
        return message;
    }
}
