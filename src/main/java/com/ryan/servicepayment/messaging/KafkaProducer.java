package com.ryan.servicepayment.messaging;


import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class KafkaProducer {

    private final StreamBridge streamBridge;



}
