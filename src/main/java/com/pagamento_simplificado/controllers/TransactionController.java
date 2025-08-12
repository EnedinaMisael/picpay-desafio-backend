package com.pagamento_simplificado.controllers;

import com.pagamento_simplificado.domain.transactional.Transaction;
import com.pagamento_simplificado.dto.TransactionalDTO;
import com.pagamento_simplificado.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionalService transactionalService;

    @GetMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionalDTO transaction) throws Exception{
        Transaction newTransaction = this.transactionalService.createTransactional(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);


    }

}
