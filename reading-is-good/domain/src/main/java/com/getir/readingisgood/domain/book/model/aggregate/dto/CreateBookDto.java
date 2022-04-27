package com.getir.readingisgood.domain.book.model.aggregate.dto;

import com.getir.framework.domain.model.aggregate.dto.AbstractCreateAggregateDto;
import com.getir.readingisgood.domain.book.model.aggregate.value.MerchantOfBook;
import com.getir.readingisgood.domain.book.model.aggregate.value.Sale;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CreateBookDto extends AbstractCreateAggregateDto {

    private String name;

    private MerchantOfBook merchant;

    private String author;

    private Sale sale;


}
