package com.finalprojectcoffee.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern){
        if(dateTime == null){
            return "Not Available";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
