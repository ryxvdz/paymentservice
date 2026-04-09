package com.ryan.servicepayment.exeption;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErroResponse> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Unprocessable Entity",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> handleContaNaoEncontrada(ContaNaoEncontradaException ex) {
        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(TipoTransacaoInvalidaException.class)
    public ResponseEntity<ErroResponse> handleTipoTransacaoInvalida(TipoTransacaoInvalidaException ex) {
        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handleHttpMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException ex) {

        // Mensagem padrão caso seja outro tipo de erro de formatação no JSON
        String mensagemAmigavel = "Erro de formatação no corpo da requisição (JSON inválido ou tipo de dado incorreto).";

        // Se o erro for especificamente sobre o Enum, deixamos a mensagem mais clara
        if (ex.getMessage() != null && ex.getMessage().contains("not one of the values accepted for Enum class")) {
            mensagemAmigavel = "Valor inválido para o campo de transação. Os valores aceitos são apenas CREDITO ou DEBITO.";
        }

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                mensagemAmigavel
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }


    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacaoDeCampos(org.springframework.web.bind.MethodArgumentNotValidException ex) {

        String mensagemDeErro = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(" | "));

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                mensagemDeErro
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}