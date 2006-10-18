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
import com.thoughtworks.turtlemock.Executable;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;

public class RecentTestListRunnerAdapterTest extends TestCase implements Executable {
    private RecentTestListRunner runner;
    private Mock listener;
    private Mock cabinet;

    protected void setUp() throws Exception {
        cabinet = Turtle.mock(Cabinet.class);
        cabinet.ifCall("action").willExecute(this);
        listener = Turtle.mock(TestListener.class);
        TestListener[] listeners = new TestListener[]{(TestListener) listener.mockTarget()};
        TestResultFactory factory = new TestResultFactory(listeners, null);
        runner = new RecentTestListRunnerAdapter((Cabinet) cabinet.mockTarget(), factory);
    }

    public void testShouldDelegateActionOfCabinet() throws Exception {
        runner.run();
        cabinet.assertDid("action");
        listener.assertDid("startTest").with(this);
        listener.assertDid("endTest").with(this);
    }

    public Object execute(Object[] objects) {
        TestResult result = (TestResult) objects[0];
        result.startTest(this);
        result.endTest(this);
        return null;
    }
}
