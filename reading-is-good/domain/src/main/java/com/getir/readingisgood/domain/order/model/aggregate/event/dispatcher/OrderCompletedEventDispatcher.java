package com.getir.readingisgood.domain.order.model.aggregate.event.dispatcher;

import com.getir.readingisgood.domain.order.model.aggregate.event.OrderCompleted;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.getir.readingisgood.infrastructure.bus.RabbitQueueProperties.EXCHANGE_READING_IS_GOOD;
import static com.getir.readingisgood.infrastructure.bus.RabbitQueueProperties.RK_ORDER_COMPLETED;

@Component
@RequiredArgsConstructor
public class OrderCompletedEventDispatcher implements ApplicationListener<OrderCompleted> {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void onApplicationEvent(final OrderCompleted orderCompleted) {
        rabbitTemplate.convertAndSend(EXCHANGE_READING_IS_GOOD, RK_ORDER_COMPLETED, orderCompleted);
    }
}
