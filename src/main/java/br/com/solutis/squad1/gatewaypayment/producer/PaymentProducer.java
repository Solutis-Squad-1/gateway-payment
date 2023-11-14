package br.com.solutis.squad1.gatewaypayment.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import br.com.solutis.squad1.gatewaypayment.dto.GatewayPaymentDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routing-key.gateway}")
    private String gatewayRoutingKey;

    @Value("${spring.rabbitmq.exchange.gateway}")
    private String gatewayExchange;

    public void produce(Long paymentId, Boolean confirmed) {
        log.info("Sending message to queue: {}", gatewayRoutingKey);

        rabbitTemplate.convertAndSend(
                gatewayRoutingKey,
                gatewayExchange,
                new GatewayPaymentDto(paymentId, confirmed)
        );
    }
}