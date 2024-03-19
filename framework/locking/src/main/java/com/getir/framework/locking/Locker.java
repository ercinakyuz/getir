package com.getir.framework.locking;

import com.getir.framework.locking.impl.Locked;

import java.util.Set;
import java.util.concurrent.locks.Lock;

public interface Locker {

    Locked lock(final String key);

    Locked multiLock(final Set<String> keySet);

    default Locked internalLock(Lock lock){
        lock.lock();
        return new Locked(lock);
    }

}
