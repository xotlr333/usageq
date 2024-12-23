package com.telco.management.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class QueueConfig {

    @Value("${spring.rabbitmq.listener.simple.retry.enabled:true}")
    private boolean retryEnabled;

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts:3}")
    private int maxAttempts;

    @Bean
    public Queue usageQueue() {
        return QueueBuilder.durable("usage.queue")
                .withArgument("x-dead-letter-exchange", "usage.dlx")
                .withArgument("x-dead-letter-routing-key", "usage.dead")
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("usage.dlq")
                .withArgument("x-message-ttl", 60000)
                .build();
    }

    @Bean
    public DirectExchange usageExchange() {
        return new DirectExchange("usage.exchange");
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("usage.dlx");
    }

    @Bean
    public Binding usageBinding(Queue usageQueue, DirectExchange usageExchange) {
        return BindingBuilder.bind(usageQueue)
                .to(usageExchange)
                .with("usage.update");
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("usage.dead");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setDefaultRequeueRejected(false);

        if (retryEnabled) {
            factory.setRetryTemplate(createRetryTemplate());
        }

        return factory;
    }

    private org.springframework.retry.support.RetryTemplate createRetryTemplate() {
        org.springframework.retry.support.RetryTemplate template = new org.springframework.retry.support.RetryTemplate();

        org.springframework.retry.policy.SimpleRetryPolicy retryPolicy = new org.springframework.retry.policy.SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);

        org.springframework.retry.backoff.ExponentialBackOffPolicy backOffPolicy = new org.springframework.retry.backoff.ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000);

        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        return template;
    }
}