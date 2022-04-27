package com.getir.framework.domain.model.error;


import com.getir.framework.domain.model.error.dto.LoadDomainErrorDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DomainError {

    private String code;

    private String message;

    public static DomainError load(final LoadDomainErrorDto loadDomainErrorDto) {
        return DomainError.builder()
                .code(loadDomainErrorDto.getCode())
                .message(loadDomainErrorDto.getMessage())
                .build();
    }
}
