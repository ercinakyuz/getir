package com.getir.readingisgood.domain.order.model.aggregate.value;

import com.getir.readingisgood.domain.order.model.aggregate.value.dto.CreateOrderItemDto;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.LoadOrderItemDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class OrderItem {

    private UUID id;

    private BookOfOrderItem book;

    private int quantity;

    public static OrderItem load(final LoadOrderItemDto loadDto) {
        validateLoadDto(loadDto);
        return OrderItem.builder()
                .id(loadDto.getId())
                .book(loadDto.getBook())
                .quantity(loadDto.getQuantity())
                .build();
    }

    public static OrderItem create(final CreateOrderItemDto createDto) {
        validateCreateDto(createDto);
        return OrderItem.builder()
                .id(randomUUID())
                .book(createDto.getBook())
                .quantity(createDto.getQuantity())
                .build();
    }

    public BigDecimal getPrice(){
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    private static void validateLoadDto(final LoadOrderItemDto loadDto) {

    }

    private static void validateCreateDto(final CreateOrderItemDto createDto) {

    }
}
