package com.getir.readingisgood.api.model.order.request;

import com.getir.readingisgood.api.model.order.request.error.OrderRequestError;
import com.getir.readingisgood.application.usecase.order.completeorder.command.CompleteOrderItemCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderRequest {

    @NotEmpty(message = OrderRequestError.EMPTY_ITEMS)
    private List<@Valid CompleteOrderItemCommand> items;
}
