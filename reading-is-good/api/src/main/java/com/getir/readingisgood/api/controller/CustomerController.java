package com.getir.readingisgood.api.controller;

import com.getir.readingisgood.api.model.customer.request.RegisterCustomerRequest;
import com.getir.readingisgood.api.model.customer.response.AuthenticateCustomerWithCredentialResponse;
import com.getir.readingisgood.api.model.customer.response.AuthenticateCustomerWithRefreshTokenResponse;
import com.getir.readingisgood.api.model.customer.request.AuthenticateCustomerWithCredentialsRequest;
import com.getir.readingisgood.api.model.customer.request.AuthenticateCustomerWithRefreshTokenRequest;
import com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.AuthenticateCustomerWithCredentialsCommand;
import com.getir.readingisgood.application.usecase.customer.authenticatewithcredentials.command.result.AuthenticateCustomerWithCredentialsCommandResult;
import com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.AuthenticateCustomerWithRefreshTokenCommand;
import com.getir.readingisgood.application.usecase.customer.authenticatewithrefreshtoken.command.result.AuthenticateCustomerWithRefreshTokenCommandResult;
import an.awesome.pipelinr.Pipeline;
import com.getir.readingisgood.application.usecase.customer.register.command.RegisterCustomerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
    private final Pipeline pipeline;

    @PostMapping("authentication/credentials")
    public ResponseEntity<AuthenticateCustomerWithCredentialResponse> authenticateWithCredentials(final @Valid @RequestBody AuthenticateCustomerWithCredentialsRequest authenticateWithCredentialRequest) {
        final AuthenticateCustomerWithCredentialsCommandResult authenticateCommandResult = pipeline.send(AuthenticateCustomerWithCredentialsCommand.builder()
                .email(authenticateWithCredentialRequest.getEmail())
                .password(authenticateWithCredentialRequest.getPassword())
                .build());
        return new ResponseEntity<>(AuthenticateCustomerWithCredentialResponse.builder()
                .result(authenticateCommandResult.getAuthenticationContract())
                .build(), HttpStatus.OK);
    }

    @PostMapping("authentication/refresh-token")
    public ResponseEntity<AuthenticateCustomerWithRefreshTokenResponse> authenticateWithRefreshToken(final @Valid @RequestBody AuthenticateCustomerWithRefreshTokenRequest authenticateWithRefreshTokenRequest) {
        final AuthenticateCustomerWithRefreshTokenCommandResult authenticateWithRefreshTokenCommandResult = pipeline.send(AuthenticateCustomerWithRefreshTokenCommand.builder()
                .refreshToken(authenticateWithRefreshTokenRequest.getRefreshToken())
                .build());
        return new ResponseEntity<>(AuthenticateCustomerWithRefreshTokenResponse.builder()
                .result(authenticateWithRefreshTokenCommandResult.getAuthenticationContract())
                .build(), HttpStatus.OK);
    }

    @PostMapping("registration")
    public ResponseEntity<Void> register(final @Valid @RequestBody RegisterCustomerRequest registerCustomerRequest) {
        pipeline.send(RegisterCustomerCommand.builder()
                .email(registerCustomerRequest.getEmail())
                .password(registerCustomerRequest.getPassword())
                .build());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}