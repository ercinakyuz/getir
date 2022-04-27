package com.getir.readingisgood.api.model.customer.request;

import com.getir.readingisgood.api.model.customer.request.error.CustomerRequestError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateCustomerWithRefreshTokenRequest {

    @NotBlank(message = CustomerRequestError.REFRESH_TOKEN_IS_NOT_VALID)
    private String refreshToken;
}