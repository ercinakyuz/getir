package com.getir.readingisgood.api.controller;

import an.awesome.pipelinr.Pipeline;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.readingisgood.api.model.order.response.GetOrderStatisticsResponse;
import com.getir.readingisgood.application.usecase.order.getstatistics.command.GetStatisticsCommand;
import com.getir.readingisgood.application.usecase.order.getstatistics.command.result.GetStatisticsCommandResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
@Validated
public class StatisticsController {

    private final Pipeline pipeline;

    @GetMapping("order")
    public ResponseEntity<GetOrderStatisticsResponse> list(@AuthenticationPrincipal final GetirJwtClaims principal, @RequestParam int year) {
        final GetStatisticsCommandResult commandResult = pipeline.send(GetStatisticsCommand.builder()
                .customerId(principal.getMemberId())
                .year(year)
                .build());
        return new ResponseEntity<>(GetOrderStatisticsResponse.builder().result(commandResult.getOrderStatistics()).build(), HttpStatus.OK);
    }
}
