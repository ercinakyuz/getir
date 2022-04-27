package com.getir.readingisgood.api.model.merchant.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.getir.readingisgood.api.model.merchant.request.error.MerchantRequestError.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMerchantRequest {

    @NotBlank(message = EMAIL_IS_NOT_VALID)
    private String email;

    @NotBlank(message = PASSWORD_IS_NOT_VALID)
    private String password;
}
