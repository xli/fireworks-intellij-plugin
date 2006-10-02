package com.thoughtworks.fireworks.core;

import javax.swing.*;

public interface ConsoleViewAdaptee {
    JComponent getComponent();

    void cleanAndPrint(String text);

    void dispose();

    void clear();

    public void printlnSystemOutput(String message);

    public void printlnErrorOutput(String message);

}
