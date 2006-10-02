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