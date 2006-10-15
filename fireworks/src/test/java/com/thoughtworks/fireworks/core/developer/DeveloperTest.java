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
package com.thoughtworks.fireworks.core.developer;

import com.thoughtworks.fireworks.core.ApplicationAdaptee;
import com.thoughtworks.turtlemock.Executable;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class DeveloperTest extends TestCase {
    private Mock application;
    private Mock thought;
    private Developer developer;
    private Mock task;
    private ReschedulableTask taskMocked;

    protected void setUp() throws Exception {
        application = Turtle.mock(ApplicationAdaptee.class);
        application.ifCall("invokeLater").willExecute(new Executable() {
            public Object execute(Object[] objects) {
                ((TimeoutableTask) objects[0]).run();
                return null;
            }
        });

        thought = Turtle.mock(Thought.class);
        developer = new Developer((ApplicationAdaptee) application.mockTarget(), new Thought[]{(Thought) thought.mockTarget()});

        task = Turtle.mock(ReschedulableTask.class);
        taskMocked = (ReschedulableTask) task.mockTarget();
    }

    public void testShouldRunTaskIfDeveloperIsNotThinking() throws Exception {
        thought.ifCall("isWorking").willReturn(Boolean.FALSE);
        developer.considersRunning(taskMocked);
        task.assertDid("run");
    }

    public void testShouldNotRunTaskIfDeveloperIsThinking() throws Exception {
        thought.ifCall("isWorking").willReturn(Boolean.TRUE);
        developer.considersRunning((ReschedulableTask) task.mockTarget());
        task.assertNotDid("run");
        application.assertDidNothing();
    }
}
