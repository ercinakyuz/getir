package com.getir.readingisgood.domain.book.locker;

import com.getir.framework.locking.Locker;
import com.getir.framework.locking.impl.Locked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookQuantityLocker {

    private static final String LOCK_FORMAT = "locked_book_quantity_%s";

    private final Locker locker;

    public Locked lock(final UUID bookId) {
        return locker.lock(String.format(LOCK_FORMAT, bookId));
    }

    public Locked lockMultiple(final Set<UUID> bookIdSet) {
        return locker.multiLock(bookIdSet.stream().map(bookId -> String.format(LOCK_FORMAT, bookId)).collect(Collectors.toSet()));
    }
}
