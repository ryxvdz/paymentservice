package com.ryan.servicepayment.services;

import static com.ryan.servicepayment.factory.ContaBalanceFactory.settarSaldo;
import com.ryan.servicepayment.dto.ContaBalance;
import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.enums.TipoTransacao;
import com.ryan.servicepayment.model.Transacao;
import com.ryan.servicepayment.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String BALANCEAMENTO_KEY_PREFIX = "balanceamento";


    public Transacao processoTransacao(TransacaoRequest transacaoRequest)
    {
        String redisKey = BALANCEAMENTO_KEY_PREFIX + transacaoRequest.contaId();
        ContaBalance contaBalance = (ContaBalance) redisTemplate.opsForValue().get(redisKey);

        if (contaBalance == null) {
            contaBalance = settarSaldo(transacaoRequest, BigDecimal.ZERO,BigDecimal.ZERO);
        }



        BigDecimal saldoAtual = contaBalance.getSaldoEmConta();
        BigDecimal limiteAtual= contaBalance.getLimiteEmConta();
        BigDecimal valorDaTransacao = transacaoRequest.valor();

        BigDecimal novoSaldo = saldoAtual;
        BigDecimal novoLimite = limiteAtual;


        if (transacaoRequest.transacao() == TipoTransacao.DEBITO){
            if(saldoAtual.compareTo(valorDaTransacao)< 0){
                throw new RuntimeException("Saldo Insuficiente!");
            } novoSaldo = saldoAtual.subtract(valorDaTransacao);
            saldoAtual= novoSaldo;


        } else if (transacaoRequest.transacao() == TipoTransacao.CREDITO) {
            if (limiteAtual.compareTo(valorDaTransacao)<0){
                throw new RuntimeException("Limite Insuficiente!");
            } novoLimite= limiteAtual.subtract(valorDaTransacao);


        }else{
            throw new RuntimeException("Tipo de Transacao Invalida");
            }

        ContaBalance novoBalance = settarSaldo(transacaoRequest, novoSaldo,novoLimite);
        redisTemplate.opsForValue().set(redisKey, novoBalance);

        Transacao transacao = new Transacao();

        transacao.setValor(transacaoRequest.valor());
        transacao.setTipoTransacao(transacaoRequest.transacao());
        transacao.setLocalizacao(transacaoRequest.localizacao());
        transacao.setDataHota(LocalDateTime.now());
        transacao.setComerciante(transacaoRequest.comerciante());



        return transacaoRepository.save(transacao);
    }


}
