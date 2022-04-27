package com.getir.framework.domain.unitofwork;

import com.getir.framework.domain.model.aggregate.AggregateRoot;

public interface AggregateOfWork<TAggregate extends AggregateRoot> {

    TAggregate Insert(TAggregate aggregate);

    TAggregate Update(TAggregate aggregate);

    TAggregate Delete(TAggregate aggregate);
}
