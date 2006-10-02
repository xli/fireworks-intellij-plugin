package com.thoughtworks.fireworks.ui.table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClearTableSelectionListener extends WindowAdapter {
    private final TestShadowResultTable table;

    public ClearTableSelectionListener(TestShadowResultTable table) {
        this.table = table;
    }

    public void windowClosed(WindowEvent e) {
        table.clearSelection();
    }

    public void windowLostFocus(WindowEvent e) {
        e.getWindow().dispose();
    }

}
