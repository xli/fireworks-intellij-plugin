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
package com.thoughtworks.fireworks.controllers.timer;

import com.thoughtworks.fireworks.controllers.CabinetController;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class ReschedulableTaskAdapterTest extends TestCase {

    private static final int LONG_TIME = 10000;

    private TimerTaskManager manager;
    private Mock controller;
    private ReschedulableTaskAdapter taskAdapter;
    private CabinetControllerActionTimer timer;

    protected void setUp() throws Exception {
        manager = new TimerTaskManager(TestUtils.createTimerTaskFactory());
        controller = Turtle.mock(CabinetController.class);
        timer = new CabinetControllerActionTimer(new TimerScheduler(manager), (CabinetController) controller.mockTarget());

        taskAdapter = new ReschedulableTaskAdapter(timer, (CabinetController) controller.mockTarget());
    }

    public void testShouldFireRunTestListActionEventWhenRunTaskAdapter() throws Exception {
        taskAdapter.run();
        controller.assertDid("fireRunTestListActionEvent");
    }

    public void testRescheduleTaskByCabinetControllerActionTimer() throws Exception {
        timer.schedule(LONG_TIME);
        manager.cancelTask();
        taskAdapter.reschedule();

        assertTrue(manager.cancelTask());
    }
}
