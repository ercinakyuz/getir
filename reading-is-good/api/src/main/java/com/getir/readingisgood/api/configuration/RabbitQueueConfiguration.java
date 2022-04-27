package com.getir.readingisgood.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.getir.readingisgood.infrastructure.bus.RabbitQueueProperties.*;


@Configuration
@RequiredArgsConstructor
public class RabbitQueueConfiguration {

    //Queues
    @Bean
    public Queue orderCompletedQueue() {
        return QueueBuilder.durable(QUEUE_ORDER_COMPLETED)
                .withArgument(ARG_KEY_DL_EXCHANGE, DL_EXCHANGE_READING_IS_GOOD)
                .withArgument(ARG_KEY_DL_ROUTING_KEY, DL_RK_ORDER_COMPLETED)
                .build();
    }

    //Deadletters
    @Bean
    public Queue orderCompletedDeadLetterQueue() {
        return QueueBuilder.durable(DL_QUEUE_ORDER_COMPLETED).build();
    }

    //Bindings
    @Bean
    public Binding orderCompletedQueueBinding() {
        return BindingBuilder.bind(orderCompletedQueue())
                .to(readingIsGoodTopicExchange())
                .with(RK_ORDER_COMPLETED);
    }

    @Bean
    public Binding orderCompletedDeadLetterBinding() {
        return BindingBuilder.bind(orderCompletedDeadLetterQueue())
                .to(deadLetterExchange())
                .with(DL_RK_ORDER_COMPLETED);
    }

    @Bean
    public TopicExchange readingIsGoodTopicExchange() {
        return new TopicExchange(EXCHANGE_READING_IS_GOOD);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DL_EXCHANGE_READING_IS_GOOD);
    }
}