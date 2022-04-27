package com.getir.readingisgood.api.model.book.response;

import com.getir.framework.core.model.response.AbstractResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class CreateBookResponse extends AbstractResponse<UUID> {
}
