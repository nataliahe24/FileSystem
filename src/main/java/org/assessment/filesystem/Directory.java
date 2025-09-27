package org.assessment.filesystem;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemItem {

    private final List<FileSystemItem> children = new ArrayList<>();

    public Directory(String name, Directory parent) {
        super(name, parent);
    }

    public List<FileSystemItem> getChildren() {
        return children;
    }

    public void addItem(FileSystemItem item) {
        children.add(item);
    }

    public void removeItem(String itemName) {
        children.removeIf(item -> item.getName().equals(itemName));
    }

    public FileSystemItem getItem(String itemName) {
        return children.stream()
                .filter(item -> item.getName().equals(itemName))
                .findFirst().orElse(null);
    }

    public Directory getDirectory(String itemName) {
        return children.stream()
                .filter(item -> item.getName().equals(itemName) && item.isDirectory())
                .findFirst().map(item -> (Directory) item)
                .orElseThrow(() -> new IllegalArgumentException(itemName + ": Not a directory"));
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

}