package br.com.grades.email.sender.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class EmailResponse {

    private Long id;
    private String sendFrom;
    private String sendTo;
    private String subject;
    private String text;
    private String sendDate;
    private String status;

    public static EmailResponse modelToResopnse(EmailModel model) {
        return EmailResponse.builder()
                .id(model.getId())
                .sendFrom(model.getSendFrom())
                .sendTo(model.getSendTo())
                .subject(model.getSubject())
                .text(model.getText())
                .sendDate(formatDate(model.getSendDate()))
                .status(transformStatus(model.getStatus()))
                .build();
    }

    private static String formatDate(LocalDateTime sendDate) {
        var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return sendDate.format(dateFormat);
    }

    private static String transformStatus(String status) {
       if (StatusEmail.SENT.name().equals(status)) {
           return "Enviado";
       } else {
           return "Erro";
       }
    }


}
