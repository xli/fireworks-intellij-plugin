package com.thoughtworks.fireworks.ui.toolwindow;

import com.thoughtworks.fireworks.controllers.ShadowCabinetView;

import java.awt.event.ActionListener;

public class ShadowCabinetViewToolWindowImpl implements ShadowCabinetView {

    private final ActionPanel actionPanel;
    private final ToolWindow window;

    public ShadowCabinetViewToolWindowImpl(ActionPanel actionPanel, ToolWindow window) {
        this.actionPanel = actionPanel;
        this.window = window;
    }

    public void registerToolWindow() {
        window.register();
    }

    public void unregisterToolWindow() {
        window.unregister();
    }

    public void addRunTestListActionListener(ActionListener listener) {
        actionPanel.addRunTestListActionListener(listener);
    }

    public void addRunAllTestsActionListener(ActionListener listener) {
        actionPanel.addRunAllTestsActionListener(listener);
    }

    public void removeAllActionListeners() {
        actionPanel.removeAllActionListeners();
    }

}
