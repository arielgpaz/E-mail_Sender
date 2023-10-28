package br.com.grades.email.sender.repository;

import br.com.grades.email.sender.domain.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long> {

    @Query("""
            select e from EmailModel e
            where upper(e.statusEmail) = upper(:statusEmail)
                and e.sendDateEmail between :sendDateEmailStart
                                        and :sendDateEmailEnd
            order by e.emailTo
            """)
    List<EmailModel> findByStatus(
            @Nullable @Param("statusEmail") String statusEmail,
            @Nullable @Param("sendDateEmailStart") LocalDateTime sendDateEmailStart,
            @Nullable @Param("sendDateEmailEnd") LocalDateTime sendDateEmailEnd);

    @Query("""
            select e from EmailModel e
            where upper(e.emailTo) = upper(:emailTo)
                and e.sendDateEmail between :sendDateEmailStart
                                        and :sendDateEmailEnd
            order by e.emailTo
            """)
    List<EmailModel> findByEmailTo(
            @Nullable @Param("emailTo") String emailTo,
            @Nullable @Param("sendDateEmailStart") LocalDateTime sendDateEmailStart,
            @Nullable @Param("sendDateEmailEnd") LocalDateTime sendDateEmailEnd);

}
