package com.getir.readingisgood.api.controller;

import an.awesome.pipelinr.Pipeline;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.readingisgood.api.model.order.request.CompleteOrderRequest;
import com.getir.readingisgood.api.model.order.response.CompleteOrderResponse;
import com.getir.readingisgood.api.model.order.response.GetOrderListResponse;
import com.getir.readingisgood.api.model.order.response.GetOrderResponse;
import com.getir.readingisgood.application.usecase.order.completeorder.command.CompleteOrderCommand;
import com.getir.readingisgood.application.usecase.order.get.command.GetOrderCommand;
import com.getir.readingisgood.application.usecase.order.get.command.result.GetOrderCommandResult;
import com.getir.readingisgood.application.usecase.order.getlist.command.GetOrderListCommand;
import com.getir.readingisgood.application.usecase.order.getlist.command.result.GetOrderListCommandResult;
import com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.GetOrderListByDateIntervalCommand;
import com.getir.readingisgood.application.usecase.order.getlistbydateinterval.command.result.GetOrderListByDateIntervalCommandResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.getir.framework.data.pagination.util.PageUtil.toCustomPage;
import static com.getir.readingisgood.infrastructure.DateUtil.toSystemInstant;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final Pipeline pipeline;

    @PostMapping("complete")
    public ResponseEntity<CompleteOrderResponse> complete(@AuthenticationPrincipal final GetirJwtClaims principal, final @Valid @RequestBody CompleteOrderRequest request) {
        final UUID orderId = pipeline.send(CompleteOrderCommand.builder()
                .customerId(principal.getMemberId())
                .items(request.getItems())
                .build());
        return new ResponseEntity<>(CompleteOrderResponse.builder().result(orderId).build(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetOrderResponse> get(@AuthenticationPrincipal final GetirJwtClaims principal, final @PathVariable UUID id) {
        final GetOrderCommandResult commandResult = pipeline.send(GetOrderCommand.builder()
                .customerId(principal.getMemberId())
                .id(id)
                .build());
        return new ResponseEntity<>(GetOrderResponse.builder().result(commandResult.getOrder()).build(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GetOrderListResponse> list(
            @AuthenticationPrincipal final GetirJwtClaims principal,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable) {
        final GetOrderListCommandResult commandResult = pipeline.send(GetOrderListCommand.builder()
                .customerId(principal.getMemberId())
                .pageable(pageable)
                .build());
        return new ResponseEntity<>(GetOrderListResponse.builder().result(toCustomPage(commandResult.getPagedOrders())).build(), HttpStatus.OK);
    }

    @GetMapping("date-interval")
    public ResponseEntity<GetOrderListResponse> listByDateInterval(
            @AuthenticationPrincipal final GetirJwtClaims principal,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) final LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) final LocalDateTime end,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable) {

        final GetOrderListByDateIntervalCommandResult commandResult = pipeline.send(GetOrderListByDateIntervalCommand.builder()
                .customerId(principal.getMemberId())
                .start(toSystemInstant(start))
                .end(toSystemInstant(end))
                .pageable(pageable)
                .build());
        return new ResponseEntity<>(GetOrderListResponse.builder().result(toCustomPage(commandResult.getOrders())).build(), HttpStatus.OK);
    }

}
