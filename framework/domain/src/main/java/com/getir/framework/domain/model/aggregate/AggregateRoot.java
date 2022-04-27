package com.getir.framework.domain.model.aggregate;

import com.getir.framework.domain.model.aggregate.event.AbstractEvent;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class AggregateRoot {

    protected Set<AbstractEvent> events;

    protected Instant createdAt;

    protected Instant modifiedAt;

    protected String createdBy;

    protected String modifiedBy;

    protected AggregateRoot applyEvents() {
        return this;
    }

    protected AggregateRoot() {
        this.events = new HashSet<>();
    }

    public AggregateRoot clearEvents() {
        events.clear();
        return this;
    }
}
