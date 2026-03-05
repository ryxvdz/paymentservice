package com.ryan.servicepayment.dto;

import com.ryan.servicepayment.enums.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoRequest(
        String contaId,
        String cartaoId,
        BigDecimal valor,
        TipoTransacao transacao,
        String comerciante,
        String localizacao
) {}
