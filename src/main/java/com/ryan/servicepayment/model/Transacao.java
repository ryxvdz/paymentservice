package com.ryan.servicepayment.model;

import com.ryan.servicepayment.enums.TipoTransacao;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Getter
@Setter
@Data
@ToString
public class Transacao {

    private String id;

    private String contaId;

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
