package com.dorvak.globallib.utils;

import java.util.Objects;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Check if the object cast to string is empty
     * @param obj Object to check
     * @return true if the object is empty
     */
    public static boolean isEmpty(Object obj) {
        return Objects.toString(obj, "").isEmpty();
    }

    /**
     * Check if the object cast to string is not empty
     * @param obj Object to check
     * @return true if the object is not empty
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * Convert the object to string and return it
     * @param obj Object to convert
     * @param replace String to replace if the object is null
     * @return String
     */
    public static String toString(Object obj, String replace) {
        return Objects.toString(obj, replace);
    }

    /**
     * Convert the object to string and return it
     * @param obj Object to convert
     * @return String
     */
    public static String toString(Object obj) {
        return Objects.toString(obj, "");
    }
}
