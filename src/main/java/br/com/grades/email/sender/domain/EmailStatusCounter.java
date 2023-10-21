package br.com.grades.email.sender.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailStatusCounter {

    private Integer total = 0;

    private Integer sent = 0;

    private Integer error = 0;
}
