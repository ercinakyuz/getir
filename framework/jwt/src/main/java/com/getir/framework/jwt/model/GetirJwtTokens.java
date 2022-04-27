package com.getir.framework.jwt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetirJwtTokens {

    private String accessToken;

    private String refreshToken;
}
