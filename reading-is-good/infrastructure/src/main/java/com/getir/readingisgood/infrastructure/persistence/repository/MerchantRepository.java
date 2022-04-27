package com.getir.readingisgood.infrastructure.persistence.repository;

import com.getir.readingisgood.infrastructure.persistence.entity.CredentialsEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository extends MongoRepository<MerchantEntity, UUID> {

    Optional<MerchantEntity> findByCredentialsEmail(final String email);
}

