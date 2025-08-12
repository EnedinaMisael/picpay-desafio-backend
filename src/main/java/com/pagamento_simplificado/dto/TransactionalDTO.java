package com.pagamento_simplificado.dto;

import java.math.BigDecimal;

public record TransactionalDTO (BigDecimal value, Long senderId, Long reciverId){
}
