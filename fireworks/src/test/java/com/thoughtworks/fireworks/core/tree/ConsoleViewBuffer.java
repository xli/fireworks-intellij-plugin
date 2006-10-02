package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.shadow.Utils;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ConsoleViewBuffer implements ConsoleViewAdaptee {
    private final PrintWriter writer;

    public ConsoleViewBuffer(StringWriter writer) {
        this.writer = new PrintWriter(writer);
    }

    public JComponent getComponent() {
        return null;
    }

    public void cleanAndPrint(String text) {
        if (Utils.isEmpty(text)) {
            return;
        }
        writer.println(text);
    }

    public void dispose() {
    }

    public void clear() {
    }

    public void printlnSystemOutput(String message) {
    }

    public void printlnErrorOutput(String message) {
    }
}
