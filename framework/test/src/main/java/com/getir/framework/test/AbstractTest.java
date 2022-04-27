package com.getir.framework.test;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTest {

    protected static Faker faker;

    @BeforeAll
    public static void beforeAllSuper() {
        faker = new Faker();
    }

    protected static Set[] emptySetValues() {
        return new Set[]{Collections.EMPTY_SET, null};
    }

    protected static List[] emptyListValues() {
        return new List[]{Collections.EMPTY_LIST, null};
    }

    protected static String[] blankStringValues() {
        return new String[]{null, EMPTY, SPACE};
    }

    protected static BigDecimal[] invalidPrice() {
        return new BigDecimal[]{null, ZERO, BigDecimal.valueOf(faker.number().randomDouble(2, -1, -10))};
    }

    protected static BigDecimal[] invalidPriceWithoutZero() {
        return new BigDecimal[]{null, BigDecimal.valueOf(faker.number().randomDouble(2, -1, -10))};
    }

    protected static Long[] notPositiveLongValuesWithNull() {
        return new Long[]{null, -3l, -2l, -1l, 0l};
    }

    protected static long[] notPositiveLongValues() {
        return new long[]{-3l, -2l, -1l, 0l};
    }

    protected static Integer[] notPositiveIntegerValuesWithNull() {
        return new Integer[]{null, -3, -2, -1, 0};
    }

    protected static int[] notPositiveIntegerValues() {
        return new int[]{ -3, -2, -1, 0};
    }


}
