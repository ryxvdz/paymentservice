package com.ryan.servicepayment.dto;

import com.ryan.servicepayment.enums.StatusDoPagamento;
import com.ryan.servicepayment.enums.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class TransacaoResponse {

    TipoTransacao tipoTransacao;
    LocalDateTime hora;
    BigDecimal valor ;
    StatusDoPagamento statusDoPagamento;
}
