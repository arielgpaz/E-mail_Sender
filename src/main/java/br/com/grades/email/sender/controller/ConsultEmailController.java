package br.com.grades.email.sender.controller;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.service.ConsultEmailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/email")
public class ConsultEmailController {

    private final ConsultEmailsService consultEmailsService;

    @GetMapping(value = "/status")
    public List<EmailModel> getEmailsByStatus(@RequestParam(required = false) String status,
                                              @RequestParam(required = false) LocalDateTime startDate,
                                              @RequestParam(required = false) LocalDateTime endDate) {
        return consultEmailsService.getEmailsByStatus(status, startDate, endDate);
    }

    @GetMapping(value = "/to")
    public List<EmailModel> getEmailsTo(@RequestParam(required = false) String to,
                                        @RequestParam(required = false) LocalDateTime startDate,
                                        @RequestParam(required = false) LocalDateTime endDate) {
        return consultEmailsService.getEmailsTo(to, startDate, endDate);
    }

}
