package com.getir.readingisgood.domain.order.model.aggregate.value.converter;

import com.getir.readingisgood.domain.order.model.aggregate.value.CustomerOfOrder;
import com.getir.readingisgood.infrastructure.persistence.entity.order.CustomerOfOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOfOrderConverter implements Converter<CustomerOfOrder, CustomerOfOrderEntity> {

    @Override
    public CustomerOfOrderEntity convert(final CustomerOfOrder customerOfOrder) {
        return CustomerOfOrderEntity.builder()
                .id(customerOfOrder.getId())
                .email(customerOfOrder.getEmail())
                .build();
    }
}
