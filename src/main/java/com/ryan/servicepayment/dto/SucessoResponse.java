package com.ryan.servicepayment.dto;

public record SucessoResponse<T>(
        String mensagem,
        T dados
) {
}
