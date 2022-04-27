package com.getir.framework.test.faker;


import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.math.BigDecimal;
import java.util.Locale;

public abstract class AbstractFaker {
    protected final Faker faker;
    protected final FakeValuesService fakeValuesService;

    protected AbstractFaker() {
        faker = new Faker();
        fakeValuesService = new FakeValuesService(new Locale("tr-TR"), new RandomService());
    }

    protected BigDecimal bigDecimalValue(final int maxNumberOfDecimals, final int min, final int max) {
        return BigDecimal.valueOf(faker.number().randomDouble(maxNumberOfDecimals, min, max));
    }
}
