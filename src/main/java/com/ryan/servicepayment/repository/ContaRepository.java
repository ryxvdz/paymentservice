package com.ryan.servicepayment.repository;

import com.ryan.servicepayment.model.Conta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends MongoRepository<Conta, String> {

    List<Conta> findByCpf(String cpf);

}
