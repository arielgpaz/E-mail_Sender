package br.com.grades.email.sender.service;

import br.com.grades.email.sender.domain.EmailInfo;
import br.com.grades.email.sender.util.CsvToListConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender emailSender;

    private static final String FROM = "emailnotas10@gmail.com";

    public void send(InputStream inputStream, String emailSubject, String additionalMessage) {

        try {
            List<EmailInfo> emailInfos = CsvToListConverter.convertCsvToList(inputStream);
            
            List<String> emailHeaders = this.getEmaiHeadersAndRemoveFromList(emailInfos);

            emailInfos.forEach(emailInfo -> this.createEmailAndSend(emailInfo, emailSubject, emailHeaders, additionalMessage));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getEmaiHeadersAndRemoveFromList(List<EmailInfo> emailInfos) {
        List<String> emailHeaders = emailInfos.get(0).getGrades();
        emailInfos.remove(0);
        return emailHeaders;
    }

    private void createEmailAndSend(EmailInfo emailInfo, String emailSubject, List<String> emailHeaders, String additionalMessage) {
        
        String subject = emailSubject + " - " + emailInfo.getStudent().getName();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(emailInfo.getStudent().getEmail());
        message.setSubject(subject);
        message.setText(this.createEmailMessage(emailInfo.getGrades(), emailHeaders, additionalMessage));
        
        emailSender.send(message);
    }

    private String createEmailMessage(List<String> grades, List<String> emailHeaders, String additionalMessage) {

        StringBuilder body = new StringBuilder(additionalMessage + "\n\n");

        IntStream.range(0, grades.size())
                .forEach(i -> body.append(emailHeaders.get(i))
                        .append(": ")
                        .append(grades.get(i))
                        .append("\n"));

        return body.toString();
    }
}
