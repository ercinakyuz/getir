package com.getir.framework.locking.impl;

import com.getir.framework.locking.Locker;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.locks.Lock;

@Component
@RequiredArgsConstructor
public class RedisLocker implements Locker {

    private final RedissonClient redissonClient;

    @Override
    public Lock lock(final String key) {
        final Lock fairLock = redissonClient.getFairLock(key);
        fairLock.lock();
        return fairLock;
    }

    @Override
    public Lock multiLock(final Set<String> keySet) {
        final RLock[] subLocks = keySet.stream().map(redissonClient::getFairLock).toArray(RLock[]::new);
        final Lock mainLock = redissonClient.getMultiLock(subLocks);
        mainLock.lock();
        return mainLock;
    }
}
