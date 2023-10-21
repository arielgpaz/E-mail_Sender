package br.com.grades.email.sender.controller;

import br.com.grades.email.sender.domain.EmailStatusCounter;
import br.com.grades.email.sender.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/grades")
@RequiredArgsConstructor
public class SendEmailController {

    private final SendEmailService sendEmailService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public EmailStatusCounter sendGrades(@RequestBody MultipartFile file, @RequestParam String emailSubject, @RequestParam(required = false) String additionalMessage) {

        try {
            return sendEmailService.send(file.getInputStream(), emailSubject, additionalMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
