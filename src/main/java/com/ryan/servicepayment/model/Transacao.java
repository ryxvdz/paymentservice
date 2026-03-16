package com.ryan.servicepayment.model;

import com.ryan.servicepayment.enums.StatusDoPagamento;
import com.ryan.servicepayment.enums.TipoTransacao;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;




@Data
@Document("Transacão")
public class Transacao {

    @Id
    private String id;

    private Conta conta;

    private StatusDoPagamento statusDoPagamento;

    private TipoTransacao tipoTransacao;

    private BigDecimal valor;

    private String comerciante;

    private String localizacao;

    private LocalDateTime dataHota;

    public Transacao(){
        this.dataHota = LocalDateTime.now();
    }


}
