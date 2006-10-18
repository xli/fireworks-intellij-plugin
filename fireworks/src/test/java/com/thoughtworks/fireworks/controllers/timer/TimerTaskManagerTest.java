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

import com.thoughtworks.fireworks.core.ApplicationAdaptee;
import com.thoughtworks.fireworks.core.developer.Developer;
import com.thoughtworks.fireworks.core.developer.ReschedulableTask;
import com.thoughtworks.fireworks.core.developer.Thought;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskManagerTest extends TestCase {
    private static final int LONG_TIME = 10000;

    private Mock task;
    private TimerTaskManager manager;
    private Mock application;

    protected void setUp() throws Exception {
        task = Turtle.mock(ReschedulableTask.class);
        application = Turtle.mock(ApplicationAdaptee.class);
        Developer developer = new Developer(new Thought[0]);

        manager = new TimerTaskManager(new TimerTaskFactory((ApplicationAdaptee) application.mockTarget(), developer));
    }

    public void testShouldCancelPreTaskWhenGetNewTask() throws Exception {
        ReschedulableTask taskMock = (ReschedulableTask) task.mockTarget();
        TimerTask timerTask1 = manager.getTask(taskMock);
        TimerTask timerTask2 = manager.getTask(taskMock);

        assertTimerTaskHasBeenCanceled(timerTask1);

        scheduleTaskByTimer(timerTask2);
        assertTrue(timerTask2.cancel());
    }

    public void testShouldCreateTaskByTimerTaskFactory() throws Exception {
        TimerTask timerTask = manager.getTask((ReschedulableTask) task.mockTarget());
        timerTask.run();
        application.assertDid("invokeLater");
    }

    public void testCancelTask() throws Exception {
        TimerTask timerTask = manager.getTask((ReschedulableTask) task.mockTarget());

        scheduleTaskByTimer(timerTask);

        assertTrue(manager.cancelTask());
        assertTimerTaskHasBeenCanceled(timerTask);
        assertFalse(manager.cancelTask());
    }

    private void assertTimerTaskHasBeenCanceled(TimerTask timerTask) {
        try {
            scheduleTaskByTimer(timerTask);
            fail("Task already cancelled");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
        }
    }

    private void scheduleTaskByTimer(TimerTask timerTask) {
        Timer timer = new Timer();
        timer.schedule(timerTask, LONG_TIME);
    }
}
