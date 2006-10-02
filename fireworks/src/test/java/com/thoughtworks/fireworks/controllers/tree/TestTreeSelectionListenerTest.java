package com.thoughtworks.fireworks.controllers.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import junit.framework.TestCase;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;

public class TestTreeSelectionListenerTest extends TestCase implements Log, ConsoleViewAdaptee {
    private final String log = "log";

    private String outputed;

    public void testShouldOutputLogWhenValueChanged() throws Exception {
        TreeSelectionListener listener = new TestTreeSelectionListener(this, this);
        listener.valueChanged(null);
        assertEquals(log, outputed);
    }

    public void output(ConsoleViewAdaptee consoleView) {
        consoleView.cleanAndPrint(log);
    }

    public void cleanAndPrint(String text) {
        outputed = text;
    }

    public void dispose() {
    }

    public void clear() {
    }

    public void printlnSystemOutput(String message) {
    }

    public void printlnErrorOutput(String message) {
    }

    public JComponent getComponent() {
        return null;
    }

}
