package com.dorvak.globallib.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class RandomUtils {

    private static final String ALPHA_NUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private RandomUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generate a random string with the given length
     * @param length length of the string
     * @return the generated string
     */
    @NotNull
    public static String randomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(ALPHA_NUMERIC_CHARS.charAt((int) (Math.random() * ALPHA_NUMERIC_CHARS.length())));
        }
        return builder.toString();
    }

    /**
     * Generate a unique random string with the given length using the given function to check if the string exists
     * @param length length of the string
     * @param checkExists function to check if the string exists
     * @return the generated string
     */
    @NotNull
    public static String randomString(int length, @NotNull Function<String, Boolean> checkExists) {
        String s;
        do {
            s = randomString(length);
        } while (checkExists.apply(s));
        return s;
    }

    /**
     * Generate a random integer between the given min and max
     * @param min minimum value
     * @param max maximum value
     * @return the generated integer
     */
    public static int randomInt(int min, int max) {
        return (int) randomDouble(min, max);
    }

    /**
     * Generate a random double between the given min and max
     * @param min minimum value
     * @param max maximum value
     * @return the generated double
     */
    public static double randomDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }
}
