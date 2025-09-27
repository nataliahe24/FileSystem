package org.assessment.filesystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class FileSystemTest {
    private FileSystem fs;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        fs = new FileSystem();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String getOutput() {
        return outputStreamCaptor.toString().trim();
    }

    private void clearOutput() {
        outputStreamCaptor.reset();
    }

    @Test
    void mkdirShouldCreateDirectory() {
        fs.mkdir("test");
        fs.cd("test");
        fs.pwd();
        assertTrue(getOutput().contains("/test"));
        clearOutput();

        fs.mkdir("nested");
        fs.cd("nested");
        fs.pwd();
        assertTrue(getOutput().contains("/test/nested"));
    }

    @Test
    void touchShouldCreateFile() {
        fs.touch("file.txt");
        fs.ls();
        assertTrue(getOutput().contains("[FILE] file.txt"));
        clearOutput();

        fs.mkdir("dir");
        fs.touch("dir/nested.txt");
        fs.cd("dir");
        fs.ls();
        assertTrue(getOutput().contains("[FILE] nested.txt"));
    }

    @Test
    void cdShouldNavigateToDirectory() {
        fs.mkdir("test");
        fs.cd("test");
        fs.pwd();
        assertTrue(getOutput().contains("/test"));
        clearOutput();

        fs.mkdir("nested");
        fs.cd("nested");
        fs.pwd();
        assertTrue(getOutput().contains("/test/nested"));
        clearOutput();

        fs.cd("..");
        fs.pwd();
        assertTrue(getOutput().contains("/test"));
        assertFalse(getOutput().contains("/test/nested"));
        clearOutput();

        fs.cd("/");
        fs.pwd();
        assertEquals("/", getOutput());
    }

    @Test
    void rmShouldRemoveItem() {
        fs.mkdir("test");
        fs.touch("file.txt");
        fs.ls();
        assertTrue(getOutput().contains("[DIR] test"));
        assertTrue(getOutput().contains("[FILE] file.txt"));
        clearOutput();

        fs.rm("test");
        fs.ls();
        assertFalse(getOutput().contains("[DIR] test"));
        assertTrue(getOutput().contains("[FILE] file.txt"));
        clearOutput();

        fs.rm("file.txt");
        fs.ls();
        assertFalse(getOutput().contains("[FILE] file.txt"));
        assertEquals("", getOutput());
    }

    @Test
    void absolutePathsShouldWork() {
        fs.mkdir("a");
        fs.mkdir("a/b");

        fs.cd("/a/b");
        fs.pwd();
        assertTrue(getOutput().contains("/a/b"));
        clearOutput();

        fs.touch("/root.txt");
        fs.cd("/");
        fs.ls();
        assertTrue(getOutput().contains("[FILE] root.txt"));
        assertTrue(getOutput().contains("[DIR] a"));
    }

    @Test
    void relativePathsShouldWork() {
        fs.mkdir("a");
        fs.mkdir("a/b");

        fs.cd("a");
        fs.pwd();
        assertTrue(getOutput().contains("/a"));
        clearOutput();

        fs.cd("b");
        fs.pwd();
        assertTrue(getOutput().contains("/a/b"));
        clearOutput();

        fs.cd("../");
        fs.pwd();
        assertTrue(getOutput().contains("/a"));
        assertFalse(getOutput().contains("/a/b"));
        clearOutput();

        fs.cd("..");
        fs.pwd();
        assertEquals("/", getOutput());
    }

    @Test
    void pwdShouldReturnCurrentPath() {
        fs.pwd();
        assertEquals("/", getOutput());
        clearOutput();

        fs.mkdir("dir1");
        fs.mkdir("dir1/dir2");
        fs.cd("dir1/dir2");
        fs.pwd();
        assertTrue(getOutput().contains("/dir1/dir2"));
    }

    @Test
    void lsShouldListDirectoryContents() {
        fs.ls();
        assertEquals("", getOutput());
        clearOutput();

        fs.mkdir("testdir");
        fs.touch("testfile.txt");
        fs.mkdir("anotherdir");
        fs.ls();
        String output = getOutput();
        assertTrue(output.contains("[DIR] testdir"));
        assertTrue(output.contains("[FILE] testfile.txt"));
        assertTrue(output.contains("[DIR] anotherdir"));
    }

    @Test
    void shouldHandleInvalidOperations() {
        fs.cd("nonexistent");
        clearOutput();
        fs.pwd();
        assertEquals("/", getOutput());
        clearOutput();

        fs.rm("nonexistent.txt");
        String output = getOutput();
        assertTrue(output.contains("File not found"));
        clearOutput();

        fs.mkdir("test");
        fs.mkdir("test");
        output = getOutput();
        assertTrue(output.contains("Directory already exists"));
        clearOutput();

        fs.touch("file.txt");
        fs.touch("file.txt");
        output = getOutput();
        assertTrue(output.contains("File already exists"));
    }

    @Test
    void shouldHandleComplexPathOperations() {
        fs.mkdir("test");
        fs.cd("./test");
        fs.pwd();
        assertTrue(getOutput().contains("/test"));
        clearOutput();

        fs.mkdir("a");
        fs.mkdir("a/b");
        fs.mkdir("a/b/c");
        fs.cd("a/b/c");
        fs.cd("../../../..");
        fs.pwd();
        assertEquals("/", getOutput());
        clearOutput();

        fs.mkdir("root1");
        fs.cd("root1");
        fs.mkdir("sub1");
        fs.touch("/root1/sub1/file.txt");
        fs.cd("sub1");
        fs.ls();
        assertTrue(getOutput().contains("[FILE] file.txt"));
    }
}