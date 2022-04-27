package com.getir.readingisgood.infrastructure.persistence.repository;

import com.getir.readingisgood.infrastructure.persistence.entity.CredentialsEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByCredentialsEmail(final String email);
}

