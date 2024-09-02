package com.dorvak.globallib.logging;

public class Logger {

    private static boolean debug = false;

    /**
     * Set the debug mode
     * @param debug true to enable debug mode, false to disable
     */
    public static void setDebug(boolean debug) {
        Logger.debug = debug;
    }

    /**
     * Get the debug mode
     * @return true if debug mode is enabled, false if disabled
     */
    public static boolean isDebug() {
        return debug;
    }

    private static java.util.logging.Logger getLogger() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        return java.util.logging.Logger.getLogger(className);
    }

    /**
     * Log an info message
     * @param message the message to log
     * @param args the arguments to replace in the message
     */
    public static void info(String message, Object... args) {
        getLogger().info(String.format(message, args));
    }

    /**
     * Log a warning message
     * @param message the message to log
     * @param args the arguments to replace in the message
     */
    public static void warning(String message, Object... args) {
        getLogger().warning(String.format(message, args));
    }

    /**
     * Log a severe message
     * @param message the message to log
     * @param args the arguments to replace in the message
     */
    public static void severe(String message, Object... args) {
        getLogger().severe(String.format(message, args));
    }

    /**
     * Log a fine message
     * @param message the message to log
     * @param args the arguments to replace in the message
     */
    public static void fine(String message, Object... args) {
        getLogger().fine(String.format(message, args));
    }

    /**
     * Handle an exception and log it
     * @param e the exception to handle
     */
    public static void handleException(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\n\tat ");
            sb.append(element.toString());
        }

        getLogger().severe(sb.toString());
    }

    /**
     * Handle an exception and log it
     * @param message the message to log
     * @param e the exception to handle
     */
    public static void handleException(String message, Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(": ");
        sb.append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\n\tat ");
            sb.append(element.toString());
        }

        getLogger().severe(sb.toString());
    }

    /**
     * Log a debug message if debug mode is enabled
     * @param s the message to log
     * @param args the arguments to replace in the message
     */
    public static void debug(String s, Object... args) {
        if (!isDebug()) {
            return;
        }

        getLogger().info(String.format("[DEBUG] " + s, args));
    }
}
