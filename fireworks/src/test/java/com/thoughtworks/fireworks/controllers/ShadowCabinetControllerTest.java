/*
 *    Copyright (c) 2006 LiXiao.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
