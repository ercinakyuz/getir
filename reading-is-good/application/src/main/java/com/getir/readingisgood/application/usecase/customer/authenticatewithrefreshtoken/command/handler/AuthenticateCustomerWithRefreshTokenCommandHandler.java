package com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.framework.jwt.resolver.RefreshTokenResolver;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.AuthenticateCustomerWithRefreshTokenCommand;
import com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.result.AuthenticateCustomerWithRefreshTokenCommandResult;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.CustomerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.handler.error.AuthenticateCustomerWithRefreshTokenCommandHandlerError.CUSTOMER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AuthenticateCustomerWithRefreshTokenCommandHandler implements Command.Handler<AuthenticateCustomerWithRefreshTokenCommand, AuthenticateCustomerWithRefreshTokenCommandResult> {

    private final RefreshTokenResolver refreshTokenResolver;
    private final CustomerBuilder customerBuilder;
    private final JwtTokenBuilder jwtTokenBuilder;

    @Override
    public AuthenticateCustomerWithRefreshTokenCommandResult handle(final AuthenticateCustomerWithRefreshTokenCommand authenticateWithRefreshTokenCommand) {

        final GetirJwtClaims odeAlJwtClaims = refreshTokenResolver.resolve(authenticateWithRefreshTokenCommand.getRefreshToken());

        final var customer= customerBuilder.buildOptional(odeAlJwtClaims.getMemberId()).orElseThrow(() -> CUSTOMER_NOT_FOUND);

        final GetirJwtTokens odeAlJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customer.getId())
                .build());

        return AuthenticateCustomerWithRefreshTokenCommandResult.builder()
                .authenticationContract(AuthenticationContract.builder()
                        .accessToken(odeAlJwtTokens.getAccessToken())
                        .refreshToken(odeAlJwtTokens.getRefreshToken())
                        .build())
                .build();
    }
}