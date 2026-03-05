package com.ryan.servicepayment.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaBalance implements Serializable {

    private String contaId;

    private BigDecimal saldoEmConta;

    private BigDecimal limiteEmConta;

}
