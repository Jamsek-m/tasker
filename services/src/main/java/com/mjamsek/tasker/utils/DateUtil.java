package com.mjamsek.tasker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(format);
        return dateFormat.format(date);
    }
    
    public static String formatDate(Date date) {
        return DateUtil.formatDate(date, "dd.MM.yyy HH:mm:ss.S");
    }
    
    public static String formatDate(String format) {
        return DateUtil.formatDate(new Date(), format);
    }
    
    public static String formatDate() {
        return DateUtil.formatDate(new Date());
    }
}
