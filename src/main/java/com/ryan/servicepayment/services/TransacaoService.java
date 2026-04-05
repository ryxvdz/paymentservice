package com.ryan.servicepayment.services;

import com.ryan.servicepayment.dto.ContaBalance;
import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.dto.TransacaoResponse;
import com.ryan.servicepayment.exeption.ContaNaoEncontradaException;
import com.ryan.servicepayment.exeption.TipoTransacaoInvalidaException;
import com.ryan.servicepayment.mapper.TransacaoMapper;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.model.Transacao;
import com.ryan.servicepayment.repository.ContaRepository;
import com.ryan.servicepayment.repository.TransacaoRepository;
import com.ryan.servicepayment.strategy.TransacaoStrategy;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.ryan.servicepayment.factory.ContaBalanceFactory.settarSaldo;


@AllArgsConstructor
@Service
public class TransacaoService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    private static final String BALANCEAMENTO_KEY_PREFIX = "balanceamento";

    private final Map<String, TransacaoStrategy> estrategias;

    public Transacao processoTransacao(TransacaoRequest transacaoRequest) {

        String redisKey = BALANCEAMENTO_KEY_PREFIX + transacaoRequest.contaId();
        Conta conta = contaRepository.findById(transacaoRequest.contaId())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada para o ID: " + transacaoRequest.contaId()));


        ContaBalance contaBalance = (ContaBalance) redisTemplate.opsForValue().get(redisKey);
        if (contaBalance == null) {
            contaBalance = settarSaldo(transacaoRequest, conta.getSaldoAtual(), conta.getLimiteAtual());
        }

        TransacaoStrategy strategy = estrategias.get(transacaoRequest.transacao().name());

        if(strategy ==null){
            throw new TipoTransacaoInvalidaException("Tipo de transação desconhecida: " + transacaoRequest.transacao());
        }

        Transacao transacao = strategy.processar(transacaoRequest, conta,contaBalance);


        redisTemplate.opsForValue().set(redisKey,contaBalance);

        return transacao;

    }


    public List<TransacaoResponse> buscarPorContaId(String id){
        return transacaoRepository.findByContaId(id)
                .stream()
                .map(TransacaoMapper::toResponse)
                .toList();
    }

}
