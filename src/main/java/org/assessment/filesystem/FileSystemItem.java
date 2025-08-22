package org.assessment.filesystem;


public abstract class FileSystemItem {
    private final String name;
    private final Directory parent;

    public FileSystemItem(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public abstract boolean isDirectory();

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

}
