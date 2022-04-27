package com.getir.readingisgood.domain.book.model.aggregate.value;

import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.book.model.aggregate.value.dto.LoadSaleDto;
import com.getir.readingisgood.domain.book.model.aggregate.value.error.SaleError;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import static com.getir.framework.core.model.exception.ExceptionState.INVALID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Sale {

    private static final int MINIMUM_QUANTITY = 0;
    private BigDecimal price;

    private int quantity;

    public static Sale load(final LoadSaleDto loadDto) {
        validateLoadDto(loadDto);
        return Sale.builder()
                .price(loadDto.getPrice())
                .quantity(loadDto.getQuantity())
                .build();
    }

    private static void validateLoadDto(final LoadSaleDto loadDto) {

    }

    public Sale decreaseQuantity(final int decreaseQuantity) {
        return changeQuantity(quantity - decreaseQuantity);
    }

    public Sale increaseQuantity(final int increaseQuantity) {
        return changeQuantity(quantity + increaseQuantity);
    }

    private Sale changeQuantity(final int quantity) {
        validateQuantity(quantity).quantity = quantity;
        return this;
    }

    private Sale validateQuantity(final int quantity) {
        if (!isValidQuantity(quantity))
            throw new DomainException(INVALID, SaleError.INVALID_QUANTITY);
        return this;
    }
    private boolean isValidQuantity(final int quantity) {
        return quantity >= MINIMUM_QUANTITY;
    }
}
