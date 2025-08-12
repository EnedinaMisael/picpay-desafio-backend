package com.pagamento_simplificado.repositories;

import com.pagamento_simplificado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findUserByDocument(String document);

    Optional<User>findUserById(Long id);


}
