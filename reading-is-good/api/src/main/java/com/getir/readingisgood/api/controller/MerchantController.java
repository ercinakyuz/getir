package com.getir.readingisgood.api.controller;

import an.awesome.pipelinr.Pipeline;
import com.getir.readingisgood.api.model.merchant.request.AuthenticateMerchantWithCredentialRequest;
import com.getir.readingisgood.api.model.merchant.request.AuthenticateMerchantWithRefreshTokenRequest;
import com.getir.readingisgood.api.model.merchant.request.RegisterMerchantRequest;
import com.getir.readingisgood.api.model.merchant.response.AuthenticateMerchantWithCredentialResponse;
import com.getir.readingisgood.api.model.merchant.response.AuthenticateMerchantWithRefreshTokenResponse;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.AuthenticateMerchantWithCredentialsCommand;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithcredentials.command.result.AuthenticateMerchantWithCredentialsCommandResult;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.AuthenticateMerchantWithRefreshTokenCommand;
import com.getir.readingisgood.application.usecase.merchant.authenticatewithrefreshtoken.command.result.AuthenticateMerchantWithRefreshTokenCommandResult;
import com.getir.readingisgood.application.usecase.merchant.register.command.RegisterMerchantCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("merchant")
@RequiredArgsConstructor
@Validated
public class MerchantController {
    private final Pipeline pipeline;

    @PostMapping("authentication/credentials")
    public ResponseEntity<AuthenticateMerchantWithCredentialResponse> authenticateWithCredentials(final @Valid @RequestBody AuthenticateMerchantWithCredentialRequest request) {
        final AuthenticateMerchantWithCredentialsCommandResult commandResult = pipeline.send(AuthenticateMerchantWithCredentialsCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
        return new ResponseEntity<>(AuthenticateMerchantWithCredentialResponse.builder()
                .result(commandResult.getAuthenticationContract())
                .build(), HttpStatus.OK);
    }

    @PostMapping("authentication/refresh-token")
    public ResponseEntity<AuthenticateMerchantWithRefreshTokenResponse> authenticateWithRefreshToken(final @Valid @RequestBody AuthenticateMerchantWithRefreshTokenRequest request) {
        final AuthenticateMerchantWithRefreshTokenCommandResult commandResult = pipeline.send(AuthenticateMerchantWithRefreshTokenCommand.builder()
                .refreshToken(request.getRefreshToken())
                .build());
        return new ResponseEntity<>(AuthenticateMerchantWithRefreshTokenResponse.builder()
                .result(commandResult.getAuthenticationContract())
                .build(), HttpStatus.OK);
    }

    @PostMapping("registration")
    public ResponseEntity<Void> register(final @Valid @RequestBody RegisterMerchantRequest request) {
        pipeline.send(RegisterMerchantCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}