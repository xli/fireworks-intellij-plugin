/*
 *    Copyright (c) 2006 LiXiao
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
package com.thoughtworks.shadow;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

import java.util.ArrayList;
import java.util.List;

public class TestState implements Comparable, TestListener {
    private final List listeners = new ArrayList();
    private final ComparableTestShadow shadow;
    private int passTimes;
    private boolean wasSuccessful;

    public TestState(ComparableTestShadow shadow) {
        this.shadow = shadow;
    }

    public int compareTo(Object o) {
        return passTimes - ((TestState) o).passTimes;
    }

    public void addError(Test test, Throwable t) {
        fail();
    }

    public void addFailure(Test test, AssertionFailedError t) {
        fail();
    }

    public void endTest(Test test) {
        if (test == this.shadow) {
            if (wasSuccessful) {
                pass();
            }
            fireEndTestEvent();
        }
    }

    public void addListener(TestStateListener listener) {
        listeners.add(listener);
    }

    private void fireEndTestEvent() {
        for (int i = 0; i < listeners.size(); i++) {
            TestStateListener listener = (TestStateListener) listeners.get(i);
            listener.endTestShadow(shadow, wasSuccessful);
        }
    }

    public void startTest(Test test) {
        if (test == this.shadow) {
            wasSuccessful = true;
        }
    }

    public String toString() {
        return getClass().getName() + "#" + shadow + ", passTimes: " + passTimes;
    }

    private void pass() {
        if (passTimes < 0) {
            passTimes = 1;
        } else {
            passTimes = ++passTimes;
        }
    }

    private void fail() {
        wasSuccessful = false;
        if (passTimes > 0) {
            passTimes = -1;
        } else {
            passTimes = --passTimes;
        }
    }

}
