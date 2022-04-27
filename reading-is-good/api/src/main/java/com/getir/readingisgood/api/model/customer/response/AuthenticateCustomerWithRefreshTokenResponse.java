package com.getir.readingisgood.api.model.customer.response;


import com.getir.framework.core.model.response.AbstractResponse;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class AuthenticateCustomerWithRefreshTokenResponse extends AbstractResponse<AuthenticationContract> {
}