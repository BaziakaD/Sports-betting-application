package com.training.sportbetting.utils;

/**
 * Class which contains different regular expressions constants.
 */
public final class Patterns {

    public static final String EMAIL = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static final String PASS = ".{8}";
    public static final String NAME = "[A-Z]{1}[a-z]{1,}";
    public static final String BALANCE = "\\d{1,}";
    public static final String CURRENCY = "UAH|USD|EUR";
    public static final String DATE = "^\\d{4}\\.(1[0-2]|0[1-9])\\.([0-2][0-9]|3[0-1])$";
    public static final String INPUT_TYPE = "bet|q";
    public static final String DIGIT = "\\d+";

    private Patterns() {}
}
