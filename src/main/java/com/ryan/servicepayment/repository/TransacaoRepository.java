package com.ryan.servicepayment.repository;

import com.ryan.servicepayment.model.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends MongoRepository<Transacao, String> {

    List<Transacao> findByContaId(String id);

}
