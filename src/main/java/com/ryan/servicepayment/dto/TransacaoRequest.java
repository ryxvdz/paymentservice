package com.ryan.servicepayment.dto;

import com.ryan.servicepayment.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransacaoRequest(
        String contaId,
        String cartaoId,
        @NotNull(message = "O valor da transação é obrigatório.")
        @Positive(message = "O valor da transação deve ser maior que zero.")
        BigDecimal valor,
        TipoTransacao transacao,
        String comerciante,
        String localizacao
) {}
