package com.training.sportbetting.utils;

import java.time.format.DateTimeFormatter;

/**
 * Utility class which contains formatters.
 */
public final class Formatter {

    private Formatter() {
    }

    /**
     * Method create and return date formatter.
     *
     * @return Date formatter with yyyy.MM.dd pattern.
     */
    public static DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    /**
     * Method create and return date/time formatter.
     *
     * @return Date/time formatter with yyyy.MM.dd HH:mm:ss pattern.
     */
    public static DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
