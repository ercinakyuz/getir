package com.getir.readingisgood.api.event.handler;

import an.awesome.pipelinr.Pipeline;
import com.getir.readingisgood.api.event.OrderCompletedEvent;
import com.getir.readingisgood.application.usecase.statistics.command.SyncOrderStatisticsCommand;
import com.getir.readingisgood.infrastructure.bus.RabbitQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCompletedEventHandler {

    private final Pipeline pipeline;

    @RabbitListener(queues = RabbitQueueProperties.QUEUE_ORDER_COMPLETED)
    public void handle(final OrderCompletedEvent orderCompleted) {
        pipeline.send(SyncOrderStatisticsCommand.builder()
                .orderId(orderCompleted.getId())
                .build());
    }
}
