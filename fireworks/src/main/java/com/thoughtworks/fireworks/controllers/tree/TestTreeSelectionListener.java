package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class TestTreeSelectionListener implements TreeSelectionListener {
    private final Log log;
    private final ConsoleViewAdaptee consoleView;

    public TestTreeSelectionListener(Log log, ConsoleViewAdaptee consoleView) {
        this.log = log;
        this.consoleView = consoleView;
    }

    public void valueChanged(TreeSelectionEvent e) {
        log.output(consoleView);
    }
}
