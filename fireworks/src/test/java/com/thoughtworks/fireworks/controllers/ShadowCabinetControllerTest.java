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

import com.thoughtworks.turtlemock.Executable;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import com.thoughtworks.fireworks.core.FireworksRunningStatus;
import junit.framework.TestCase;

import java.awt.event.ActionListener;

public class ShadowCabinetControllerTest extends TestCase {
    private ShadowCabinetController controller;
    private Mock view;
    private Mock allTestsRunner;
    private Mock recentTestListRunner;
    private Mock controllerListener;

    protected void setUp() throws Exception {
        controllerListener = Turtle.mock(ShadowCabinetControllerListener.class);

        allTestsRunner = Turtle.mock(AllTestsRunner.class);
        recentTestListRunner = Turtle.mock(RecentTestListRunner.class);
        view = Turtle.mock(ShadowCabinetView.class);

        controller = new ShadowCabinetController(
                new ShadowCabinetControllerListener[]{(ShadowCabinetControllerListener) controllerListener.mockTarget()},
                (ShadowCabinetView) view.mockTarget(),
                (AllTestsRunner) allTestsRunner.mockTarget(),
                (RecentTestListRunner) recentTestListRunner.mockTarget(),
                new FireworksRunningStatus());
    }

    public void testFireRunAllTestsActionEvent() throws Exception {
        controller.fireRunAllTestsActionEvent();
        assertRunAllTestsActionEventWasFired();
    }

    public void testFireRunTestListActionEvent() throws Exception {
        controller.fireRunTestListActionEvent();
        assertRunRecentTestListActionEventWasFired();
    }

    public void testAddRunAllTestsRunnerActionIntoView() throws Exception {
        view.ifCall("addRunAllTestsActionListener").willExecute(performActinListener());

        controller.start();
        assertRunAllTestsActionEventWasFired();
    }

    public void testAddRunTestListActionListenerIntoView() throws Exception {
        view.ifCall("addRunTestListActionListener").willExecute(performActinListener());

        controller.start();
        assertRunRecentTestListActionEventWasFired();
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

    private Executable performActinListener() {
        return new Executable() {
            public Object execute(Object[] objects) {
                ActionListener listener = (ActionListener) objects[0];
                listener.actionPerformed(null);
                return null;
            }
        };
    }

    private void assertRunAllTestsActionEventWasFired() {
        controllerListener.assertDid("actionStarted").then("actionFinished");
        allTestsRunner.assertDid("run");
    }

    private void assertRunRecentTestListActionEventWasFired() {
        controllerListener.assertDid("actionStarted").then("actionFinished");
        recentTestListRunner.assertDid("run");
    }
}
