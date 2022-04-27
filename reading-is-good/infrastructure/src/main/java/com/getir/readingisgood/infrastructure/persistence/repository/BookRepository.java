package com.getir.readingisgood.infrastructure.persistence.repository;

import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BookRepository extends MongoRepository<BookEntity, UUID> {

}

