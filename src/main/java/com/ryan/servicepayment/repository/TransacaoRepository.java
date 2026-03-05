package com.ryan.servicepayment.repository;

import com.ryan.servicepayment.model.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransacaoRepository extends MongoRepository<Transacao, String> {

    List<Transacao> findByContaId(String contaId);

    List<Transacao> findByUserIdOrderByCreatedAtDesc(String transacaoId);
}
