package com.getir.readingisgood.domain.book.model.aggregate.builder.args;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookBuilderWithOwnershipArgs {

    private UUID id;

    private UUID merchantId;
}
