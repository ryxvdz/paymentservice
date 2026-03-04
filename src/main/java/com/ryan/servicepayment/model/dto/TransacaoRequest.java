package com.ryan.servicepayment.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoRequest(
        @NotBlank(message = "ID da conta obrigatorio") String contaId,
        @NotBlank(message = "ID do cartao obrigatorio") String cartaoId,
        @NotNull(message = "Valor obrigatorio") BigDecimal valor,
        @NotBlank(message = "Comerciante obrigatorio") String comerciante,
        String localizacao
) {}
