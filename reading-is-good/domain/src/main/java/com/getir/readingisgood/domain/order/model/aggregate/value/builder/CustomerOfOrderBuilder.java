package com.getir.readingisgood.domain.order.model.aggregate.value.builder;

import com.getir.readingisgood.domain.customer.model.aggregate.Customer;
import com.getir.readingisgood.domain.customer.model.aggregate.builder.CustomerBuilder;
import com.getir.readingisgood.domain.order.model.aggregate.value.CustomerOfOrder;
import com.getir.readingisgood.domain.order.model.aggregate.value.dto.LoadCustomerOfOrderDto;
import com.getir.readingisgood.infrastructure.persistence.entity.order.CustomerOfOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerOfOrderBuilder {

    private final CustomerBuilder customerBuilder;

    public CustomerOfOrder buildFromEntity(final CustomerOfOrderEntity customerEntity) {
        return CustomerOfOrder.load(LoadCustomerOfOrderDto.builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .build());
    }

    public CustomerOfOrder buildNew(final UUID customerId) {
        final Customer customer = customerBuilder.build(customerId);
        return CustomerOfOrder.load(LoadCustomerOfOrderDto.builder()
                .id(customer.getId())
                .email(customer.getCredentials().getEmail())
                .build());
    }
}
