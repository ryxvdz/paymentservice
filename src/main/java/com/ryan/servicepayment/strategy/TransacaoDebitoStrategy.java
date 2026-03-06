package com.ryan.servicepayment.strategy;

import com.ryan.servicepayment.dto.ContaBalance;
import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.enums.StatusDoPagamento;
import com.ryan.servicepayment.messaging.KafkaProducer;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.model.Transacao;
import com.ryan.servicepayment.repository.ContaRepository;
import com.ryan.servicepayment.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component("DEBITO")
public class TransacaoDebitoStrategy implements TransacaoStrategy{

    private final TransacaoRepository transacaoRepository;
    private final KafkaProducer kafkaProducer;
    private final ContaRepository contaRepository;

    @Override
    public Transacao processar(TransacaoRequest transacaoRequest, Conta conta, ContaBalance contaBalance){

        BigDecimal saldoAtual = contaBalance.getSaldoEmConta();
        BigDecimal valorTransacao = transacaoRequest.valor();

        if (saldoAtual.compareTo(valorTransacao) < 0){
            Transacao transacaoRecusada = new Transacao();

            transacaoRecusada.setConta(conta);
            transacaoRecusada.setId(UUID.randomUUID().toString());
            transacaoRecusada.setTipoTransacao(transacaoRequest.transacao());
            transacaoRecusada.setLocalizacao(transacaoRequest.localizacao());
            transacaoRecusada.setDataHota(LocalDateTime.now());
            transacaoRecusada.setStatusDoPagamento(StatusDoPagamento.RECUSADO);
            transacaoRecusada.setComerciante(transacaoRequest.comerciante());

            transacaoRepository.save(transacaoRecusada);

            kafkaProducer.envioTransacaoRecusada(transacaoRecusada);

            throw new RuntimeException("Saldo Insuficiente!");
        }

        contaBalance.setSaldoEmConta(saldoAtual.subtract(valorTransacao));

        Transacao transacaoAprovada = new Transacao();

        transacaoAprovada.setConta(conta);
        transacaoAprovada.setId(UUID.randomUUID().toString());
        transacaoAprovada.setValor(transacaoRequest.valor());
        transacaoAprovada.setTipoTransacao(transacaoRequest.transacao());
        transacaoAprovada.setLocalizacao(transacaoRequest.localizacao());
        transacaoAprovada.setStatusDoPagamento(StatusDoPagamento.APROVADO);
        transacaoAprovada.setDataHota(LocalDateTime.now());
        transacaoAprovada.setComerciante(transacaoRequest.comerciante());


        conta.setSaldoAtual(contaBalance.getSaldoEmConta());
        contaRepository.save(conta);


        transacaoRepository.save(transacaoAprovada);
        kafkaProducer.envioTransacaoAprovada(transacaoAprovada);


        return transacaoAprovada;

    }
}
