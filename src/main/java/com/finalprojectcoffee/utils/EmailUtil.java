package com.finalprojectcoffee.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
    private static final String EMAIL_PATTERN = "[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
