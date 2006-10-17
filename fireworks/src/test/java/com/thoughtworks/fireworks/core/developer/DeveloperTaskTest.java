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

import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class DeveloperTaskTest extends TestCase {
    private Developer developer;
    private Mock task;
    private DeveloperTask developerTask;
    private Mock thought;

    protected void setUp() throws Exception {
        thought = Turtle.mock(Thought.class);
        developer = new Developer(new Thought[]{(Thought) thought.mockTarget()});
        task = Turtle.mock(ReschedulableTask.class);
        developerTask = new DeveloperTask(developer, (ReschedulableTask) task.mockTarget());
    }

    public void testShouldConsiderRunningTask() throws Exception {
        thought.ifCall("isWorking").willReturn(Boolean.FALSE);        
        developerTask.run();
        task.assertDid("run");
    }

    public void testShouldRescheduleTaskWhenDeveloperIsThinking() throws Exception {
        thought.ifCall("isWorking").willReturn(Boolean.TRUE);
        developerTask = new DeveloperTask(developer, (ReschedulableTask) task.mockTarget());
        developerTask.run();
        task.assertDid("reschedule");
        task.assertNotDid("run");
    }

    public void testShouldDelegateReschedule() throws Exception {
        developerTask.reschedule();
        task.assertDid("reschedule");
        thought.assertDidNothing();
    }
}
