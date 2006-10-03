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
package com.thoughtworks.fireworks.ui.toolwindow;

import com.thoughtworks.fireworks.core.TestCounterListener;
import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.shadow.TestResultStatus;

import javax.swing.*;

public class TestResultSummary extends JLabel implements TestCounterListener {

    public TestResultSummary() {
        super(new TestResultStatus(0, 0, 0).summary());
        setBackground(Utils.TRANSPARENT_WHITE);
    }

    public void testResult(int runCount, int failureCount, int errorCount) {
        setText(new TestResultStatus(runCount, failureCount, errorCount).summary());
    }
}
