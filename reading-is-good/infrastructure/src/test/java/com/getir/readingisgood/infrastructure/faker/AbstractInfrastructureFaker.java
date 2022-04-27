package com.getir.readingisgood.infrastructure.faker;

import com.getir.framework.data.entity.AbstractEntity;
import com.getir.framework.test.faker.AbstractFaker;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.MerchantOfBookEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractInfrastructureFaker extends AbstractFaker {

    public void loadAbstractPropertiesForCreation(final AbstractEntity abstractEntity) {
        abstractEntity.setId(id());
        abstractEntity.setCreatedAt(createdAt());
        abstractEntity.setCreatedBy(createdBy());
    }

    public void loadAbstractPropertiesForModification(final AbstractEntity abstractEntity) {
        loadAbstractPropertiesForCreation(abstractEntity);
        abstractEntity.setModifiedAt(modifiedAt());
        abstractEntity.setModifiedBy(modifiedBy());
    }

    public UUID id() {
        return UUID.randomUUID();
    }

    public Instant createdAt() {
        return Instant.now();
    }

    public String createdBy() {
        return faker.name().fullName();
    }

    public Instant modifiedAt() {
        return Instant.now();
    }

    public String modifiedBy() {
        return faker.name().fullName();
    }

    public int quantity() {
        return faker.number().numberBetween(1, 10);
    }

    public BigDecimal price() {
        return bigDecimalValue(2, 1, 10);
    }
}
