package com.getir.readingisgood.application.contract.mapper;


import com.getir.readingisgood.application.contract.BookOfOrderItemContract;
import com.getir.readingisgood.domain.order.model.aggregate.value.BookOfOrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookOfOrderItemContractMapper {

private final MerchantOfBookOrderItemContractMapper merchantOfBookOrderItemContractMapper;

    public BookOfOrderItemContract map(final BookOfOrderItem book) {
        return BookOfOrderItemContract.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .price(book.getPrice())
                .merchant(merchantOfBookOrderItemContractMapper.map(book.getMerchant()))
                .build();
    }
}
