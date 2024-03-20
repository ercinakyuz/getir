package com.getir.framework.locking.impl;

import com.getir.framework.locking.Locker;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RedisLocker extends Locker {

    private final RedissonClient redissonClient;

    @Override
    protected Function<String, Lock> lockFactory() {
        return redissonClient::getFairLock;
    }

    @Override
    protected Function<Set<String>, Lock> multiLockFactory() {
        return this::buildMultiLock;
    }

    private Lock buildMultiLock(Set<String> keys) {
        var subLocks = keys.stream().map(redissonClient::getFairLock).toArray(RLock[]::new);
        return redissonClient.getMultiLock(subLocks);
    }
}
