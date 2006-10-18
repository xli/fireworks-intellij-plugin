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
import com.thoughtworks.fireworks.core.developer.TimeoutableTask;
import com.thoughtworks.turtlemock.Executable;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

import java.util.TimerTask;

public class TimerTaskFactoryTest extends TestCase {
    private Mock task;
    private Mock application;
    private Developer developer;
    private TimerTaskFactory factory;

    protected void setUp() throws Exception {
        task = Turtle.mock(ReschedulableTask.class);
        application = Turtle.mock(ApplicationAdaptee.class);
        application.ifCall("invokeLater").willExecute(new Executable() {
            public Object execute(Object[] objects) {
                ((TimeoutableTask) objects[0]).run();
                return null;
            }
        });
        developer = new Developer(new Thought[0]);
        factory = new TimerTaskFactory((ApplicationAdaptee) application.mockTarget(), developer);
    }

    public void testCreateTask() throws Exception {
        TimerTask timerTask = factory.createTimerTask((ReschedulableTask) task.mockTarget());
        timerTask.run();

        application.assertDid("invokeLater");
        task.assertDid("run");
    }
}
