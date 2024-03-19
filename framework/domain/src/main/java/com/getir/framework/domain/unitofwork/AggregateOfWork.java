package com.getir.framework.domain.unitofwork;

import com.getir.framework.domain.model.aggregate.AggregateRoot;

public interface AggregateOfWork<TAggregate extends AggregateRoot> {

    TAggregate insert(TAggregate aggregate);

    TAggregate update(TAggregate aggregate);

    TAggregate delete(TAggregate aggregate);
}
