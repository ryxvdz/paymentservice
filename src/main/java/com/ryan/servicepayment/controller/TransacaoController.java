package com.ryan.servicepayment.controller;


import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.model.Transacao;
import com.ryan.servicepayment.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;



    @PostMapping("criar-transacao")
    public Transacao criarTransacao(@RequestBody TransacaoRequest transacaoRequest){

        return transacaoService.processoTransacao(transacaoRequest);
    }
}
