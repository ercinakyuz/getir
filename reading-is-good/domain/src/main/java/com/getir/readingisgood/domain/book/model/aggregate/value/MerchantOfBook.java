package com.getir.readingisgood.domain.book.model.aggregate.value;

import com.getir.framework.core.model.exception.ExceptionState;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.readingisgood.domain.book.model.aggregate.value.dto.LoadMerchantDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

import static com.getir.readingisgood.domain.book.model.aggregate.value.error.MerchantOfBookError.DOES_NOT_BELONG_TO_MERCHANT;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class MerchantOfBook {

    private UUID id;

    private String email;

    public static MerchantOfBook load(final LoadMerchantDto loadMerchantDto) {
        validateLoadMerchantDto(loadMerchantDto);
        return MerchantOfBook.builder()
                .id(loadMerchantDto.getId())
                .email(loadMerchantDto.getEmail())
                .build();
    }

    private static void validateLoadMerchantDto(final LoadMerchantDto loadMerchantDto) {

    }

    public MerchantOfBook validate(final UUID merchantId){
        if (!id.equals(merchantId))
            throw new DomainException(ExceptionState.UNPROCESSABLE, DOES_NOT_BELONG_TO_MERCHANT);
        return this;
    }
}
