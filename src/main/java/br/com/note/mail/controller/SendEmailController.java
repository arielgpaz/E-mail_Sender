package br.com.note.mail.controller;

import br.com.note.mail.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/grades")
@RequiredArgsConstructor
public class SendEmailController {

    private final SendEmailService sendEmailService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void sendGrades(@RequestBody MultipartFile file) {

        try {
            sendEmailService.send(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
