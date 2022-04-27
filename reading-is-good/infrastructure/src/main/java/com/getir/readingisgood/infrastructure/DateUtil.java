package com.getir.readingisgood.infrastructure;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public final class DateUtil {

    private static ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private DateUtil(){}

    public static Instant toSystemInstant(final LocalDateTime localDateTime) {
        return localDateTime.atZone(DEFAULT_ZONE_ID).toInstant();
    }

    public static LocalDateTime toSystemLocalDateTime(final Instant instant) {
        return instant.atZone(DEFAULT_ZONE_ID).toLocalDateTime();
    }

    public static String stringifyWithIsoDateTime(final LocalDateTime localDateTime) {
        return localDateTime.format(ISO_DATE_TIME);
    }

}
