package com.getir.framework.locking.impl;

import com.getir.framework.locking.Locker;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisLocker implements Locker {

    private final RedissonClient redissonClient;

    @Override
    public Locked lock(String key) {
        var lock = redissonClient.getFairLock(key);
        return internalLock(lock);
    }

    @Override
    public Locked multiLock(Set<String> keySet) {
        var subLocks = keySet.stream().map(redissonClient::getFairLock).toArray(RLock[]::new);
        var mainLock = redissonClient.getMultiLock(subLocks);
        return internalLock(mainLock);
    }
}
