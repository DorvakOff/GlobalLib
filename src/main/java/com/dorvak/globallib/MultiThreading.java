package com.dorvak.globallib;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreading {

    private static final int SCHEDULED_POOL_SIZE = 100;
    private static final int POOL_SIZE = 250;

    private static final ExecutorService POOL = Executors.newFixedThreadPool(POOL_SIZE, new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, String.format("Thread %s", counter.incrementAndGet()));
        }
    });

    private static final ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(SCHEDULED_POOL_SIZE, new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, String.format("Thread %s", counter.incrementAndGet()));
        }
    });

    /**
     * Schedule a task to run at a fixed rate after an initial delay
     * @param r the runnable
     * @param initialDelay the initial delay
     * @param delay the delay
     * @param unit the time unit
     * @return the scheduled future
     */
    public static @NotNull ScheduledFuture<?> scheduleRepeatingTask(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        return RUNNABLE_POOL.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    /**
     * Schedule a task to run after a delay
     * @param r the runnable
     * @param delay the delay
     * @param unit the time unit
     * @return the scheduled future
     */
    public static @NotNull ScheduledFuture<?> schedule(Runnable r, long delay, TimeUnit unit) {
        return RUNNABLE_POOL.schedule(r, delay, unit);
    }

    /**
     * Run a task asynchronously
     * @param runnable the runnable
     */
    public static void run(Runnable runnable) {
        POOL.execute(runnable);
    }

    /**
     * Shutdown the thread pools
     */
    public static void shutdown() {
        RUNNABLE_POOL.shutdown();
        POOL.shutdown();
    }
}
