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
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class ConfiguredTimerTest extends TestCase {

    private static final Integer LONG_TIME = new Integer(10000);

    private TimerTaskManager manager;
    private TimerScheduler scheduler;
    private ConfiguredTimer timer;
    private Mock config;
    private Mock controller;

    protected void setUp() throws Exception {
        manager = new TimerTaskManager(TestUtils.createTimerTaskFactory());
        scheduler = new TimerScheduler(manager);

        config = Turtle.mock(FireworksConfig.class);
        config.ifCall("autoRunTestsDelayTime").willReturn(LONG_TIME);
        controller = Turtle.mock(CabinetController.class);

        timer = new ConfiguredTimer((FireworksConfig) config.mockTarget(), new CabinetControllerActionTimer(scheduler, (CabinetController) controller.mockTarget()));
    }

    public void testSchedule() throws Exception {
        timer.schedule();
        assertTrue(manager.cancelTask());
    }

    public void testReschedule() throws Exception {
        timer.reschedule();
        assertFalse(manager.cancelTask());

        timer.schedule();
        manager.cancelTask();
        timer.reschedule();
        assertTrue(manager.cancelTask());
    }

    public void testCancelTasks() throws Exception {
        timer.cancelTasks();
        assertFalse(manager.cancelTask());

        timer.schedule();
        timer.cancelTasks();
        assertFalse(manager.cancelTask());
    }
}
