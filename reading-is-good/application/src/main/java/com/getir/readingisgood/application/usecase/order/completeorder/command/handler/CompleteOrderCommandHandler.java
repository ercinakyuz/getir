package com.getir.readingisgood.application.usecase.order.completeorder.command.handler;

import an.awesome.pipelinr.Command;
import com.getir.framework.domain.model.exception.DomainException;
import com.getir.framework.locking.impl.Locked;
import com.getir.readingisgood.application.usecase.order.completeorder.command.CompleteOrderCommand;
import com.getir.readingisgood.domain.book.locker.BookQuantityLocker;
import com.getir.readingisgood.domain.book.service.BookQuantityUpdateService;
import com.getir.readingisgood.domain.book.service.dto.DecreaseBookQuantityServiceDto;
import com.getir.readingisgood.domain.order.model.aggregate.Order;
import com.getir.readingisgood.domain.order.model.aggregate.builder.OrderBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.builder.args.NewOrderBuilderArgs;
import com.getir.readingisgood.domain.order.model.aggregate.unitofwork.OrderOfWork;
import com.getir.readingisgood.domain.order.model.aggregate.value.OrderItem;
import com.getir.readingisgood.domain.order.model.aggregate.value.builder.args.NewOrderItemBuilderArgs;
import com.getir.readingisgood.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static com.getir.readingisgood.application.usecase.order.completeorder.command.handler.error.CompleteOrderCommandHandlerError.INSUFFICIENT_BOOK_STOCK;

@Component
@RequiredArgsConstructor
public class CompleteOrderCommandHandler implements Command.Handler<CompleteOrderCommand, UUID> {

    private final OrderBuilder orderBuilder;

    private final OrderOfWork orderOfWork;

    private final BookQuantityUpdateService bookQuantityUpdateService;

    private final PaymentService paymentService;

    private final BookQuantityLocker bookQuantityLocker;

    @Transactional
    @Override
    public UUID handle(final CompleteOrderCommand completeOrderCommand) {
        final Order order = orderBuilder.buildNew(NewOrderBuilderArgs.builder()
                .customerId(completeOrderCommand.getCustomerId())
                .items(completeOrderCommand.getItems().stream().map(completeOrderItemCommand -> NewOrderItemBuilderArgs.builder()
                        .bookId(completeOrderItemCommand.getBookId())
                        .quantity(completeOrderItemCommand.getQuantity())
                        .build()).collect(Collectors.toList()))
                .build());
        final Map<UUID, Integer> decreaseBookQuantityMap = order.getItems()
                .stream().collect(Collectors.toMap(orderItem -> orderItem.getBook().getId(), OrderItem::getQuantity));
        try (var locked = bookQuantityLocker.lockMultiple(decreaseBookQuantityMap.keySet())) {
            tryDecreaseBookQuantities(order.getCustomer().getId(), decreaseBookQuantityMap);
            orderOfWork.insert(order);
            paymentService.pay();
        }
        return order.getId();
    }

    private void tryDecreaseBookQuantities(final UUID customerId, final Map<UUID, Integer> decreaseBookQuantityMap) {
        try {
            for (final var entry : decreaseBookQuantityMap.entrySet()) {
                bookQuantityUpdateService.decrease(DecreaseBookQuantityServiceDto.builder()
                        .id(entry.getKey())
                        .customerId(customerId)
                        .quantity(entry.getValue())
                        .build());
            }
        } catch (DomainException domainException) {
            throw INSUFFICIENT_BOOK_STOCK;
        }
    }
}
