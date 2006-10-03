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

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

import java.util.ArrayList;
import java.util.List;

public class TestCounter implements TestListener {
    private List listeners = new ArrayList();
    private int runCount;
    private int failuresCount;
    private int errorsCount;

    public void addError(Test test, Throwable t) {
        errorsCount++;
    }

    public void addFailure(Test test, AssertionFailedError t) {
        failuresCount++;
    }

    public void endTest(Test test) {
        fireEvent();
    }

    private void fireEvent() {
        for (int i = 0; i < listeners.size(); i++) {
            ((TestCounterListener) listeners.get(i)).testResult(runCount, failuresCount, errorsCount);
        }
    }

    public void startTest(Test test) {
        runCount++;
    }

    public void addListener(TestCounterListener listener) {
        listeners.add(listener);
    }

    public void start() {
        fireEvent();
    }
}
