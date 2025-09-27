package org.assessment.filesystem;

import java.util.*;

public final class PathUtils {

    public PathUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<String> normalizePath(String path) {
        String[] parts = path.split("/");
        List<String> result = new ArrayList<>();

        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }

            result.add(part);
        }

        return result;

    }

    public static Directory resolvePath(Directory initialDirectory, String path, int limit) {
        Directory dir = initialDirectory;
        List<String> parts = normalizePath(path);

        for (int i = 0; i < limit; i++) {
            String part = parts.get(i);
            if (part.equals("..")) {
                if (dir.getParent() != null) {
                    dir = dir.getParent();
                } else {
                    throw new IllegalArgumentException("Already in root");
                }
            } else {
                dir = dir.getDirectory(part);
            }

        }

        return dir;
    }

}