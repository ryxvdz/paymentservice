package com.ryan.servicepayment.model;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Conta {

    private String id;
    private String nomeCompleto;
    private String cpf;
    private String cartao;
    private BigDecimal saldoInicial;
    private BigDecimal limiteInicial;
}
