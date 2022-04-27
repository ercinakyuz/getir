package com.getir.readingisgood.infrastructure.persistence.repository;

import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderStatisticsRepository extends MongoRepository<OrderStatisticsEntity, UUID> {

    List<OrderStatisticsEntity> findAllByCustomerIdAndYear(final UUID customerId, final int year);

    Optional<OrderStatisticsEntity> findByCustomerIdAndYearAndMonth(final UUID customerId, final int year, final Month month);

}
