package com.ryan.servicepayment.model;

import com.ryan.servicepayment.enums.TipoTransacao;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;




@Data
public class Transacao {

    private String id;

    private Conta conta;

    private TipoTransacao tipoTransacao;

    private Cartao cartao;

    private BigDecimal valor;

    private String comerciante;

    private String localizacao;

    private LocalDateTime dataHota;

    public Transacao(){
        this.dataHota = LocalDateTime.now();
    }


}
