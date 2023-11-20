package br.com.solutis.squad1.gatewaypayment.consumer;

import br.com.solutis.squad1.gatewaypayment.dto.GatewayDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import br.com.solutis.squad1.gatewaypayment.producer.PaymentProducer;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final PaymentProducer paymentProducer;

    @RabbitListener(queues = {"${spring.rabbitmq.queue.gateway}"})
    public void consume(
            @Payload GatewayDto gatewayDto
            ) {
        Boolean confirmed = isConfirmed();
        paymentProducer.produce(gatewayDto.paymentId(), confirmed);
    }

    public boolean isConfirmed() {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        return randomDouble < 0.9;
    }
}