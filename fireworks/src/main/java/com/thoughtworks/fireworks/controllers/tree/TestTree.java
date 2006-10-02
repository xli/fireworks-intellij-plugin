package com.thoughtworks.fireworks.controllers.tree;

import javax.swing.tree.TreePath;

public interface TestTree {
    void showMenu(TreePath path, int x, int y);

    void jumpToSourceOfNode(TreePath path);

    TreePath getPathForLocation(int x, int y);

    void setSelectionPath(TreePath path);

    TreePath getSelectionPath();
}
