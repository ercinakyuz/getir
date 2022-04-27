package com.getir.readingisgood.infrastructure.util;

import org.mockito.stubbing.Stubber;

import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.doAnswer;

public class AsyncTestUtil {

    private AsyncTestUtil() {}

    public static Stubber countDown(CountDownLatch latch) {
        return doAnswer(o -> {
            try {
                return o.callRealMethod();
            } finally {
                latch.countDown();
            }
        });
    }
}