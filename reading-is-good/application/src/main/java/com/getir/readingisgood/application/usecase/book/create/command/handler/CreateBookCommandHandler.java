package com.getir.readingisgood.application.usecase.book.create.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.book.create.command.CreateBookCommand;
import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.builder.BookBuilder;
import com.getir.readingisgood.domain.book.model.aggregate.builder.args.NewBookBuilderArgs;
import com.getir.readingisgood.domain.book.model.aggregate.unitofwork.BookOfWork;
import com.getir.readingisgood.domain.book.model.aggregate.value.builder.args.SaleBuilderArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateBookCommandHandler implements Command.Handler<CreateBookCommand, UUID> {

    private final BookBuilder bookBuilder;

    private final BookOfWork bookOfWork;

    @Override
    public UUID handle(final CreateBookCommand createBookCommand) {

        final Book book = bookBuilder.buildNew(NewBookBuilderArgs.builder()
                .merchantId(createBookCommand.getMerchantId())
                .name(createBookCommand.getName())
                .author(createBookCommand.getAuthor())
                .sale(SaleBuilderArgs.builder()
                        .price(createBookCommand.getPrice())
                        .quantity(createBookCommand.getQuantity())
                        .build())
                .build());

        bookOfWork.Insert(book);

        return book.getId();
    }
}
