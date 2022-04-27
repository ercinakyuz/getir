package com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.readingisgood.application.usecase.order.completeorder.command.handler.AuthenticationContract;
import com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.AuthenticateCustomerWithCredentialsCommand;
import com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.result.AuthenticateCustomerWithCredentialsCommandResult;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.CustomerBuilder;
import com.getir.readingisgood.domain.model.value.builder.args.BuildCredentialsArgs;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.args.BuildCustomerWithCredentialsArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateWithCredentialCommandHandler implements Command.Handler<AuthenticateCustomerWithCredentialsCommand, AuthenticateCustomerWithCredentialsCommandResult> {

    private final CustomerBuilder customerBuilder;
    private final JwtTokenBuilder jwtTokenBuilder;

    @Override
    public AuthenticateCustomerWithCredentialsCommandResult handle(final AuthenticateCustomerWithCredentialsCommand authenticateMemberCommand) {

        final var customer = customerBuilder.buildWithCredentials(
                BuildCustomerWithCredentialsArgs.builder()
                        .credentials(BuildCredentialsArgs.builder()
                                .email(authenticateMemberCommand.getEmail())
                                .password(authenticateMemberCommand.getPassword())
                                .build())
                        .build());

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customer.getId())
                .build());

        return AuthenticateCustomerWithCredentialsCommandResult.builder()
                .authenticationContract(AuthenticationContract.builder()
                        .accessToken(getirJwtTokens.getAccessToken())
                        .refreshToken(getirJwtTokens.getRefreshToken())
                        .build())
                .build();
    }
}
