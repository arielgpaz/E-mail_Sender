package br.com.grades.email.sender.controller;

import br.com.grades.email.sender.domain.EmailStatusCounter;
import br.com.grades.email.sender.exception.EmailSenderException;
import br.com.grades.email.sender.service.DownloadEmailsService;
import br.com.grades.email.sender.service.SendEmailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping(value = "/grades")
@RequiredArgsConstructor
public class EmailFilesController {

    private final SendEmailsService sendEmailsService;
    private final DownloadEmailsService downloadEmailsService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<EmailStatusCounter> sendGrades(@RequestBody MultipartFile file, @RequestParam String emailSubject, @RequestParam(required = false) String additionalMessage) {

        try {

            EmailStatusCounter counter = sendEmailsService.send(file.getInputStream(), emailSubject, additionalMessage);

            return ResponseEntity.ok(counter);

        } catch (IOException e) {
            log.info("Falha ao ler o arquivo enviado.", e);
            throw new EmailSenderException("Falha ao ler o arquivo enviado.", e);
        }
    }

    @GetMapping(value = "/download", produces = "text/csv")
    public ResponseEntity<byte[]> getGradesCsv(@RequestParam(required = false) String status,
                                               @RequestParam(required = false) LocalDateTime startDate,
                                               @RequestParam(required = false) LocalDateTime endDate) {

        byte[] file = downloadEmailsService.getCsvFile(status, startDate, endDate);

        return ResponseEntity.ok(file);
    }
}
