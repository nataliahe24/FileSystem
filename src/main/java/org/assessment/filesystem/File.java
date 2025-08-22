package org.assessment.filesystem;

public class File extends FileSystemItem {


    public File(String name, Directory parent) {
        super(name, parent);

    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}

