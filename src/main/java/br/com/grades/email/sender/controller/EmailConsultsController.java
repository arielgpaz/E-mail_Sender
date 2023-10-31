package br.com.grades.email.sender.controller;

import br.com.grades.email.sender.domain.EmailModel;
import br.com.grades.email.sender.service.EmailConsultsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/email")
public class EmailConsultsController {

    private final EmailConsultsService emailConsultsService;

    @GetMapping
    public ResponseEntity<List<EmailModel>> getEmails(@RequestParam(required = false) String status,
                                                      @RequestParam(required = false) String to,
                                                      @RequestParam(required = false) LocalDateTime startDate,
                                                      @RequestParam(required = false) LocalDateTime endDate) {
        log.info("Consultando emails");

        List<EmailModel> emailsByStatus = emailConsultsService.getEmails(status, to, startDate, endDate);

        return ResponseEntity.ok(emailsByStatus);
    }

}
