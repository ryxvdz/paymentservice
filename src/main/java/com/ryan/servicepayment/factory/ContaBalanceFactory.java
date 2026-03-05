package com.ryan.servicepayment.factory;

import com.ryan.servicepayment.dto.ContaBalance;
import com.ryan.servicepayment.dto.TransacaoRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ContaBalanceFactory {

    public static ContaBalance settarSaldo(TransacaoRequest transacaoRequest, BigDecimal saldo, BigDecimal limite){

        return ContaBalance.builder()
                .contaId(transacaoRequest.contaId())
                .saldoEmConta(saldo)
                .limiteEmConta(limite)
                .build();

    }
}
