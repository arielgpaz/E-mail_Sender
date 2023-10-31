package br.com.grades.email.sender.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_EMAIL")
public class EmailModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -5302985991330725118L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;

    private String emailFrom;

    private String emailTo;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime sendDateEmail;

    private String statusEmail;
}
