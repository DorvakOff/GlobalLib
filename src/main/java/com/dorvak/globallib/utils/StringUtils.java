package com.dorvak.globallib.utils;

import java.util.Objects;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isEmpty(Object obj) {
        return Objects.toString(obj, "").isEmpty();
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static String toString(Object obj, String replace) {
        return Objects.toString(obj, replace);
    }

    public static String toString(Object obj) {
        return Objects.toString(obj, "");
    }
}
