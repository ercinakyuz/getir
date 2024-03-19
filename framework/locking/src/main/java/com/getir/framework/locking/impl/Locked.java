package com.getir.framework.locking.impl;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.Lock;

@RequiredArgsConstructor
public class Locked implements AutoCloseable {

    private final Lock lock;

    @Override
    public void close() {
        lock.unlock();
    }
}
