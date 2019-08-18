package com.mjamsek.tasker.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParserUtil {
    
    public static String parseServiceId(String url) {
        Pattern pattern = Pattern.compile("/.+/services/(\\d+)/.+");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String result = matcher.group(1);
            try {
                return result;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
    
}
