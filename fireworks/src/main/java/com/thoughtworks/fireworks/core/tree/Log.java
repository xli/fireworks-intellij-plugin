package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;

public interface Log {
    public void output(ShadowTreeNode node, ConsoleViewAdaptee consoleView);
}
