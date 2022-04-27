package com.getir.framework.domain.model.aggregate.converter;

import com.getir.framework.data.entity.AbstractEntity;
import com.getir.framework.domain.model.aggregate.AggregateRoot;
import org.springframework.core.convert.converter.Converter;

public abstract class GenericAggregateConverter<TAggregate extends AggregateRoot, TEntity extends AbstractEntity> implements Converter<TAggregate, TEntity> {

    public TEntity convertAll(final TAggregate aggregate) {
        final TEntity entity = convert(aggregate);
        entity.setCreatedAt(aggregate.getCreatedAt());
        entity.setCreatedBy(aggregate.getCreatedBy());
        entity.setModifiedAt(aggregate.getModifiedAt());
        entity.setModifiedBy(aggregate.getModifiedBy());
        return entity;
    }
}
