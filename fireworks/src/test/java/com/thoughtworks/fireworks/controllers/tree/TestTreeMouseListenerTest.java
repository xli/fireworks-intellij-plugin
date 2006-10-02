package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TestTreeMouseListenerTest extends TestCase {
    private final JTree source = new JTree();
    private final int x = 1;
    private final int y = 2;
    private final int clickOnce = 1;
    private final int clickTwice = 2;
    private Mock tree;
    private MouseListener listener;
    private TreePath pathForEventLocation;

    protected void setUp() throws Exception {
        tree = Turtle.mock(TestTree.class);
        listener = new TestTreeMouseListener((TestTree) tree.mockTarget());
        pathForEventLocation = new TreePath(this);
        tree.ifCall("getPathForLocation").willReturn(pathForEventLocation);
    }

    public void testShouldNotSelectPathAndShowMenuIfPathForEventLocationIsNull() throws Exception {
        tree = Turtle.mock(TestTree.class);
        listener = new TestTreeMouseListener((TestTree) tree.mockTarget());

        listener.mouseClicked(createMouseEvent(clickOnce, MouseEvent.BUTTON3));
        tree.assertNotDid("setSelectionPath");
        tree.assertNotDid("showMenu");
    }

    public void testShouldShowMenuWhenRightClickMouse() throws Exception {
        listener.mouseClicked(createMouseEvent(clickOnce, MouseEvent.BUTTON3));

        tree.assertDid("showMenu").with(pathForEventLocation, new Integer(x), new Integer(y));
    }

    public void testShouldSelectPathWhereMouseClicked() throws Exception {
        listener.mouseClicked(createMouseEvent(clickOnce, MouseEvent.BUTTON3));
        tree.assertDid("setSelectionPath").with(pathForEventLocation);
    }

    public void testShouldJumpToPathNodeSourceAndNotShowMenuIfDoubleClickLeftButton() throws Exception {
        listener.mouseClicked(createMouseEvent(clickTwice, MouseEvent.BUTTON1));
        tree.assertDid("jumpToSourceOfNode").with(pathForEventLocation);
        tree.assertNotDid("showMenu");
    }

    public void testShouldNotJumpToSourceOfNodeIfClickLeftButtonOnce() throws Exception {
        listener.mouseClicked(createMouseEvent(clickOnce, MouseEvent.BUTTON1));
        tree.assertNotDid("jumpToSourceOfNode");
        tree.assertNotDid("showMenu");
    }

    public void testShouldNotJumpToSourceOfNodeIfDoubleClickRightButton() throws Exception {
        listener.mouseClicked(createMouseEvent(clickTwice, MouseEvent.BUTTON3));
        tree.assertDid("showMenu");
        tree.assertNotDid("jumpToSourceOfNode");
    }

    private MouseEvent createMouseEvent(int clickCount, int button) {
        return new MouseEvent(source, 0, 0, 0, x, y, clickCount, false, button);
    }
}
