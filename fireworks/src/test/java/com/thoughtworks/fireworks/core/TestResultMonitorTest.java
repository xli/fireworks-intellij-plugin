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
package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.shadow.TestShadowResult;
import junit.framework.TestCase;

public class TestResultMonitorTest extends TestCase implements ResultOfTestEndListener, TestShadow {
    private TestResultMonitor resultMonitor;
    private TestShadowResult result;
    private TestShadowResult testResultListenered;

    protected void setUp() throws Exception {
        result = new TestShadowResult();
        resultMonitor = new TestResultMonitor(result);
        resultMonitor.addListener(this);
        testResultListenered = null;
    }

    public void testShouldFireAsCabinetListenerAfterStart() throws Exception {
        resultMonitor.start();
        assertSame(testResultListenered, result);
    }

    public void testShouldFireListenerWithTestResult() throws Exception {
        resultMonitor.start();
        result.addListener(resultMonitor);
        result.startTest(this);
        result.endTest(this);

        result.startTest(this);
        result.addError(this, null);
        result.endTest(this);

        result.startTest(this);
        result.addFailure(this, null);
        result.endTest(this);

        result.startTest(this);
        result.testIgnored(this);
        result.endTest(this);

        assertSame(testResultListenered, result);
    }

    public void testEnd(TestShadowResult result) {
        this.testResultListenered = result;
    }

    public void accept(ShadowVisitor visitor) {
    }
}
