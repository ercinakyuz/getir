package com.getir.readingisgood.application.usecase.book.decreasequantity.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.readingisgood.application.usecase.book.decreasequantity.command.DecreaseBookQuantityCommand;
import com.getir.readingisgood.domain.book.locker.BookQuantityLocker;
import com.getir.readingisgood.domain.book.model.aggregate.Book;
import com.getir.readingisgood.domain.book.model.aggregate.builder.BookBuilder;
import com.getir.readingisgood.domain.book.model.aggregate.builder.args.BookBuilderWithOwnershipArgs;
import com.getir.readingisgood.domain.book.model.aggregate.unitofwork.BookOfWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Component
@RequiredArgsConstructor
public class DecreaseBookQuantityCommandHandler implements Command.Handler<DecreaseBookQuantityCommand, Void> {

    private final BookBuilder bookBuilder;

    private final BookOfWork bookOfWork;

    private final BookQuantityLocker bookQuantityLocker;

    @Override
    public Void handle(final DecreaseBookQuantityCommand decreaseBookQuantityCommand) {
        final Lock lockedBook = bookQuantityLocker.lock(decreaseBookQuantityCommand.getId());
        try {
            final Book book = bookBuilder.buildWithOwnership(BookBuilderWithOwnershipArgs.builder()
                    .id(decreaseBookQuantityCommand.getId())
                    .merchantId(decreaseBookQuantityCommand.getMerchantId())
                    .build())
                    .decreaseQuantity(decreaseBookQuantityCommand.getQuantity());
            bookOfWork.update(book);
        }
        finally {
            lockedBook.unlock();
        }
        return null;
    }
}
