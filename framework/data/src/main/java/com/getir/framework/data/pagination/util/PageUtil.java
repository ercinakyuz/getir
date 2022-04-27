package com.getir.framework.data.pagination.util;

import com.getir.framework.data.pagination.impl.CustomPageImpl;
import org.springframework.data.domain.Page;

public final class PageUtil {

    private PageUtil(){}

    public static <T> CustomPageImpl<T> toCustomPage(final Page<T> page){
        return new CustomPageImpl<>(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}
