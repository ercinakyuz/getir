package com.getir.readingisgood.application.usecase.order.completeorder.command;

import com.getir.readingisgood.application.usecase.order.error.OrderCommandError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderItemCommand {

    @NotNull(message = OrderCommandError.INVALID_BOOK_ID)
    private UUID bookId;

    @Positive(message = OrderCommandError.INVALID_QUANTITY)
    private int quantity;
}
