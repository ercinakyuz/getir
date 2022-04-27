package com.getir.readingisgood.domain.book.model.aggregate.value.builder;

import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.domain.book.model.aggregate.value.dto.LoadMerchantDto;
import com.getir.readingisgood.domain.merchant.model.aggregate.Merchant;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.MerchantBuilder;
import com.getir.readingisgood.infrastructure.persistence.entity.order.MerchantOfOrderedBookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MerchantOfBookBuilder {

    private final MerchantBuilder merchantBuilder;

    public MerchantOfBook buildFromEntity(final MerchantOfOrderedBookEntity merchantEntity){
        return MerchantOfBook.load(LoadMerchantDto.builder()
                .id(merchantEntity.getId())
                .email(merchantEntity.getEmail())
                .build());
    }

    public MerchantOfBook build(final UUID merchantId){
        final Merchant merchant = merchantBuilder.build(merchantId);
        return MerchantOfBook.load(LoadMerchantDto.builder()
                .id(merchant.getId())
                .email(merchant.getCredentials().getEmail())
                .build());
    }
}
