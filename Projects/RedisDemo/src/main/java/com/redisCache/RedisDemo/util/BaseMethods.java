package com.redisCache.RedisDemo.util;

import java.util.List;

public class BaseMethods {

    public static String getStringWithQuotes(String value) {
        value = "'" + value + "'";
        return value;
    }

    public static StringBuilder getJsonString(String searchContent2, String searchText2) {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(searchContent2).append(":").append(searchText2).append("}");
        return sb;
    }

    public static StringBuilder getJsonStringWithIntValue(String searchContent2, Integer searchText2) {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(searchContent2).append(":").append(searchText2).append("}");
        return sb;
    }

    public static boolean validateStrNotNullOrNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean validateListNotNullOrNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static boolean validateObjectNotNull(Object object) {
        return object != null;
    }

}
