package com.dorvak.globallib.utils;

import com.dorvak.globallib.logging.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }


    private static void copyDirectoryCompatibilityMode(@NotNull File sourceDirectory, @NotNull File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists() && !destinationDirectory.mkdir()) {
            Logger.severe("Failed to create directory: " + destinationDirectory.getName());
            return;
        }

        String[] files = sourceDirectory.list();

        if (files == null) {
            Logger.severe("Failed to copy directory: " + sourceDirectory.getName());
            return;
        }

        for (String f : files) {
            copyDirectory(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    /**
     * Copy a directory from source to destination
     * @param source the source directory
     * @param destination the destination directory
     * @throws IOException if an I/O error occurs
     */
    public static void copyDirectory(@NotNull File source, File destination) throws IOException {
        if (source.isDirectory()) {
            copyDirectoryCompatibilityMode(source, destination);
        } else {
            copyFile(source, destination);
        }
    }

    /**
     * Copy a file from source to destination
     * @param sourceFile the source file
     * @param destinationFile the destination file
     * @throws IOException if an I/O error occurs
     */
    public static void copyFile(File sourceFile, File destinationFile)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

    /**
     * Delete a repository recursively
     * @param directory the directory to delete
     */
    public static void deleteDirectory(@NotNull File directory) throws IOException {
        Stream <Path> walk = Files.walk(directory.toPath());
        Stream<Path> paths = walk
                .map(java.nio.file.Path::toFile)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .map(File::toPath);

        for (Path path : (Iterable<Path>) paths::iterator) {
            Files.delete(path);
        }

        walk.close();
        paths.close();
    }

    /**
     * Get the extension of a file
     * @param fileName the file name
     * @return the extension of the file
     */
    public static @NotNull String getExtension(@NotNull String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Get the file name without the extension
     * @param fileName the file name
     * @return the file name without the extension
     */
    public static @NotNull String getFileName(@NotNull String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * Create a directory if it does not exist
     * @param directory the directory to create
     */
    public static void createDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            if (!file.mkdirs())
                throw new RuntimeException("Failed to create directory");
            Logger.debug("Created directory: " + directory);
        }
    }

    /**
     * Create a file if it does not exist
     * @param path the path of the file
     * @param fileName the name of the file
     * @throws IOException if an I/O error occurs
     */
    public static void createFile(String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        createFile(file);
    }

    /**
     * Create a file if it does not exist
     * @param file the file to create
     * @throws IOException if an I/O error occurs
     */
    public static void createFile(@NotNull File file) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new RuntimeException("Failed to create file");
            }
            Logger.debug("Created file: " + file.getAbsolutePath());
        }
    }
}
