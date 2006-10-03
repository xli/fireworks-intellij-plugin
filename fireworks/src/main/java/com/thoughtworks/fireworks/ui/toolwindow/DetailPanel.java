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
package com.thoughtworks.fireworks.ui.toolwindow;

import com.thoughtworks.fireworks.ui.table.TestShadowResultTable;
import com.thoughtworks.fireworks.ui.tree.TreeDetailPanel;

import javax.swing.*;

public class DetailPanel {
    private static final String FAILURES_TAB_TITLE = "Failures";
    private static final String HIERARCHY_TAB_TITLE = "Hierarchy";
    private static final String LOG_TAB_TITLE = "Log";

    private final JTabbedPane tabbedPane;

    public DetailPanel(TreeDetailPanel treePanel, TestShadowResultTable table, TestLogPanel logPanel) {
        tabbedPane = new JTabbedPane();
        tabbedPane.add(FAILURES_TAB_TITLE, tableDetailPanel(table));
        tabbedPane.add(HIERARCHY_TAB_TITLE, treePanel.getComponent());
        tabbedPane.add(LOG_TAB_TITLE, logPanel);
    }

    private JComponent tableDetailPanel(TestShadowResultTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return new JScrollPane(table);
    }

    public JComponent getComponent() {
        return tabbedPane;
    }
}