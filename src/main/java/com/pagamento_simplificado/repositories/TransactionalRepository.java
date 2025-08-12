package com.pagamento_simplificado.repositories;

import com.pagamento_simplificado.domain.transactional.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionalRepository extends JpaRepository<Transaction, Long> {
}
