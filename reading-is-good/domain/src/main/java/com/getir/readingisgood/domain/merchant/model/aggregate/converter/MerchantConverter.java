package com.getir.readingisgood.domain.merchant.model.aggregate.converter;

import com.getir.framework.domain.model.aggregate.converter.GenericAggregateConverter;
import com.getir.readingisgood.domain.merchant.model.aggregate.Merchant;
import com.getir.readingisgood.domain.model.value.converter.CredentialsConverter;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantConverter extends GenericAggregateConverter<Merchant, MerchantEntity> {

    private final CredentialsConverter credentialsConverter;

    @Override
    public MerchantEntity convert(final Merchant merchant) {
        return MerchantEntity.builder()
                .id(merchant.getId())
                .credentials(credentialsConverter.convert(merchant.getCredentials()))
                .build();
    }
}

