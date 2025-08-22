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

    public FileSystemItem getItem(String name) {
        for (FileSystemItem item : children) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

}