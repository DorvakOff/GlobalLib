package com.dorvak.globallib.utils;

import com.dorvak.globallib.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * A class to read the output of a process and consume it
 */
public class StreamGobbler implements Runnable {

    private final Process process;
    private final Consumer<String> lineConsumer;

    /**
     * Create a new StreamGobbler
     * @param process the process to read the output from
     * @param consumer the consumer to consume the output
     */
    public StreamGobbler(Process process, Consumer<String> consumer) {
        this.process = process;
        this.lineConsumer = consumer;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineConsumer.accept(line);
            }
        } catch (Exception e) {
            Logger.handleException("Error while reading process output", e);
        }
    }
}
