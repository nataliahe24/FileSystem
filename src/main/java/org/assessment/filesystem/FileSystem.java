package org.assessment.filesystem;


public class FileSystem {
    private final Directory root;
    private Directory currentDirectory;


    public FileSystem() {
        root = new Directory("/", null);
        currentDirectory = root;
    }

    private Directory navigateToParent(String path) {
        Directory dir = path.startsWith("/") ? root : currentDirectory;
        String cleanPath  = path.startsWith("/") ? path.substring(1) : path;
        String[] parts = cleanPath .split("/");

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if (part.isEmpty() || ".".equals(part)) {
                continue;
            }
            if ("..".equals(part)) {
                if (dir.getParent() != null) {
                    dir = dir.getParent();
                } else {
                    System.out.println("Cannot go above root directory");
                    return null;
                }
            } else {
                FileSystemItem item = dir.getItem(part);
                if (!(item instanceof Directory)) {
                    System.out.println("Directory not found: " + part);
                    return null;
                }
                dir = (Directory) item;
            }
        }
        return dir;
    }

    private String lastSegment(String path) {
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;
        String[] parts = cleanPath.split("/");
        return parts[parts.length - 1];
    }

    private Directory navigateTo(String path) {
        if ("/".equals(path)) {
            return root;
        }
        Directory dir = path.startsWith("/") ? root : currentDirectory;
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;
        String[] parts = cleanPath.split("/");

        for (String part : parts) {
            if (part.isEmpty() || ".".equals(part)) {
                continue;
            }
            if ("..".equals(part)) {
                if (dir.getParent() != null) {
                    dir = dir.getParent();
                } else {
                    System.out.println("Already at root directory");
                    return null;
                }
            } else {
                FileSystemItem item = dir.getItem(part);
                if (!(item instanceof Directory)) {
                    System.out.println("Directory not found: " + part);
                    return null;
                }
                dir = (Directory) item;
            }
        }
        return dir;
    }

    public void cd(String path) {
        Directory target = navigateTo(path);
        if (target != null) {
            currentDirectory = target;
        }
    }

    public void touch(String path) {
        Directory parent = navigateToParent(path);
        if (parent == null) return;

        String fileName = lastSegment(path);
        FileSystemItem item = parent.getItem(fileName);
        if (item == null) {
            parent.addItem(new File(fileName, parent));
        } else {
            System.out.println("File already exists: " + fileName);
        }
    }

}

