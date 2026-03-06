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
@Component("CREDITO")
public class TransacaoCreditoStrategy implements TransacaoStrategy{

    private final TransacaoRepository transacaoRepository;
    private final KafkaProducer kafkaProducer;
    private final ContaRepository contaRepository;


    @Override
    public Transacao processar(TransacaoRequest transacaoRequest, Conta conta, ContaBalance contaBalance){

        BigDecimal limiteAtual = contaBalance.getLimiteEmConta();
        BigDecimal valorDaTransacao=  transacaoRequest.valor();


        if (limiteAtual.compareTo(valorDaTransacao)< 0){
            Transacao transacaoRecusada = new Transacao();

            transacaoRecusada.setConta(conta);
            transacaoRecusada.setValor(transacaoRequest.valor());
            transacaoRecusada.setId(UUID.randomUUID().toString());
            transacaoRecusada.setTipoTransacao(transacaoRequest.transacao());
            transacaoRecusada.setLocalizacao(transacaoRequest.localizacao());
            transacaoRecusada.setDataHota(LocalDateTime.now());
            transacaoRecusada.setStatusDoPagamento(StatusDoPagamento.RECUSADO);
            transacaoRecusada.setComerciante(transacaoRequest.comerciante());

            transacaoRepository.save(transacaoRecusada);
            kafkaProducer.envioTransacaoRecusada(transacaoRecusada);

            throw new RuntimeException("Limite Insuficiente!");
        }

        contaBalance.setLimiteEmConta(limiteAtual.subtract(valorDaTransacao));

        Transacao transacaoAprovada = new Transacao();

        transacaoAprovada.setId(UUID.randomUUID().toString());
        transacaoAprovada.setConta(conta);
        transacaoAprovada.setValor(valorDaTransacao);
        transacaoAprovada.setTipoTransacao(transacaoRequest.transacao());
        transacaoAprovada.setLocalizacao(transacaoRequest.localizacao());
        transacaoAprovada.setDataHota(LocalDateTime.now());
        transacaoAprovada.setStatusDoPagamento(StatusDoPagamento.APROVADO);
        transacaoAprovada.setComerciante(transacaoRequest.comerciante());

        transacaoRepository.save(transacaoAprovada);
        conta.setSaldoAtual(contaBalance.getSaldoEmConta());
        contaRepository.save(conta);

        kafkaProducer.envioTransacaoAprovada(transacaoAprovada);

        return transacaoAprovada;


    }
}
