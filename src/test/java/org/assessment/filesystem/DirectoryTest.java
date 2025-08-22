package org.assessment.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {
    private Directory root;
    private Directory child;
    private File file;

    @BeforeEach
    void setUp() {
        root = new Directory("root", null);
        child = new Directory("child", root);
        file = new File("test.txt", root);
    }

    @Test
    void addItemShouldAddItem() {
        root.addItem(child);
        assertEquals(child, root.getItem("child"));
    }

    @Test
    void removeItemShouldRemoveExistingItem() {
        root.addItem(file);
        root.removeItem("test.txt");
        assertNull(root.getItem("test.txt"));
    }

    @Test
    void getItemShouldReturnNullForNonExistentItem() {
        assertNull(root.getItem("nonexistent"));
    }

    @Test
    void getChildrenShouldReturnAllItems() {
        root.addItem(child);
        root.addItem(file);
        assertEquals(2, root.getChildren().size());
    }
}