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

public class TestCounterTest extends TestCase implements TestCounterListener, TestShadow {
    private int runCount;
    private int failuresCount;
    private int errorsCount;
    private int ignoreCount;
    private TestCounter counter;
    private TestShadowResult result;

    protected void setUp() throws Exception {
        runCount = -1;
        failuresCount = -1;
        errorsCount = -1;
        ignoreCount = -1;
        counter = new TestCounter();
        counter.addListener(this);
        result = new TestShadowResult();
        result.addListener(counter);
    }

    public void testShouldFireAsCabinetListener() throws Exception {
        counter.start();
        assertEquals(0, runCount);
        assertEquals(0, failuresCount);
        assertEquals(0, errorsCount);
        assertEquals(0, ignoreCount);
    }

    public void testShouldFireListenerWithTestResult() throws Exception {
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

        assertEquals(runCount, result.runCount());
        assertEquals(failuresCount, result.failureCount());
        assertEquals(errorsCount, result.errorCount());
        assertEquals(ignoreCount, result.ignoreCount());
    }

    public void testResult(int runCount, int failureCount, int errorCount, int ignoreCount) {
        this.runCount = runCount;
        this.failuresCount = failureCount;
        this.errorsCount = errorCount;
        this.ignoreCount = ignoreCount;
    }

    public void accept(ShadowVisitor visitor) {
    }
}
