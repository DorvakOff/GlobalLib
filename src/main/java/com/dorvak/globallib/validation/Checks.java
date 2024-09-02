package com.dorvak.globallib.validation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Checks {

    private Checks() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Check if the email is valid
     * @param email email to check
     * @return true if the email is valid
     */
    @Contract(pure = true)
    public static boolean isEmail(@NotNull String email) {
        return email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Check if the password is valid: at least 8 characters, at least one digit, at least one lowercase letter, at least one uppercase letter
     * @param password password to check
     * @return true if the password is valid
     */
    @Contract(pure = true)
    public static boolean isPasswordValid(@NotNull String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}
