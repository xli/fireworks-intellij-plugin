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
package com.thoughtworks.fireworks.adapters;

import com.thoughtworks.fireworks.core.TestCounterListener;
import com.thoughtworks.shadow.*;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class TestsRunningProgressIndicatorAdapter implements TestCounterListener, ShadowCabinetListener, RunListenerAdaptee, ShadowVisitor {
    private TestResultStatus status;
    private String testClassName;
    private String testMethodName;

    public void testResult(int runCount, int failureCount, int errorCount, int ignoreCount) {
        status = new TestResultStatus(runCount, failureCount, errorCount, ignoreCount);
        displaySummaryAsText();
    }

    public void afterAddTest(ComparableTestShadow test) {
    }

    public void afterRemoveTest(ComparableTestShadow test) {
    }

    public void endAction() {
        displaySummaryAsText();
    }

    public void startAction() {
        status = new TestResultStatus(0, 0, 0, 0);
        displaySummaryAsText();
    }

    public void addError(Test test, Throwable t) {
        ProgressIndicatorUtils.displayAsText2("error: " + t.getMessage());
    }

    public void addFailure(Test test, AssertionFailedError t) {
        ProgressIndicatorUtils.displayAsText2("failure: " + t.getMessage());
    }

    public void testIgnored(TestShadow testShadow) {
        ProgressIndicatorUtils.displayAsText2("ignored");
    }

    public void endTest(Test test) {
    }

    public void startTest(Test test) {
        ((Shadow) test).accept(this);
    }

    public void visitTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public void visitTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public void end() {
        ProgressIndicatorUtils.displayAsText2("Running: " + testClassName + "#" + testMethodName);
    }

    public Sunshine decorate(final Sunshine sunshine) {
        return new Sunshine() {
            public Test shine(String testClassName) {
                ProgressIndicatorUtils.displayAsText2(testClassName);
                return sunshine.shine(testClassName);
            }
        };
    }

    private void displaySummaryAsText() {
        ProgressIndicatorUtils.displayAsText(status.summary());
    }

}
