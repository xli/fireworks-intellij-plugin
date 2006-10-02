package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.stubs.Success;
import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.constraint.CheckResult;
import com.thoughtworks.turtlemock.constraint.Constraint;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public class ShadowTreeModelTest extends TestCase {
    private ShadowTreeModel model;
    private ComparableTestShadow shadow;
    private ShadowClassTreeNode child;
    private Mock listener;
    private ShadowSummaryTreeNode root;
    private ShadowCabinet cabinet;
    private TestResult result;

    protected void setUp() throws Exception {
        root = new ShadowSummaryTreeNode();
        model = new ShadowTreeModel(root);
        shadow = new ComparableTestShadow(new Success());
        child = new ShadowClassTreeNode(shadow);
        listener = Turtle.mock(TreeModelListener.class);

        cabinet = new ShadowCabinet();
        cabinet.addListener(model);
        result = new TestResult();
        result.addListener(model);
    }

    public void testShouldNotSupportOperationValueForPathChanged() throws Exception {
        try {
            model.valueForPathChanged(null, null);
            fail();
        } catch (UnsupportedOperationException e) {
            assertNotNull(e.getMessage());
        }
    }

    public void testDefaultShadowTreeModelShouldHasRootTreeNode() throws Exception {
        assertEquals(root, model.getRoot());
        assertEquals(0, model.getChildCount(root));
        assertTrue(model.isLeaf(root));
    }

    public void testShouldAddComparableTestShadowAsChildOfRootNode() throws Exception {
        cabinet.add(shadow);
        assertEquals(0, model.getChildCount(child));
        assertEquals(1, model.getChildCount(root));
        assertEquals(child, model.getChild(root, 0));
        assertEquals(0, model.getIndexOfChild(root, child));
        assertEquals("1 Test Class", root.label());
    }

    public void testShouldRemoveComparableTestShadowFromRootNode() throws Exception {
        cabinet.add(shadow);
        cabinet.remove(shadow);
        assertEquals(0, model.getChildCount(root));
        assertEquals("0 Test Class", root.label());
    }

    public void testShouldFireTreeNodesInsertedEventWhenAfterAddTestEventIsFired() throws Exception {
        model.addTreeModelListener((TreeModelListener) listener.mockTarget());
        cabinet.add(shadow);

        listener.assertDid("treeNodesInserted").withFirstArgConstraint(childEventConstraint());
    }

    public void testShouldFireTreeNodesRemovedEventWhenAfterRemoveTestEventIsFired() throws Exception {
        model.addTreeModelListener((TreeModelListener) listener.mockTarget());
        cabinet.add(shadow);
        cabinet.remove(shadow);

        listener.assertDid("treeNodesRemoved").withFirstArgConstraint(childEventConstraint());
    }

    public void testShouldCreateTreeModelAfterCabinetAction() throws Exception {
        Test test1 = new SuccessTestShadow();
        Test test2 = new SuccessTestShadow();
        ComparableTestShadow shadow1 = new ComparableTestShadow(test1);
        ComparableTestShadow shadow2 = new ComparableTestShadow(test2);
        cabinet.add(shadow1);
        cabinet.add(shadow2);

        cabinet.action(result);

        assertEquals(2, model.getChildCount(root));
        for (int i = 0; i < 2; i++) {
            ShadowTreeNode child = (ShadowTreeNode) model.getChild(root, i);
            assertEquals(1, model.getChildCount(child));
        }
    }

    private Constraint childEventConstraint() {
        return new Constraint() {
            public CheckResult check(Object param) {
                TreeModelEvent event = (TreeModelEvent) param;

                assertEquals(model, event.getSource());
                assertEquals(1, event.getPath().length);
                assertEquals(new TreePath(root), event.getTreePath());

                assertEquals(1, event.getChildIndices().length);
                assertEquals(0, event.getChildIndices()[0]);

                assertEquals(1, event.getChildren().length);
                assertEquals(child, event.getChildren()[0]);

                return CheckResult.PASS;
            }
        };
    }
}
