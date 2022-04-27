package com.getir.framework.domain.model.aggregate.state;

import lombok.Getter;

@Getter
public abstract class AggregateState {
    private String value;

    protected AggregateState(final String value) {
        this.value = value;
    }

    protected static <TState> TState create(final Class<TState> classOfState, final String value) {
        TState state;
        try {
            state = classOfState.getDeclaredConstructor(String.class).newInstance(value);
        } catch (Exception e) {
            state = null;
        }
        return state;
    }
}
