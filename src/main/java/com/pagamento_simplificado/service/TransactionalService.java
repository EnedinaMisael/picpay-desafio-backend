package com.pagamento_simplificado.service;

import com.pagamento_simplificado.domain.User;
import com.pagamento_simplificado.dto.TransactionalDTO;
import com.pagamento_simplificado.domain.transactional.Transaction;
import com.pagamento_simplificado.repositories.TransactionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionalService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionalRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Transaction createTransactional(TransactionalDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User reciver = this.userService.findUserById(transaction.reciverId());

        userService.validadeTransaction(sender, transaction.value());

        boolean isAutorized = this.autorizationTransaction(sender, transaction.value());
        if (!isAutorized){
            throw new Exception("Transação não está autorizada!");
        }

        Transaction newtransaction = new Transaction();
        newtransaction.setAmount(transaction.value());
        newtransaction.setSender(sender);
        newtransaction.setReciver(reciver);
        newtransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        reciver.setBalance(reciver.getBalance().add(transaction.value()));

        this.repository.save(newtransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(reciver);

        try {
            this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
            this.notificationService.sendNotification(reciver, "Transação recebida com sucesso!");
        } catch (Exception e) {
            System.err.println("Falha na notificação: " + e.getMessage());
            throw new Exception("Falha no serviço de notificação. Transação cancelada.");
        }

        return newtransaction;
    }

    public boolean autorizationTransaction(User sender, BigDecimal value){
        try {

            ResponseEntity<Map> autorizationResponse = restTemplate.getForEntity(
                    "https://util.devi.tools/api/v2/authorize", Map.class);

            System.out.println("Status Code: " + autorizationResponse.getStatusCode());
            System.out.println("Response Body: " + autorizationResponse.getBody());

            if (autorizationResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = autorizationResponse.getBody();

                if (body != null && body.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) body.get("data");
                    Object authObj = data.get("authorization");

                    if (authObj instanceof Boolean) {
                        return (Boolean) authObj;
                    }
                }
            }

            return false;

        } catch (Exception e) {
            System.err.println("Erro na autorização: " + e.getMessage());
            return false;
        }
    }

}
