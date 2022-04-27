package com.getir.readingisgood.domain.book.model.aggregate.dto;

import com.getir.framework.domain.model.aggregate.dto.AbstractLoadAggregateDto;
import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.domain.book.model.aggregate.value.Sale;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class LoadBookDto extends AbstractLoadAggregateDto {

    private UUID id;

    private String name;

    private MerchantOfBook merchant;

    private String author;

    private Sale sale;
}
