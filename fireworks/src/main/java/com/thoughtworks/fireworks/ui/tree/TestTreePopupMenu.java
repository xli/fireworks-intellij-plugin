package com.thoughtworks.fireworks.ui.tree;

import com.thoughtworks.fireworks.controllers.tree.JumpToSourceActionListener;
import com.thoughtworks.fireworks.controllers.tree.RemoveTreeNodeActionListener;
import com.thoughtworks.fireworks.controllers.tree.TestTree;
import com.thoughtworks.fireworks.core.tree.RemovableSource;

import javax.swing.*;
import java.awt.*;

public class TestTreePopupMenu {
    private static final String REMOVE_MENU_ITEM_NAME = "Remove";
    private static final String JUMP_TO_SOURCE_MENU_ITEM_NAME = "Jump to source";

    private final JPopupMenu menu;
    private final JMenuItem removeMenuItem;
    private final JMenuItem jumpToSource;
    private final Component invoker;

    public TestTreePopupMenu(TestShadowListTree invoker) {
        this(invoker, invoker);
    }

    private TestTreePopupMenu(Component invoker, TestTree testTree) {
        this.invoker = invoker;
        menu = new JPopupMenu();
        removeMenuItem = new JMenuItem(REMOVE_MENU_ITEM_NAME);
        jumpToSource = new JMenuItem(JUMP_TO_SOURCE_MENU_ITEM_NAME);
        menu.add(removeMenuItem);
        menu.add(jumpToSource);

        removeMenuItem.addActionListener(new RemoveTreeNodeActionListener(testTree));
        jumpToSource.addActionListener(new JumpToSourceActionListener(testTree));
    }

    public void showMenu(RemovableSource node, int x, int y) {
        removeMenuItem.setEnabled(node.isRemovable());
        jumpToSource.setEnabled(node.isSource());
        menu.show(invoker, x, y);
    }
}
