package com.finalprojectcoffee.utils;

public class PhoneNumberUtil {
    private static final String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    public static Boolean validationPhoneNumber(String phoneNumber){
        return phoneNumber.matches(pattern);
    }
}
