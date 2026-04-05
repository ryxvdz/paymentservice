package com.ryan.servicepayment.exeption;

import java.time.LocalDateTime;

public record ErroResponse(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String mensagem
) {
}
