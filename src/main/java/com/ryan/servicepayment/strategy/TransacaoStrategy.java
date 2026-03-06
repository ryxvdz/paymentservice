package com.ryan.servicepayment.strategy;

import com.ryan.servicepayment.dto.ContaBalance;
import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.model.Transacao;

public interface TransacaoStrategy {

    Transacao processar(TransacaoRequest transacaoRequest, Conta conta, ContaBalance contaBalance);
}
