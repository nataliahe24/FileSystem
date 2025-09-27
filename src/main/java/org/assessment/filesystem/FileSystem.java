package org.assessment.filesystem;

import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private final Directory root;
    private Directory currentDirectory;

    public FileSystem() {
        root = new Directory("/", null);
        currentDirectory = root;
    }

    private Directory navigateToParent(String path) {
        int limit = PathUtils.normalizePath(path).size() - 1;
        return PathUtils.resolvePath(currentDirectory, path, limit);
    }

    private String lastItem(String path) {
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;
        String[] parts = cleanPath.split("/");
        return parts[parts.length - 1];
    }

    private Directory navigateTo(String path) {
        int limit = PathUtils.normalizePath(path).size();
        return PathUtils.resolvePath(currentDirectory, path, limit);
    }

    public void cd(String path) {
        Directory target = navigateTo(path);
        if (target != null) {
            currentDirectory = target;
        }
    }

    public void touch(String path) {
        Directory parent = navigateToParent(path);
        String fileName = lastItem(path);
        FileSystemItem item = parent.getItem(fileName);
        if (item == null) {
            parent.addItem(new File(fileName, parent));
        } else {
            throw new IllegalArgumentException("File already exists: " + fileName);
        }
    }

    public void ls() {
        for (FileSystemItem item : currentDirectory.getChildren()) {
            System.out.println((item.isDirectory() ? item.getName() + "/ " : item.getName()));
        }
    }

    public void lsR() {
        lsR(getCurrentPath());
    }

    private void lsR(String path) {
        Directory dir = navigateTo(path);
        if (dir == null) {
            throw new IllegalArgumentException("Directory not found: " + path);
        }

        System.out.println(path + ":");

        for (FileSystemItem item : dir.getChildren()) {
            System.out.println(item.isDirectory() ? item.getName() + "/" : item.getName());
        }

        for (FileSystemItem item : dir.getChildren()) {
            if (item.isDirectory()) {
                lsR("/".equals(path) ? "/" + item.getName() : path + "/" + item.getName());
            }
        }
    }

    public void mkdir(String path) {
        Directory parent = navigateToParent(path);
        String dirName = lastItem(path);
        FileSystemItem item = parent.getItem(dirName);
        if (item == null) {
            parent.addItem(new Directory(dirName, parent));
        } else {
            System.out.println("Directory already exists: " + dirName);
        }
    }

    public void pwd() {
        System.out.println(getCurrentPath());
    }

    private String getCurrentPath() {
        List<String> path = new ArrayList<>();
        Directory dir = currentDirectory;
        while (dir != root) {
            path.add(0, dir.getName());
            dir = dir.getParent();
        }
        String result = String.join("/", path);
        return "/" + result;
    }

    public void rm(String path) {
        Directory parent = navigateToParent(path);
        String itemName = lastItem(path);
        FileSystemItem item = parent.getItem(itemName);
        if (item != null) {
            parent.removeItem(itemName);
        } else {
            System.out.println("File not found: " + itemName);
        }
    }

}
