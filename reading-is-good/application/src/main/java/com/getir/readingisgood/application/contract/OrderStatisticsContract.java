package com.getir.readingisgood.application.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticsContract {

    private Month month;

    private int totalOrderCount;

    private BigDecimal totalOrderAmount;

    private int totalBookCount;
}
