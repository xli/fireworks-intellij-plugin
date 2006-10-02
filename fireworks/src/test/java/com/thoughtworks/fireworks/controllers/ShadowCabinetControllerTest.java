package com.thoughtworks.fireworks.controllers;

import com.thoughtworks.fireworks.core.TestResultFactory;
import com.thoughtworks.shadow.Cabinet;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.turtlemock.internal.ProxyTypeMock;
import junit.framework.TestCase;

public class ShadowCabinetControllerTest extends TestCase {
    private ShadowCabinetController controller;
    private Mock view;
    private TestResultFactory factory;
    private ProxyTypeMock cabinet;
    private Mock listener;

    protected void setUp() throws Exception {
        listener = Turtle.mock(RunAllTestsActionListener.class);
        view = Turtle.mock(ShadowCabinetView.class);
        cabinet = Turtle.mock(Cabinet.class);
        factory = new TestResultFactory(null, null);

        controller = new ShadowCabinetController((ShadowCabinetView) this.view.mockTarget(),
                factory,
                (Cabinet) cabinet.mockTarget(),
                (RunAllTestsActionListener) listener.mockTarget());
    }

    public void testFireRunAllTestsActionEvent() throws Exception {
        Mock controllerListener = Turtle.mock(ShadowCabinetControllerListener.class);
        controller.addListener((ShadowCabinetControllerListener) controllerListener.mockTarget());
        controller.fireRunAllTestsActionEvent();
        controllerListener.assertDid("actionWasFired");
        listener.assertDid("actionPerformed");
    }

    public void testFireActionListener() throws Exception {
        Mock listener = Turtle.mock(ShadowCabinetControllerListener.class);
        controller.addListener((ShadowCabinetControllerListener) listener.mockTarget());
        controller.fireRunTestListActionEvent();
        listener.assertDid("actionWasFired");
    }

    public void testShouldBeStartable() throws Exception {
        controller.start();
        view.assertDid("registerToolWindow");
        view.assertDid("addRunTestListActionListener");
        view.assertDid("addRunAllTestsActionListener");
        controller.stop();
        view.assertDid("unregisterToolWindow");
        view.assertDid("removeAllActionListeners");
    }

    public void testFireActionEvent() throws Exception {
        controller.fireRunTestListActionEvent();

        cabinet.assertDid("action");
    }
}
