package com.getir.readingisgood.domain.book.model.aggregate;

import com.getir.framework.domain.model.aggregate.GenericAggregateRoot;
import com.getir.readingisgood.domain.book.model.aggregate.dto.CreateBookDto;
import com.getir.readingisgood.domain.book.model.aggregate.dto.LoadBookDto;
import com.getir.readingisgood.domain.book.model.aggregate.state.BookState;
import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.domain.book.model.aggregate.value.Sale;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.getir.readingisgood.domain.book.model.aggregate.state.BookState.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Book extends GenericAggregateRoot<Book, UUID, BookState> {

    private String name;

    private MerchantOfBook merchant;

    private String author;

    private Sale sale;

    public static Book load(final LoadBookDto loadBookDto) {

        validateLoadDto(loadBookDto);

        final Book book = Book.builder()
                .name(loadBookDto.getName())
                .merchant(loadBookDto.getMerchant())
                .author(loadBookDto.getAuthor())
                .sale(loadBookDto.getSale())
                .build();

        book.id = loadBookDto.getId();
        book.createdAt = loadBookDto.getCreatedAt();
        book.createdBy = loadBookDto.getCreatedBy();
        book.modifiedAt = loadBookDto.getModifiedAt();
        book.modifiedBy = loadBookDto.getModifiedBy();

        return book.changeState(LOADED);
    }

    public static Book create(final CreateBookDto createDto) {

        validateCreateDto(createDto);

        final Book book = Book.builder()
                .name(createDto.getName())
                .merchant(createDto.getMerchant())
                .author(createDto.getAuthor())
                .sale(createDto.getSale())
                .build();

        book.id = UUID.randomUUID();
        book.createdAt = Instant.now();
        book.createdBy = String.format("merchantId: %s", book.getMerchant().getId());

        return book.changeState(CREATED);
    }

    private static void validateCreateDto(final CreateBookDto createDto) {
    }

    private static void validateLoadDto(final LoadBookDto loadDto) {

    }

    public Book validateOwnership(final UUID merchantId) {
        merchant.validate(merchantId);
        return this;
    }

    public Book decreaseQuantity(final int decreaseQuantity) {
        sale.decreaseQuantity(decreaseQuantity);
        return logForMerchantModification();
    }

    public Book decreaseQuantityForOrderCompletion(final int decreaseQuantity, final UUID customerId) {
        sale.decreaseQuantity(decreaseQuantity);
        return logForCustomerModification(customerId);
    }

    public Book increaseQuantity(final int increaseQuantity) {
        sale.increaseQuantity(increaseQuantity);
        return logForMerchantModification();
    }

    private Book logForCustomerModification(final UUID customerId) {
        modifiedAt = Instant.now();
        modifiedBy = String.format("customerId: %s", customerId);
        return this;
    }

    private Book logForMerchantModification() {
        modifiedAt = Instant.now();
        modifiedBy = String.format("merchantId: %s", merchant.getId());
        return this;
    }
}
