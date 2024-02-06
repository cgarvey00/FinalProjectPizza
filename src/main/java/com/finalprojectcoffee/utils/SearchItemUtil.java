package com.finalprojectcoffee.utils;

/**
 * @author cgarvey00
 */
public class SearchItemUtil {
    private static final String pattern = "[a-zA-Z0-9\\s!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]+";

    public static boolean validateKeyword(String keyword) {
        return keyword.matches(pattern);
    }
}
