package com.getir.framework.domain.model.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class GenericAggregateRoot<TAggregate extends AggregateRoot, TId, TState> extends AggregateRoot {
    protected TId id;
    protected TState state;

    protected TAggregate changeState(final TState state) {
        this.state = state;
        return (TAggregate) this.applyEvents();
    }
}
