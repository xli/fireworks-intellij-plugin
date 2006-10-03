/*
 *    Copyright (c) 2006 LiXiao.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
