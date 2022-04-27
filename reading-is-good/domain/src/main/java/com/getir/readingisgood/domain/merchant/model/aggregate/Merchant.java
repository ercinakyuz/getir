package com.getir.readingisgood.domain.merchant.model.aggregate;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.aggregate.GenericAggregateRoot;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.merchant.error.MerchantError;
import com.getir.readingisgood.domain.merchant.model.aggregate.dto.CreateMerchantDto;
import com.getir.readingisgood.domain.merchant.model.aggregate.dto.LoadMerchantDto;
import com.getir.readingisgood.domain.merchant.model.aggregate.state.MerchantState;
import com.getir.readingisgood.domain.model.value.Credentials;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static com.getir.framework.core.model.exception.ExceptionState.INVALID;
import static com.getir.readingisgood.domain.merchant.model.aggregate.state.MerchantState.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Merchant extends GenericAggregateRoot<Merchant, UUID, MerchantState> {

    private final Credentials credentials;

    public static Merchant load(final LoadMerchantDto loadMerchantDto) {

        validateLoadMerchantDto(loadMerchantDto);

        final Merchant merchant = Merchant.builder()
                .credentials(loadMerchantDto.getCredential())
                .build();

        merchant.id = loadMerchantDto.getId();
        merchant.createdAt = loadMerchantDto.getCreatedAt();
        merchant.createdBy = loadMerchantDto.getCreatedBy();
        merchant.modifiedAt = loadMerchantDto.getModifiedAt();
        merchant.modifiedBy = loadMerchantDto.getModifiedBy();

        return merchant.changeState(LOADED);
    }

    public static Merchant create(final CreateMerchantDto createMerchantDto) {

        validateCreateMerchantDto(createMerchantDto);

        final Merchant merchant = Merchant.builder()
                .credentials(createMerchantDto.getCredential())
                .build();

        merchant.id = UUID.randomUUID();
        merchant.createdAt = Instant.now();
        merchant.createdBy = "Creator";

        return merchant.changeState(CREATED);
    }

    private static void validateCreateMerchantDto(final CreateMerchantDto createMerchantDto) {
    }

    private static void validateLoadMerchantDto(final LoadMerchantDto loadMerchantDto) {

    }

    public Merchant validatePassword(final String password) {
        if (!credentials.isValidPassword(password))
            throw new DomainException(INVALID, MerchantError.INVALID_PASSWORD);
        return this;
    }
}
