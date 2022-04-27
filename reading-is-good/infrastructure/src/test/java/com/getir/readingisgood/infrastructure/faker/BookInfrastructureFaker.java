package com.getir.readingisgood.infrastructure.faker;

import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.MerchantOfBookEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.SaleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookInfrastructureFaker extends AbstractInfrastructureFaker {

    private final MerchantInfrastructureFaker merchantInfrastructureFaker;

    public BookInfrastructureFaker() {
        merchantInfrastructureFaker = new MerchantInfrastructureFaker();
    }

    public BookEntity bookEntity() {
        return bookEntity(merchantInfrastructureFaker.id());
    }

    public BookEntity bookEntity(final UUID merchantId) {
        final BookEntity bookEntity = BookEntity.builder()
                .author(author())
                .name(name())
                .sale(saleEntity())
                .merchant(merchantOfBookEntity(merchantId))
                .build();
        loadAbstractPropertiesForCreation(bookEntity);
        return bookEntity;
    }

    public List<BookEntity> bookEntities(final List<MerchantEntity> merchantEntities) {
        final List<BookEntity> bookEntities = new ArrayList<>();
        for (var merchantEntity : merchantEntities) {
            final int bookCount = faker.number().numberBetween(1, 3);
            final List<BookEntity> booksOfMerchant = IntStream.range(0, bookCount).mapToObj(value -> {
                final BookEntity bookEntity = bookEntity();
                bookEntity.getMerchant().setId(merchantEntity.getId());
                return bookEntity;
            }).collect(Collectors.toList());
            bookEntities.addAll(booksOfMerchant);
        }
        return bookEntities;
    }

    public MerchantOfBookEntity merchantOfBookEntity() {
        return merchantOfBookEntity(merchantInfrastructureFaker.id());
    }

    public MerchantOfBookEntity merchantOfBookEntity(final UUID id) {
        return MerchantOfBookEntity.builder()
                .id(id)
                .build();
    }

    public SaleEntity saleEntity() {
        return SaleEntity.builder()
                .price(price())
                .quantity(quantity())
                .build();
    }

    public String name() {
        return faker.book().title();
    }

    public String author() {
        return faker.book().author();
    }
}
