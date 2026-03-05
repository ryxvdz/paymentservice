package com.ryan.servicepayment.services;


import com.ryan.servicepayment.dto.ContaBalance;
import com.ryan.servicepayment.dto.ContaRequest;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String BALANCEAMENTO_KEY_PREFIX = "balanceamento:";

    public Conta criarConta(ContaRequest contaRequest){
        Conta conta = new Conta();

        conta.setId(UUID.randomUUID().toString());
        conta.setNomeCompleto(contaRequest.nomeCompleto());
        conta.setCpf(contaRequest.CPF());
        conta.setSaldoInicial(contaRequest.saldoInicial());
        conta.setLimiteInicial(contaRequest.limiteInicial());
        conta.setCartao(UUID.randomUUID().toString());

        Conta contaSalva = contaRepository.save(conta);

        ContaBalance contaBalance = new ContaBalance(
          contaSalva.getId(),
          contaRequest.saldoInicial(),
          contaRequest.limiteInicial()
        );

        redisTemplate.opsForValue().set(BALANCEAMENTO_KEY_PREFIX+ contaSalva.getId(),contaBalance);
        return contaSalva;
    }

    public List<Conta> listarTodasContas(){

        return contaRepository.findAll();
    }

    public Conta buscarPorCpf(String cpf){

    return (Conta) contaRepository.findByCpf(cpf);
    }


}
