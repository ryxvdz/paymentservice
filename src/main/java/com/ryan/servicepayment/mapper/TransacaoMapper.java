package com.ryan.servicepayment.mapper;

import com.ryan.servicepayment.dto.TransacaoResponse;
import com.ryan.servicepayment.model.Transacao;

public class TransacaoMapper {


    public static TransacaoResponse toResponse(Transacao transacao){
        TransacaoResponse response = new TransacaoResponse();
        response.setHora(transacao.getDataHota());
        response.setTipoTransacao(transacao.getTipoTransacao());
        response.setStatusDoPagamento(transacao.getStatusDoPagamento());
        response.setValor(transacao.getValor());

        return response;
    }
}
