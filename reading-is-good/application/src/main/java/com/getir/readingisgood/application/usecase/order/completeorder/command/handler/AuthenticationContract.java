package com.getir.readingisgood.application.usecase.order.completeorder.command.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationContract {

    private String accessToken;

    private String refreshToken;
}