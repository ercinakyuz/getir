package com.getir.readingisgood.infrastructure.persistence.repository;

import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<OrderEntity, UUID> {

    Page<OrderEntity> findAllByCustomerId(UUID customer, Pageable pageable);
    Page<OrderEntity> findAllByCustomerIdAndCreatedAtBetween(UUID customer, Instant createdAtStart, Instant createdAtEnd, Pageable pageable);
}
