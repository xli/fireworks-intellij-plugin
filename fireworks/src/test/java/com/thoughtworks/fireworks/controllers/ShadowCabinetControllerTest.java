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

import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

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
                (RecentTestListRunner) recentTestListRunner.mockTarget());
    }

    public void testFireRunAllTestsActionEvent() throws Exception {
        controller.fireRunAllTestsActionEvent();
        controllerListener.assertDid("actionStarted").then("actionFinished");
        allTestsRunner.assertDid("run");
    }

    public void testFireActionListener() throws Exception {
        controller.fireRunTestListActionEvent();
        controllerListener.assertDid("actionStarted").then("actionFinished");
        recentTestListRunner.assertDid("run");
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
}
