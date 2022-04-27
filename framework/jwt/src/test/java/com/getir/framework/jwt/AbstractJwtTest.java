package com.getir.framework.jwt;

import com.getir.framework.jwt.faker.JwtFaker;
import com.getir.framework.test.AbstractTest;
import org.junit.jupiter.api.BeforeAll;

public class AbstractJwtTest extends AbstractTest {

    protected static JwtFaker jwtFaker;

    @BeforeAll
    public static void beforeAll() {
        jwtFaker = new JwtFaker();
    }

}
