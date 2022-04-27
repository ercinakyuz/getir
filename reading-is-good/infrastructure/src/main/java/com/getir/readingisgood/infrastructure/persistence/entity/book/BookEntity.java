package com.getir.readingisgood.infrastructure.persistence.entity.book;

import com.getir.framework.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "unique_merchantId_name_author", unique = true, def = "{'merchant.id': 1, 'name': 1, 'author': 1}")
@Document("books")
public class BookEntity extends AbstractEntity {

    private MerchantOfBookEntity merchant;

    private String name;

    private String author;

    private SaleEntity sale;

}
