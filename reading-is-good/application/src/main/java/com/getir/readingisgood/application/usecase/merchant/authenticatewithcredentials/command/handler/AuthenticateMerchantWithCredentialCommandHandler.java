package com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.AuthenticateMerchantWithCredentialsCommand;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.result.AuthenticateMerchantWithCredentialsCommandResult;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.MerchantBuilder;
import com.getir.readingisgood.domain.merchant.model.aggregate.builder.args.BuildMerchantWithCredentialsArgs;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateMerchantWithCredentialCommandHandler implements Command.Handler<AuthenticateMerchantWithCredentialsCommand, AuthenticateMerchantWithCredentialsCommandResult> {

    private final MerchantBuilder merchantBuilder;
    private final JwtTokenBuilder jwtTokenBuilder;

    @Override
    public AuthenticateMerchantWithCredentialsCommandResult handle(final AuthenticateMerchantWithCredentialsCommand authenticateMemberCommand) {

        final var merchant = merchantBuilder.buildWithCredentials(
                BuildMerchantWithCredentialsArgs.builder()
                        .credentials(BuildCredentialsArgs.builder()
                                .email(authenticateMemberCommand.getEmail())
                                .password(authenticateMemberCommand.getPassword())
                                .build())
                        .build());

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(merchant.getId())
                .build());

        return AuthenticateMerchantWithCredentialsCommandResult.builder()
                .authenticationContract(AuthenticationContract.builder()
                        .accessToken(getirJwtTokens.getAccessToken())
                        .refreshToken(getirJwtTokens.getRefreshToken())
                        .build())
                .build();
    }
}
