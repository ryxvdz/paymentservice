package com.ryan.servicepayment.controller;


import com.ryan.servicepayment.dto.ContaRequest;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.services.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/conta")
public class ContaController {

    private final ContaService contaService;


    @PostMapping("/criar-conta")
    public Conta criarConta(@RequestBody @Valid ContaRequest contaRequest) {
        return contaService.criarConta(contaRequest);
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
