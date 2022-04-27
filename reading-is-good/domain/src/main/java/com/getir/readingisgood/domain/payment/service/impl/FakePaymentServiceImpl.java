package com.getir.readingisgood.domain.payment.service.impl;

import com.getir.readingisgood.domain.payment.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class FakePaymentServiceImpl implements PaymentService {

    @Override
    public void pay() { }
}
