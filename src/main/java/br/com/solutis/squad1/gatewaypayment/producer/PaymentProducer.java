package br.com.solutis.squad1.gatewaypayment.producer;

import br.com.solutis.squad1.gatewaypayment.dto.GatewayResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routing-key.payment.gateway}")
    private String paymentGatewayRoutingKey;

    @Value("${spring.rabbitmq.exchange.payment.gateway}")
    private String paymentGatewayExchange;

    public void produce(Long paymentId, Boolean confirmed) {
        log.info("Sending message to queue: {}", paymentGatewayRoutingKey);

        rabbitTemplate.convertAndSend(
                paymentGatewayExchange,
                paymentGatewayRoutingKey,
                new GatewayResponseDto(paymentId, confirmed)
        );
    }
}