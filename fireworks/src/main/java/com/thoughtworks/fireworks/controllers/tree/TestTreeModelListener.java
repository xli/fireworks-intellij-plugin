package com.thoughtworks.fireworks.controllers.tree;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public class TestTreeModelListener implements TreeModelListener {
    private final JTree tree;

    public TestTreeModelListener(JTree tree) {
        this.tree = tree;
    }

    public void treeNodesChanged(TreeModelEvent e) {
    }

    public void treeNodesInserted(TreeModelEvent e) {
        tree.expandPath(new TreePath(tree.getModel().getRoot()));
    }

    public void treeNodesRemoved(TreeModelEvent e) {
    }

    public void treeStructureChanged(TreeModelEvent e) {
    }
}
