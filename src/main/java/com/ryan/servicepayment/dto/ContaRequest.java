package com.ryan.servicepayment.dto;

import java.math.BigDecimal;

public record ContaRequest(String nomeCompleto,
                           String CPF,
                           BigDecimal saldoInicial,
                           BigDecimal limiteInicial) {
}
