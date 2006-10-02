package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.fireworks.core.tree.RemovableSource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveTreeNodeActionListener implements ActionListener {
    private final TestTree tree;

    public RemoveTreeNodeActionListener(TestTree tree) {
        this.tree = tree;
    }

    public void actionPerformed(ActionEvent e) {
        selectionNode().removeSelf();
    }

    private RemovableSource selectionNode() {
        return (RemovableSource) tree.getSelectionPath().getLastPathComponent();
    }
}
