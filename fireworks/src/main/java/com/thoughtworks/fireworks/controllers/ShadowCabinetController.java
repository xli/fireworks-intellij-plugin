package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.TestResultFactory;
import com.thoughtworks.shadow.Cabinet;
import org.picocontainer.Startable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShadowCabinetController implements Startable, CabinetController {
    private final ShadowCabinetView view;
    private final TestResultFactory factory;
    private final Cabinet shadowCabinet;
    private final RunAllTestsActionListener runAllTests;
    private final List listeners = new ArrayList();

    public ShadowCabinetController(ShadowCabinetView view,
                                   TestResultFactory factory,
                                   Cabinet shadowCabinet,
                                   RunAllTestsActionListener runAllTests) {
        this.view = view;
        this.factory = factory;
        this.shadowCabinet = shadowCabinet;
        this.runAllTests = runAllTests;
    }

    public void start() {
        view.registerToolWindow();
        view.addRunTestListActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireRunTestListActionEvent();
            }
        });
        view.addRunAllTestsActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireRunAllTestsActionEvent();
            }
        });
    }

    public void stop() {
        view.unregisterToolWindow();
        view.removeAllActionListeners();
    }

    public void addListener(ShadowCabinetControllerListener listener) {
        listeners.add(listener);
    }

    public void fireRunAllTestsActionEvent() {
        fireActionWasFiredEvent();
        runAllTests.actionPerformed(factory.createTestResult());
    }

    public void fireRunTestListActionEvent() {
        fireActionWasFiredEvent();
        shadowCabinet.action(factory.createTestResult());
    }

    private void fireActionWasFiredEvent() {
        for (int i = 0; i < listeners.size(); i++) {
            ((ShadowCabinetControllerListener) listeners.get(i)).actionWasFired();
        }
    }
}
