package com.getir.readingisgood.infrastructure.persistence.entity;

import com.getir.framework.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Month;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "unique_customerId_year_month", unique = true, def = "{'customerId': 1, 'year': 1, 'month': 1}")
@CompoundIndex(name = "customerId_year", def = "{'customerId': 1, 'year': 1}")
@Document("order_statistics")
public class OrderStatisticsEntity extends AbstractEntity {

    private UUID customerId;

    private int year;

    private Month month;

    private int totalOrderCount;

    private BigDecimal totalOrderAmount;

    private int totalBookCount;


}
