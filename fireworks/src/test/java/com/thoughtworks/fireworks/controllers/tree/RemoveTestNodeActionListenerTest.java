package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.fireworks.core.tree.RemovableSource;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import javax.swing.tree.TreePath;
import java.awt.event.ActionListener;

public class RemoveTestNodeActionListenerTest extends TestCase {
    public void testShouldRemoveSelfWhenActionPerformed() throws Exception {
        Mock removable = Turtle.mock(RemovableSource.class);
        Mock tree = Turtle.mock(TestTree.class);
        tree.ifCall("getSelectionPath").willReturn(new TreePath(removable.mockTarget()));
        ActionListener listener = new RemoveTreeNodeActionListener((TestTree) tree.mockTarget());
        listener.actionPerformed(null);
        removable.assertDid("removeSelf");
    }
}
