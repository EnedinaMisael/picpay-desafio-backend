package com.pagamento_simplificado.repositories;

import com.pagamento_simplificado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    // optional pq pode ou nao ter um usuario cadastrado com o documento X
    // se segue um padrao no metodo o JPA MONTA A QUERY no BD
    // procura um USER By (pela) coluna Document
    Optional<User>findUserByDocument(String document);

    Optional<User>findUserById(Long id);


}
