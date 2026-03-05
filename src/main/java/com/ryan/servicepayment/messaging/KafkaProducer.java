package com.ryan.servicepayment.messaging;


import com.ryan.servicepayment.dto.TransacaoRequest;
import com.ryan.servicepayment.model.Transacao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


@Slf4j
@AllArgsConstructor
@Service
public class KafkaProducer {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;



    public void envioTransacaoAprovada(Transacao transacao){
        try {
            String message = objectMapper.writeValueAsString(transacao);
            streamBridge.send("spring.cloud.topics.trasancao-aprovada", message);
            log.info("Transação Aprovada com sucesso: {}", transacao.getId());
        }catch (RuntimeException e){

            log.error("Erro ao enviar mensagem para topico.", e);
        }
    }

    public void envioTransacaoRecusada(TransacaoRequest transacaoRequest){
        try {
            String message = objectMapper.writeValueAsString(transacaoRequest);
            streamBridge.send("spring.cloud.topics.trasancao-reprovada", message);
            log.info("Transação recusada: {}");
        }catch (RuntimeException e){

            log.error("Erro ao enviar mensagem para topico.", e);
        }

    }

}
