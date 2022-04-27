package com.getir.readingisgood.domain.merchant.model.aggregate.builder;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.aggregate.builder.AbstractAggregateBuilder;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.merchant.error.MerchantError;
import com.getir.readingisgood.domain.merchant.model.aggregate.Merchant;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.args.BuildMerchantWithCredentialsArgs;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.args.BuildNewMerchantArgs;
import com.getir.readingisgood.domain.merchant.model.aggregate.dto.CreateMerchantDto;
import com.getir.readingisgood.domain.merchant.model.aggregate.dto.LoadMerchantDto;
import com.getir.readingisgood.domain.model.value.builder.CredentialsBuilder;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.getir.framework.core.model.exception.ExceptionState.UNPROCESSABLE;
import static com.getir.readingisgood.domain.merchant.error.MerchantError.INVALID_EMAIL;
import static com.getir.readingisgood.domain.merchant.error.MerchantError.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class MerchantBuilder extends AbstractAggregateBuilder {

    private final MerchantRepository merchantRepository;
    private final CredentialsBuilder credentialBuilder;

    public Optional<Merchant> buildOptional(final UUID id) {
        return merchantRepository.findById(id).map(this::fromEntity);
    }

    public Merchant build(final UUID id) {
        return buildOptional(id).orElseThrow(() -> new DomainException(UNPROCESSABLE, NOT_FOUND));
    }
    public Merchant buildWithCredentials(final BuildMerchantWithCredentialsArgs args) {
        final BuildCredentialsArgs buildCredentialsArgs = args.getCredentials();
        final MerchantEntity merchantEntity = merchantRepository.findByCredentialsEmail(buildCredentialsArgs.getEmail())
                .orElseThrow(() -> new DomainException(ExceptionState.INVALID, INVALID_EMAIL));
        return fromEntity(merchantEntity).validatePassword(buildCredentialsArgs.getPassword());
    }

    public Merchant buildNew(final BuildNewMerchantArgs args) {
        return Merchant.create(CreateMerchantDto.builder()
                .credential(credentialBuilder.buildNewCredentials(args.getCredentials()))
                .build());
    }

    private Merchant fromEntity(final MerchantEntity merchantEntity){
        final LoadMerchantDto loadMerchantDto = LoadMerchantDto.builder()
                .id(merchantEntity.getId())
                .credential(credentialBuilder.buildCredentials(merchantEntity.getCredentials()))
                .build();
        loadAbstractProperties(loadMerchantDto,merchantEntity);
        return Merchant.load(loadMerchantDto);
    }
}
