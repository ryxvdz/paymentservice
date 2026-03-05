package com.ryan.servicepayment.repository;

import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.model.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContaRepository extends MongoRepository<Conta, String> {

    List<Transacao> findByContaId(String contaId);
}
