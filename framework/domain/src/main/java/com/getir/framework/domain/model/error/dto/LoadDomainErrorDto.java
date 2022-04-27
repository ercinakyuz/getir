package com.getir.framework.domain.model.error.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadDomainErrorDto {
    private String code;

    private String message;

    private String resourceKey;
}
