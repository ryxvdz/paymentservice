package com.ryan.servicepayment.exeption;

public class TipoTransacaoInvalidaException extends RuntimeException {
    public TipoTransacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
