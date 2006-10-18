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

import com.thoughtworks.fireworks.core.ResultOfTestEndListener;
import com.thoughtworks.shadow.TestShadowResult;

import java.awt.*;

public class TestResultSummaryBgColor implements ResultOfTestEndListener {
    private final TestResultSummaryBgColorListener[] listeners;
    private final int level = 10;

    public TestResultSummaryBgColor(TestResultSummaryBgColorListener[] listeners) {
        this.listeners = listeners;
    }

    public void testEnd(TestShadowResult result) {
        fireEvent(result.runCount(), result.failureCount(), result.errorCount());
    }

    void fireEvent(int runCount, int failureCount, int errorCount) {
        fireEvent(getColor(runCount, failureCount + errorCount));
    }

    private Color getColor(int runCount, int failureAndErrorCount) {
        if (runCount == 0) {
            return Color.RED;
        }

        return new Color(red(failureAndErrorCount), green(runCount, failureAndErrorCount), 0);
    }

    private int green(int runCount, int failureAndErrorCount) {
        if (failureAndErrorCount < 2) {
            return 255;
        }
        if (failureAndErrorCount > level) {
            return 0;
        }

        return 255 - 255 * failureAndErrorCount / runCount;
    }

    private int red(int failureAndErrorCount) {
        return failureAndErrorCount > 0 ? 255 : 0;
    }

    private void fireEvent(Color color) {
        for (TestResultSummaryBgColorListener listener : listeners) {
            listener.setBackground(color);
        }
    }

}
