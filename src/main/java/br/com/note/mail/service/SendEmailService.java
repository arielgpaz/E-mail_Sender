package br.com.note.mail.service;

import br.com.note.mail.domain.EmailInfo;
import br.com.note.mail.util.CsvToListConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender emailSender;

    public void send(InputStream inputStream) {

        try {
            List<EmailInfo> emailInfos = CsvToListConverter.convertCsvToList(inputStream);

            emailInfos.forEach(this::sendEmail);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(EmailInfo emailInfo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("emailnotas10@gmail.com");
        message.setTo(emailInfo.getStudent().getEmail());
        message.setSubject("Notas");
        message.setText(createEmailMessage(emailInfo.getGrades()));
        emailSender.send(message);
    }

    private String createEmailMessage(List<String> grades) {

        int gradesQtty = grades.size();

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < gradesQtty - 1; i++) {

            if (i == gradesQtty - 1) {
                body.append("Total: ").append(grades.get(gradesQtty - 1));
            }

            body.append("Nota ").append(i).append(": ").append(grades.get(i));
        }

        return body.toString();
    }
}
