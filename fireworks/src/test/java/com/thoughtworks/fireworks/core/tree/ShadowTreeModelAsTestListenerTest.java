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
package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.constraint.CheckResult;
import com.thoughtworks.turtlemock.constraint.Constraint;
import junit.framework.TestCase;
import junit.framework.TestResult;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public class ShadowTreeModelAsTestListenerTest extends TestCase {
    private ShadowTreeModel model;
    private ComparableTestShadow shadow;
    private ShadowClassTreeNode child;
    private Mock listener;
    private ShadowSummaryTreeNode root;
    private TestShadow grandchildTest;
    private ShadowMethodTreeNode grandchild;

    private ShadowCabinet cabinet;
    private TestResult result;

    protected void setUp() throws Exception {
        root = new ShadowSummaryTreeNode();
        model = new ShadowTreeModel(root);
        grandchildTest = new SuccessTestShadow();
        shadow = new ComparableTestShadow(grandchildTest);
        child = new ShadowClassTreeNode(shadow, root);
        grandchild = new ShadowMethodTreeNode(grandchildTest, child);
        listener = Turtle.mock(TreeModelListener.class);

        cabinet = new ShadowCabinet();
        cabinet.addListener(model);
        result = new TestResult();
        result.addListener(model);
    }

    public void testShouldAddTestAsChildOfTestShadowWhichIsAddBeforeByAfterAddTestEvent() throws Exception {
        cabinet.add(shadow);
        cabinet.action(result);

        assertEquals(1, model.getChildCount(child));
        assertEquals(grandchild, model.getChild(child, 0));
        assertEquals(0, model.getIndexOfChild(child, grandchild));
    }

    public void testShouldFireTreeNodesInsertedEventWhenTestStartEventIsFired() throws Exception {
        model.addTreeModelListener((TreeModelListener) listener.mockTarget());
        cabinet.add(shadow);
        listener.assertDid("treeNodesInserted");

        cabinet.action(result);
        listener.assertDid("treeNodesInserted").withFirstArgConstraint(childEventConstraint());
    }

    public void testShouldFireTreeNodesInsertedEventEvenIfTestExistedStart() throws Exception {
        model.addTreeModelListener((TreeModelListener) listener.mockTarget());
        cabinet.add(shadow);
        listener.assertDid("treeNodesInserted");

        cabinet.action(result);
        listener.assertDid("treeNodesInserted");

        cabinet.action(result);
        listener.assertDid("treeNodesInserted").withFirstArgConstraint(childEventConstraint());
    }

    public void testShouldClearTestMethodNodeAndFireTreeNodesRemovedEventWhenStartAction() throws Exception {
        model.addTreeModelListener((TreeModelListener) listener.mockTarget());
        cabinet.add(shadow);
        cabinet.action(result);
        listener.assertNotDid("treeNodesRemoved");
        cabinet.action(result);
        listener.assertDid("treeNodesRemoved").withFirstArgConstraint(childEventConstraint());
    }

    public void testShouldRemoveNodeIfTestExistedButNotStart() throws Exception {
        model.addTreeModelListener((TreeModelListener) listener.mockTarget());
        cabinet.add(shadow);
        cabinet.action(result);

        cabinet.remove(shadow);
        assertEquals(0, model.getChildCount(child));
    }

    private Constraint childEventConstraint() {
        return new Constraint() {
            public CheckResult check(Object param) {
                TreeModelEvent event = (TreeModelEvent) param;

                assertEquals(model, event.getSource());
                assertEquals(2, event.getPath().length);
                assertEquals(new TreePath(new Object[]{root, child}), event.getTreePath());

                assertEquals(1, event.getChildIndices().length);
                assertEquals(0, event.getChildIndices()[0]);

                assertEquals(1, event.getChildren().length);
                assertEquals(grandchild, event.getChildren()[0]);

                return CheckResult.PASS;
            }
        };
    }

}
