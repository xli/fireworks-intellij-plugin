package com.thoughtworks.fireworks.controllers.tree;

import junit.framework.TestCase;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.TreePath;

public class TestTreeModelListenerTest extends TestCase {
    private JTree tree;
    private TreePath rootPath;

    protected void setUp() throws Exception {
        tree = new JTree();
        rootPath = new TreePath(tree.getModel().getRoot());
    }

    public void testShouldExpandRootPathWhenTreeNodeInserted() throws Exception {
        tree.collapsePath(rootPath);

        TreeModelListener listener = new TestTreeModelListener(tree);
        listener.treeNodesInserted(null);

        assertTrue(tree.isExpanded(rootPath));
    }

    public void testShouldNotExpandAgainIfRootNodeIsExpanded() throws Exception {
        assertTrue(tree.isExpanded(rootPath));

        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            public void treeWillExpand(TreeExpansionEvent event) {
                fail();
            }

            public void treeWillCollapse(TreeExpansionEvent event) {
                fail();
            }
        });

        TreeModelListener listener = new TestTreeModelListener(tree);
        listener.treeNodesInserted(null);

        assertTrue(tree.isExpanded(rootPath));
    }
}
