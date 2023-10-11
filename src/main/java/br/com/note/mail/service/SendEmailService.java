package br.com.note.mail.service;

import br.com.note.mail.domain.EmailInfo;
import br.com.note.mail.util.CsvToListConverter;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    public void send(InputStream inputStream) {

        try {
            List<EmailInfo> emailInfos = CsvToListConverter.convertCsvToList(inputStream);

            emailInfos.forEach(this::sendEmail);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendEmail(EmailInfo emailInfo) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("emailnotas10@gmail.com",
                                "EmailNotas10");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("emailnotas10@gmail.com"));

            Address to = new InternetAddress(emailInfo.getStudent().getEmail());

            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject("Notas!");
            message.setText(this.createEmailMessage(emailInfo.getGrades()));

            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
