package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.tree.TreePath;
import java.awt.event.ActionListener;

public class JumpToSourceActionListenerTest extends TestCase {
    public void testShouldRemoveSelfWhenActionPerformed() throws Exception {
        Mock tree = Turtle.mock(TestTree.class);
        TreePath selectionPath = new TreePath(this);
        tree.ifCall("getSelectionPath").willReturn(selectionPath);
        ActionListener listener = new JumpToSourceActionListener((TestTree) tree.mockTarget());
        listener.actionPerformed(null);

        tree.assertDid("jumpToSourceOfNode").with(selectionPath);
    }
}
