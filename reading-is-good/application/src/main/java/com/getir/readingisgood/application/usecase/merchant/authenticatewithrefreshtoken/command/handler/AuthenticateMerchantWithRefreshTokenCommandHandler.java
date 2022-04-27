package com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.framework.jwt.resolver.RefreshTokenResolver;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.AuthenticateMerchantWithRefreshTokenCommand;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.result.AuthenticateMerchantWithRefreshTokenCommandResult;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.MerchantBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.handler.error.AuthenticateMerchantWithRefreshTokenCommandHandlerError.MERCHANT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AuthenticateMerchantWithRefreshTokenCommandHandler implements Command.Handler<AuthenticateMerchantWithRefreshTokenCommand, AuthenticateMerchantWithRefreshTokenCommandResult> {

    private final RefreshTokenResolver refreshTokenResolver;
    private final MerchantBuilder merchantBuilder;
    private final JwtTokenBuilder jwtTokenBuilder;

    @Override
    public AuthenticateMerchantWithRefreshTokenCommandResult handle(final AuthenticateMerchantWithRefreshTokenCommand authenticateWithRefreshTokenCommand) {

        final GetirJwtClaims odeAlJwtClaims = refreshTokenResolver.resolve(authenticateWithRefreshTokenCommand.getRefreshToken());

        final var merchant= merchantBuilder.buildOptional(odeAlJwtClaims.getMemberId()).orElseThrow(() -> MERCHANT_NOT_FOUND);

        final GetirJwtTokens odeAlJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(merchant.getId())
                .build());

        return AuthenticateMerchantWithRefreshTokenCommandResult.builder()
                .authenticationContract(AuthenticationContract.builder()
                        .accessToken(odeAlJwtTokens.getAccessToken())
                        .refreshToken(odeAlJwtTokens.getRefreshToken())
                        .build())
                .build();
    }
}