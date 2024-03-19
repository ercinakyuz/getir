package com.getir.framework.domain.model.aggregate;

import com.getir.framework.domain.model.aggregate.event.AbstractEvent;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.collections.set.UnmodifiableSet;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public abstract class AggregateRoot {

    @Getter(AccessLevel.NONE)
    private Set<AbstractEvent> events;

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

    public AggregateRoot addEvent(AbstractEvent event) {
        events.add(event);
        return this;
    }
    public Set<AbstractEvent> getEvents() {
        return events.stream().collect(Collectors.toUnmodifiableSet());
    }
    public AggregateRoot clearEvents() {
        events.clear();
        return this;
    }
}
