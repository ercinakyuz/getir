package com.getir.readingisgood.application.contract.mapper;


import com.getir.readingisgood.application.contract.MerchantOfBookOrderItemContract;
import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantOfBookOrderItemContractMapper {

    public MerchantOfBookOrderItemContract map(final MerchantOfBook merchant) {
        return MerchantOfBookOrderItemContract.builder()
                .id(merchant.getId())
                .email(merchant.getEmail())
                .build();
    }
}
