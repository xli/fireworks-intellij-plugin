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
package com.thoughtworks.fireworks.ui.tree;

import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.controllers.tree.TestTreeSelectionListener;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;

import javax.swing.*;
import java.awt.*;

public class TreeDetailPanel {
    private static final String TEST_LIST_PANEL_TITLE = "Recent Test List";
    private static final String CONSOLE_VIEW_PANEL_TITLE = "Console";
    private final JComponent treeDetailPanel;

    public TreeDetailPanel(TestShadowListTree tree, ProjectAdapter projectAdapter) {
        ConsoleViewAdaptee consoleView = projectAdapter.createTextConsoleBuilder();
        tree.getSelectionModel().addTreeSelectionListener(new TestTreeSelectionListener(tree, consoleView));
        treeDetailPanel = treeDetailPanel(tree, consoleView);
    }

    public JComponent getComponent() {
        return treeDetailPanel;
    }

    private JComponent treeDetailPanel(JTree tree, ConsoleViewAdaptee consoleView) {
        JComponent left = decorateTitle(new JScrollPane(tree), TEST_LIST_PANEL_TITLE);
        JComponent right = decorateTitle(consoleView.getComponent(), CONSOLE_VIEW_PANEL_TITLE);
        JSplitPane treePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        treePane.setDividerLocation(300);
        treePane.setOneTouchExpandable(true);
        return treePane;
    }

    private JComponent decorateTitle(JComponent comp, String title) {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(BorderFactory.createTitledBorder(title));
        pane.add(comp, BorderLayout.CENTER);
        return pane;
    }
}
