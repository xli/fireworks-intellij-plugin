package com.thoughtworks.fireworks.adapters;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.shadow.Utils;

import javax.swing.*;

public class ConsoleViewAdapter implements ConsoleViewAdaptee {
    private final ConsoleView console;
    private JComponent component;

    public ConsoleViewAdapter(ConsoleView console) {
        this.console = console;
        this.component = console.getComponent();
    }

    public JComponent getComponent() {
        return component;
    }

    public void cleanAndPrint(String text) {
        clear();
        printButFilterEmptyAndNull(text, ConsoleViewContentType.NORMAL_OUTPUT);
    }

    private void printButFilterEmptyAndNull(String text, ConsoleViewContentType type) {
        if (!Utils.isEmpty(text)) {
            console.print(text, type);
        }
    }

    public void dispose() {
        console.dispose();
    }

    public void clear() {
        console.clear();
    }

    public void printlnSystemOutput(String message) {
        printButFilterEmptyAndNull(message, ConsoleViewContentType.SYSTEM_OUTPUT);
        println(ConsoleViewContentType.SYSTEM_OUTPUT);
    }

    public void printlnErrorOutput(String message) {
        printButFilterEmptyAndNull(message, ConsoleViewContentType.ERROR_OUTPUT);
        println(ConsoleViewContentType.ERROR_OUTPUT);
    }

    private void println(ConsoleViewContentType type) {
        printButFilterEmptyAndNull(Utils.LINE_SEP, type);
    }

}
