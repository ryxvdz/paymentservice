package com.ryan.servicepayment.controller;


import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.dto.TransacaoResponse;
import com.ryan.servicepayment.model.Transacao;
import com.ryan.servicepayment.services.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;



    @PostMapping("criar-transacao")
    public Transacao criarTransacao(@RequestBody TransacaoRequest transacaoRequest){

        return transacaoService.processoTransacao(transacaoRequest);
    }

    @GetMapping("/{id}")
    public List<TransacaoResponse> transacaoPorId(@PathVariable String id){
        return transacaoService.buscarPorContaId(id);
    }
}
