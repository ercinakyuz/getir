package com.getir.readingisgood.domain.order.model.aggregate.event;

import com.getir.framework.domain.model.aggregate.event.AbstractEvent;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderCompleted extends AbstractEvent {
    private UUID id;
}
