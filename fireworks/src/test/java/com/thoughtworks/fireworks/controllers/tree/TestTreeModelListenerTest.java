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

import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.TreePath;

public class TestTreeModelListenerTest extends TestCase {
    private Mock testTree;
    private TreePath rootPath;
    private TreeModelListener listener;

    protected void setUp() throws Exception {
        testTree = Turtle.mock(TestTree.class);
        listener = new TestTreeModelListener((TestTree) testTree.mockTarget());
    }

    public void testShouldExpandRootPathWhenTreeNodeInserted() throws Exception {
        listener.treeNodesInserted(null);
        testTree.assertDid("expandRootNode");
    }

    public void testShouldDoNothingWhenTreeNodesChangedRemovedOrStructureChanged() throws Exception {
        listener.treeNodesChanged(null);
        listener.treeNodesRemoved(null);
        listener.treeStructureChanged(null);
        testTree.assertDidNothing();
    }
}
