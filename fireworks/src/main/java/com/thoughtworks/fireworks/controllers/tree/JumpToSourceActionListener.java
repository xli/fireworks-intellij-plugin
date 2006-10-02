package com.thoughtworks.fireworks.controllers.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JumpToSourceActionListener implements ActionListener {
    private final TestTree tree;

    public JumpToSourceActionListener(TestTree tree) {
        this.tree = tree;
    }

    public void actionPerformed(ActionEvent e) {
        tree.jumpToSourceOfNode(tree.getSelectionPath());
    }
}
