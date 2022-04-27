package com.getir.readingisgood.domain.model.value.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadCredentialDto {
    private String email;
    private String password;
}
