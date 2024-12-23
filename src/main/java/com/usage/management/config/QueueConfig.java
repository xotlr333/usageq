package com.usage.management.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    @Bean
    public Queue usageQueue() {
        return QueueBuilder.durable("usage.queue")
            .withArgument("x-dead-letter-exchange", "usage.dlx")
            .withArgument("x-dead-letter-routing-key", "usage.dead")
            .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("usage.dead.queue").build();
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
    public Binding usageBinding() {
        return BindingBuilder
            .bind(usageQueue())
            .to(usageExchange())
            .with("usage.update");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
            .bind(deadLetterQueue())
            .to(deadLetterExchange())
            .with("usage.dead");
    }
}
