package com.ryan.servicepayment.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Conta {

    private String id;
    private String nomeCompleto;
    private String cpf;
    private Cartao cartao;
    private BigDecimal saldoInicial;
    private BigDecimal limiteInicial;
}
