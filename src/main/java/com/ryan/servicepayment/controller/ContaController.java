package com.ryan.servicepayment.controller;


import com.ryan.servicepayment.dto.ContaRequest;
import com.ryan.servicepayment.dto.SucessoResponse;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.services.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/conta")
public class ContaController {

    private final ContaService contaService;


    @PostMapping("/criar-conta")
    public ResponseEntity<SucessoResponse<Conta>> criarConta(@RequestBody @Valid ContaRequest contaRequest) {

        Conta contaSalva = contaService.criarConta(contaRequest);

        SucessoResponse<Conta> resposta = new SucessoResponse<>(
                "Conta criada com sucesso e pronta para uso!",
                contaSalva
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/listar-todas-as-contas")
    public List<Conta> todasAsContas(){
        return contaService.listarTodasContas();
    }


    @GetMapping("/{cpf}")
    public Conta buscarPorCpf(String cpf){
        return contaService.buscarPorCpf(cpf);
    }


    @DeleteMapping
    public void deletarConta(String id){
        contaService.deletarConta(id);
    }
}
