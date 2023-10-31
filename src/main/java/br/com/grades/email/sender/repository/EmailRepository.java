package br.com.grades.email.sender.repository;

import br.com.grades.email.sender.domain.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long>, JpaSpecificationExecutor<EmailModel> {

}
