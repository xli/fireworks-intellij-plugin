package com.thoughtworks.fireworks.controllers;

public interface CabinetController {
    void fireRunTestListActionEvent();

    void addListener(ShadowCabinetControllerListener listener);

    void fireRunAllTestsActionEvent();
}
