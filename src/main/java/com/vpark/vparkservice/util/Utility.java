package com.vpark.vparkservice.util;

/**
 * Created by kalana.w on 5/23/2020.
 */
public class Utility {
    public static String queryLikeAny(String param) {
        String wildcard = "%";
        if (param == null || param.isEmpty() || param.equalsIgnoreCase("null") || param.equalsIgnoreCase("undefined")) {
            param = "";
        }
        return wildcard + param + wildcard;
    }
}
