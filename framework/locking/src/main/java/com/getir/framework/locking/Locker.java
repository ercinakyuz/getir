package com.getir.framework.locking;

import com.getir.framework.locking.impl.Locked;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

public abstract class Locker {

    protected abstract Function<String, Lock> lockFactory();

    protected abstract Function<Set<String>, Lock> multiLockFactory();

    public Locked lock(final String key) {
        return internalLock(lockFactory().apply(key));
    }

    public Locked multiLock(final Set<String> keys) {
        return internalLock(multiLockFactory().apply(keys));
    }

    private static Locked internalLock(Lock lock) {
        lock.lock();
        return new Locked(lock);
    }

}
