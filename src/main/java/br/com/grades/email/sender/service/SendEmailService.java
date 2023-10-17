package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailInfo;
import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.domain.StatusEmail;
import br.com.grades.email.sender.repository.EmailRepository;
import br.com.grades.email.sender.util.CsvToListConverter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender emailSender;
    private final EmailRepository emailRepository;

    private static final String FROM = "emailnotas10@gmail.com";

    public void send(InputStream inputStream, String emailSubject, String additionalMessage) {

        List<EmailInfo> emailInfos = CsvToListConverter.convertCsvToList(inputStream);

        List<EmailModel> emailsModel = this.buildEmailsModels(emailSubject, additionalMessage, emailInfos);

        this.sendEmails(emailsModel);
    }

    private List<EmailModel> buildEmailsModels(String emailSubject, String additionalMessage, List<EmailInfo> emailInfos) {

        List<EmailModel> emailsModel = new ArrayList<>();

        List<String> emailHeaders = this.getEmaiHeadersAndRemoveFromList(emailInfos);

        emailInfos.forEach(emailInfo -> {
            EmailModel emailModel = EmailModel.builder()
                    .emailFrom(FROM)
                    .emailTo(emailInfo.getStudent().getEmail())
                    .subject(emailSubject + " - " + emailInfo.getStudent().getName())
                    .text(this.createEmailMessage(emailInfo.getGrades(), emailHeaders, additionalMessage))
                    .sendDateEmail(LocalDateTime.now())
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

    private void sendEmails(List<EmailModel> emails) {

        emails.forEach(email -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            try {
                emailSender.send(message);
                email.setStatusEmail(StatusEmail.SENT.getValue());
            } catch (MailException e) {
                email.setStatusEmail(StatusEmail.ERROR.getValue());
            } finally {
                emailRepository.save(email);
            }
        });
    }
}
