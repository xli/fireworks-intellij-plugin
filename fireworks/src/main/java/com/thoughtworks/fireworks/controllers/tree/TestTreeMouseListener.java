package com.thoughtworks.fireworks.controllers.tree;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestTreeMouseListener extends MouseAdapter {
    private final TestTree tree;

    public TestTreeMouseListener(TestTree tree) {
        this.tree = tree;
    }

    public void mouseClicked(MouseEvent e) {
        TreePath pathForLocation = tree.getPathForLocation(e.getX(), e.getY());
        if (pathForLocation == null) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            tree.setSelectionPath(pathForLocation);
            tree.showMenu(pathForLocation, e.getX(), e.getY());
        }
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
            tree.jumpToSourceOfNode(pathForLocation);
        }
    }
}
