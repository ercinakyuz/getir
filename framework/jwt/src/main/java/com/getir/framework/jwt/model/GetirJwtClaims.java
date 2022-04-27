package com.getir.framework.jwt.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetirJwtClaims {

    private UUID memberId;
}

