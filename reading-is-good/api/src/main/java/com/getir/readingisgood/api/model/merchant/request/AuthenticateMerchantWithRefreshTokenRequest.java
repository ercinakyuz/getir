package com.getir.readingisgood.api.model.merchant.request;

import com.getir.readingisgood.api.model.merchant.request.error.MerchantRequestError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateMerchantWithRefreshTokenRequest {

    @NotBlank(message = MerchantRequestError.REFRESH_TOKEN_IS_NOT_VALID)
    private String refreshToken;
}