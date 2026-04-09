package com.ryan.servicepayment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record ContaRequest(
        @NotBlank(message = "O nome completo é obrigatório e não pode estar em branco.")
        String nomeCompleto,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O formato do CPF é inválido.")
        String CPF,

        @NotNull(message = "O saldo inicial é obrigatório.")
        @PositiveOrZero(message = "O saldo inicial não pode ser negativo.")
        BigDecimal saldoInicial,

        @PositiveOrZero(message = "O limite inicial não pode ser negativo.")
        BigDecimal limiteInicial) {
}
