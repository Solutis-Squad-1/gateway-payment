package br.com.solutis.squad1.gatewaypayment.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.queue.gateway}")
    private String gatewayQueueName;

    @Value("${spring.rabbitmq.exchange.gateway}")
    private String gatewayExchangeName;

    @Value("${spring.rabbitmq.routing-key.gateway}")
    private String gatewayRoutingKey;

    @Bean
    public Queue gatewayQueue() {
        return QueueBuilder
                .durable(gatewayQueueName)
                .build();
    }

    @Bean
    DirectExchange gatewayExchange() {
        return new DirectExchange(gatewayExchangeName);
    }

    @Bean
    Binding gatewayBinding() {
        return BindingBuilder
                .bind(gatewayQueue())
                .to(gatewayExchange())
                .with(gatewayRoutingKey);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> listener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
