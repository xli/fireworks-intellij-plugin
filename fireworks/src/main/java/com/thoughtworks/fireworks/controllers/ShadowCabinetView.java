package com.thoughtworks.fireworks.controllers;

import java.awt.event.ActionListener;

public interface ShadowCabinetView {
    void registerToolWindow();

    void unregisterToolWindow();

    void addRunTestListActionListener(ActionListener listener);

    void removeAllActionListeners();

    void addRunAllTestsActionListener(ActionListener listener);
}
