package br.com.grades.email.sender.controller;

import br.com.grades.email.sender.domain.EmailResponse;
import br.com.grades.email.sender.service.EmailConsultsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/email")
public class EmailConsultsController {

    private final EmailConsultsService emailConsultsService;

    @GetMapping
    public ResponseEntity<List<EmailResponse>> getEmails(@RequestParam(required = false) List<Long> ids,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String to,
                                                         @RequestParam(required = false) LocalDateTime startDate,
                                                         @RequestParam(required = false) LocalDateTime endDate) {
        log.info("Consultando emails");

        List<EmailResponse> emailsByStatus = emailConsultsService.getEmails(ids, status, to, startDate, endDate);

        return ResponseEntity.ok(emailsByStatus);
    }

}
