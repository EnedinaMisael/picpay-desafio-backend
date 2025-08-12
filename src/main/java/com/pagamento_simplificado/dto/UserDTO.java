package com.pagamento_simplificado.dto;

import com.pagamento_simplificado.domain.UserType;

import java.math.BigDecimal;

public record UserDTO (String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType){
}
