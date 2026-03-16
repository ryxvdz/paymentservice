package com.ryan.servicepayment.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Document("Conta")
public class Conta {

    @Id
    private String id;
    private String nomeCompleto;
    private String cpf;
    private String cartao;
    private BigDecimal saldoAtual;
    private BigDecimal limiteAtual;
}
