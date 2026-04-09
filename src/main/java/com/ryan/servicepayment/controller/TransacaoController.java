package com.ryan.servicepayment.controller;


import com.ryan.servicepayment.dto.SucessoResponse;
import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.dto.TransacaoResponse;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.model.Transacao;
import com.ryan.servicepayment.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;



    @PostMapping("criar-transacao")
    public ResponseEntity<SucessoResponse<Transacao>> criarTransacao(@RequestBody @Valid TransacaoRequest transacaoRequest){

        Transacao transacaoSalva= transacaoService.processoTransacao(transacaoRequest);

        SucessoResponse<Transacao> resposta = new SucessoResponse<>(
                "Processo de Transacao iniciado com sucesso",
                transacaoSalva
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/{id}")
    public List<TransacaoResponse> transacaoPorId(@PathVariable String id){
        return transacaoService.buscarPorContaId(id);
    }
}
