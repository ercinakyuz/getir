package com.getir.framework.locking;


import java.util.Set;
import java.util.concurrent.locks.Lock;

public interface Locker {

    Lock lock(String key);

    Lock multiLock(final Set<String> keySet);

}
