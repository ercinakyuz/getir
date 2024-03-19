package com.getir.readingisgood.domain.merchant.model.aggregate.unitofwork;

import com.getir.framework.domain.unitofwork.AggregateOfWork;
import com.getir.readingisgood.domain.merchant.model.aggregate.Merchant;
import com.getir.readingisgood.domain.merchant.model.aggregate.converter.MerchantConverter;
import com.getir.readingisgood.infrastructure.persistence.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MerchantOfWork implements AggregateOfWork<Merchant> {

    private final MerchantConverter merchantConverter;

    private final MerchantRepository merchantRepository;

    @Override
    public Merchant insert(final Merchant merchant) {
        merchantRepository.save(merchantConverter.convertAll(merchant));
        return merchant;
    }

    @Override
    public Merchant update(final Merchant merchant) {
        merchantRepository.save(merchantConverter.convertAll(merchant));
        return merchant;
    }

    @Override
    public Merchant delete(final Merchant merchant) {
        merchantRepository.deleteById(merchant.getId());
        return merchant;
    }
}


