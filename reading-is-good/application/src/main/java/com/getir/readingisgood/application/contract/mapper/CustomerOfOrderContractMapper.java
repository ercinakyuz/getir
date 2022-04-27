package com.getir.readingisgood.application.contract.mapper;


import com.getir.readingisgood.application.contract.CustomerOfOrderContract;
import com.getir.readingisgood.domain.order.model.aggregate.value.CustomerOfOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOfOrderContractMapper {

    public CustomerOfOrderContract map(final CustomerOfOrder customer) {
        return CustomerOfOrderContract.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .build();
    }
}
