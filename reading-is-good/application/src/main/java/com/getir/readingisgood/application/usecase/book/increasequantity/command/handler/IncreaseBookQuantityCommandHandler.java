package com.getir.readingisgood.application.usecase.book.increasequantity.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.framework.locking.impl.Locked;
import com.getir.readingisgood.application.usecase.book.increasequantity.command.IncreaseBookQuantityCommand;
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
public class IncreaseBookQuantityCommandHandler implements Command.Handler<IncreaseBookQuantityCommand, Void> {

    private final BookBuilder bookBuilder;

    private final BookOfWork bookOfWork;

    private final BookQuantityLocker bookQuantityLocker;

    @Override
    public Void handle(final IncreaseBookQuantityCommand increaseBookQuantityCommand) {
        try (var ignored = bookQuantityLocker.lock(increaseBookQuantityCommand.getId())) {
            final Book book = bookBuilder.buildWithOwnership(BookBuilderWithOwnershipArgs.builder()
                            .id(increaseBookQuantityCommand.getId())
                            .merchantId(increaseBookQuantityCommand.getMerchantId())
                            .build())
                    .increaseQuantity(increaseBookQuantityCommand.getQuantity());
            bookOfWork.update(book);
        }
        return null;
    }
}
