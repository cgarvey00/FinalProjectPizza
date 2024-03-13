package com.finalprojectcoffee.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cgarvey00
 */
public class PostcodeUtil {
    private static final Pattern IRISH_POSTCODE_REGEX = Pattern.compile("^[A-Za-z0-9]{3}( ?[A-Za-z0-9]){3}$]");

    /**
     * Checking the Postcode to ensure it matches the regex
     */
    public static Boolean validateIrishPostcode(String postcode) {
        Matcher postcodeMatcher = IRISH_POSTCODE_REGEX.matcher(postcode);
        return postcodeMatcher.matches();

    }
}
