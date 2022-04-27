package com.getir.readingisgood.api.controller;

import an.awesome.pipelinr.Pipeline;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.readingisgood.api.model.book.request.ChangeBookQuantityRequest;
import com.getir.readingisgood.api.model.book.request.CreateBookRequest;
import com.getir.readingisgood.api.model.book.response.CreateBookResponse;
import com.getir.readingisgood.api.model.customer.response.AuthenticateCustomerWithCredentialResponse;
import com.getir.readingisgood.application.usecase.book.decreasequantity.command.DecreaseBookQuantityCommand;
import com.getir.readingisgood.application.usecase.book.increasequantity.command.IncreaseBookQuantityCommand;
import com.getir.readingisgood.application.usecase.book.create.command.CreateBookCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity<CreateBookResponse> create(@AuthenticationPrincipal final GetirJwtClaims principal, final @Valid @RequestBody CreateBookRequest request) {
        final UUID bookId = pipeline.send(CreateBookCommand.builder()
                .merchantId(principal.getMemberId())
                .name(request.getName())
                .author(request.getAuthor())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build());
        return new ResponseEntity<>(CreateBookResponse.builder().result(bookId).build(), HttpStatus.CREATED);
    }

    @PatchMapping("{id}/quantity/increase")
    public ResponseEntity<Void> increaseQuantity(
            @AuthenticationPrincipal final GetirJwtClaims principal,
            @PathVariable final UUID id,
            @Valid @RequestBody final ChangeBookQuantityRequest request) {
        pipeline.send(IncreaseBookQuantityCommand.builder()
                .merchantId(principal.getMemberId())
                .id(id)
                .quantity(request.getQuantity())
                .build());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/quantity/decrease")
    public ResponseEntity<Void> decreaseQuantity(
            @AuthenticationPrincipal final GetirJwtClaims principal,
            @PathVariable final UUID id,
            @Valid @RequestBody final ChangeBookQuantityRequest request) {
        pipeline.send(DecreaseBookQuantityCommand.builder()
                .merchantId(principal.getMemberId())
                .id(id)
                .quantity(request.getQuantity())
                .build());
        return ResponseEntity.ok().build();
    }
}
