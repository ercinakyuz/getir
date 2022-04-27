package com.getir.readingisgood.infrastructure.persistence.entity.order;


import com.getir.framework.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "index_customerId_createdAt", def = "{'customer.id': 1, 'createdAt': 1}")
@Document("orders")
public class OrderEntity extends AbstractEntity {

    private CustomerOfOrderEntity customer;

    private List<OrderItemEntity> items;
}
