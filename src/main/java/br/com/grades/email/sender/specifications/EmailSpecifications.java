package br.com.grades.email.sender.specifications;

import br.com.grades.email.sender.domain.EmailModel;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EmailSpecifications {

    public static Specification<EmailModel> byStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (isNotBlank(status)) {
                return criteriaBuilder.equal(root.get("statusEmail"), status.toUpperCase());
            }
            return null;
        };
    }

    public static Specification<EmailModel> byTo(String to) {
        return (root, query, criteriaBuilder) -> {
            if (isNotBlank(to)) {
                return criteriaBuilder.equal(root.get("emailTo"), to);
            }
            return null;
        };
    }

    public static Specification<EmailModel> byStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) -> {
            if (nonNull(startDate)) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("sendDateEmail"), startDate);
            }
            return null;
        };
    }

    public static Specification<EmailModel> byEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (nonNull(endDate)) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("sendDateEmail"), endDate);
            }
            return null;
        };
    }

    public static Specification<EmailModel> orderByEmailTo(Specification<EmailModel> spec) {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("emailTo")));
            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }
}
