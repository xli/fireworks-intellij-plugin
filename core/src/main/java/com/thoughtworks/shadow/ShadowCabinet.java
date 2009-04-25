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

import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.util.*;

public class ShadowCabinet implements Cabinet {

    private static final int DEFAULT_MAX_SIZE = 5;

    private final List<ShadowCabinetListener> listeners = new ArrayList<ShadowCabinetListener>();
    private final LinkedList<ComparableTestShadow> shadows = new LinkedList<ComparableTestShadow>();
    private int maxSize = DEFAULT_MAX_SIZE;

    public void add(ComparableTestShadow testShadow) {
        if (maxSize == 0) {
            return;
        }
        addIntoSet(testShadow);
    }

    public void addListener(ShadowCabinetListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ShadowCabinetListener listener) {
        listeners.remove(listener);
    }

    public void action(TestResult result) {
        fireStartActionEvent();
        try {
            TestSuite suite = new TestSuite();
            for (Iterator<ComparableTestShadow> iter = shadows.iterator(); iter.hasNext();) {
                suite.addTest(iter.next());
            }
            suite.run(result);
        } finally {
            fireEndActionEvent();
        }
    }

    public int maxSize() {
        return maxSize;
    }

    public int size() {
        return shadows.size();
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        removeMaxPassTimesTests(maxSize);
    }

    private void removeMaxPassTimesTests(int maxSize) {
        Collections.sort(shadows);
        while (size() > maxSize) {
            fireAfterRemoveTestEvent(shadows.removeLast());
        }
    }

    public void remove(ComparableTestShadow test) {
        if (shadows.remove(test)) {
            fireAfterRemoveTestEvent(test);
        }
    }

    private void addIntoSet(ComparableTestShadow testShadow) {
        if (shadows.contains(testShadow)) {
            return;
        }
        if (size() >= maxSize) {
            removeMaxPassTimesTests(maxSize - 1);
        }
        testShadow.setContainer(this);
        shadows.add(testShadow);
        fireAfterAddTestEvent(testShadow);
    }

    private void fireAfterAddTestEvent(ComparableTestShadow test) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).afterAddTest(test);
        }
    }

    private void fireAfterRemoveTestEvent(ComparableTestShadow test) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).afterRemoveTest(test);
        }
    }

    private void fireEndActionEvent() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).endAction();
        }
    }

    private void fireStartActionEvent() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).startAction();
        }
    }
}
