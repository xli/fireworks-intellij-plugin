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

import com.thoughtworks.shadow.TestShadowResult;
import junit.framework.TestListener;
import junit.framework.TestResult;

public class TestResultFactory {
    private final TestListener[] testListeners;
    private final ResultOfTestEndListener[] counterListeners;

    public TestResultFactory(TestListener[] testListeners, ResultOfTestEndListener[] counterListeners) {
        this.testListeners = testListeners == null ? new TestListener[0] : testListeners;
        this.counterListeners = counterListeners == null ? new ResultOfTestEndListener[0] : counterListeners;
    }

    public TestResult createTestResult() {
        TestShadowResult result = new TestShadowResult();
        result.addListener(getTestCounter(result));
        for (int i = 0; i < testListeners.length; i++) {
            result.addListener(testListeners[i]);
        }
        return result;
    }

    private TestResultMonitor getTestCounter(TestShadowResult result) {
        TestResultMonitor resultMonitor = new TestResultMonitor(result);
        for (int i = 0; i < counterListeners.length; i++) {
            resultMonitor.addListener(counterListeners[i]);
        }
        resultMonitor.start();
        return resultMonitor;
    }
}
