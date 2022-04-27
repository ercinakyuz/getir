package com.getir.readingisgood.api.model.merchant.response;


import com.getir.framework.core.model.response.AbstractResponse;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public class AuthenticateMerchantWithCredentialResponse extends AbstractResponse<AuthenticationContract> {

}
