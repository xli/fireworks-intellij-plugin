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

public class TimeoutableTaskTest extends TestCase {
    private static final long ONE_SECOND = 1000;

    private Mock task;
    private TimeoutableTask timeoutableTask;

    protected void setUp() throws Exception {
        task = Turtle.mock(ReschedulableTask.class);
        timeoutableTask = new TimeoutableTask((ReschedulableTask) task.mockTarget());
    }

    public void testRunTask() throws Exception {
        timeoutableTask.run();
        task.assertDid("run");
    }

    public void testShouldRescheduleTaskIfRunTaskTimeout() throws Exception {
        Thread.sleep(ONE_SECOND);
        timeoutableTask.run();
        task.assertDid("reschedule");
        task.assertNotDid("run");
    }
}
