package com.getir.readingisgood.api.model.order.response;

import com.getir.framework.core.model.response.AbstractResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class CompleteOrderResponse extends AbstractResponse<UUID> {
}
