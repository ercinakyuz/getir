package com.getir.readingisgood.domain.customer.model.aggregate.unitofwork;

import com.getir.framework.domain.unitofwork.AggregateOfWork;
import com.getir.readingisgood.domain.customer.model.aggregate.Customer;
import com.getir.readingisgood.domain.customer.model.aggregate.converter.CustomerConverter;
import com.getir.readingisgood.infrastructure.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOfWork implements AggregateOfWork<Customer> {

    private final CustomerConverter customerConverter;

    private final CustomerRepository customerRepository;

    @Override
    public Customer insert(final Customer customer) {
        customerRepository.save(customerConverter.convertAll(customer));
        return customer;
    }

    @Override
    public Customer update(final Customer customer) {
        customerRepository.save(customerConverter.convertAll(customer));
        return customer;
    }

    @Override
    public Customer delete(final Customer customer) {
        customerRepository.deleteById(customer.getId());
        return customer;
    }
}


