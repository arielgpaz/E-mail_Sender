package br.com.note.mail.controller;

import br.com.note.mail.service.SendEmailService;
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
    public void sendGrades(@RequestBody MultipartFile file, @RequestParam String emailSubject, @RequestParam(required = false) String additionalMessage) {

        try {
            sendEmailService.send(file.getInputStream(), emailSubject, additionalMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
