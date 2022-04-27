package com.getir.readingisgood.domain.order.model.aggregate.value;

import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.LoadBookOfOrderItemDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class BookOfOrderItem {

    private UUID id;

    private String name;

    private String author;

    private BigDecimal price;

    private MerchantOfBook merchant;

    public static BookOfOrderItem load(final LoadBookOfOrderItemDto loadDto) {
        validateLoadDto(loadDto);
        return BookOfOrderItem.builder()
                .id(loadDto.getId())
                .name(loadDto.getName())
                .author(loadDto.getAuthor())
                .price(loadDto.getPrice())
                .merchant(loadDto.getMerchant())
                .build();
    }

    private static void validateLoadDto(final LoadBookOfOrderItemDto loadDto) {

    }
}
