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

public class TestResultStatus {
    private final int runCount;
    private final int failureCount;
    private final int errorCount;
    private final int ignoreCount;

    public TestResultStatus(int runCount, int failureCount, int errorCount, int ignoreCount) {
        this.runCount = runCount;
        this.failureCount = failureCount;
        this.errorCount = errorCount;
        this.ignoreCount = ignoreCount;
    }

    public TestResultStatus(TestShadowResult testResult) {
        this(testResult.runCount(), testResult.failureCount(), testResult.errorCount(), testResult.ignoreCount());
    }

    public boolean wasSuccessful() {
        return (runCount - ignoreCount) > 0 && errorCount == 0 && failureCount == 0;
    }

    public boolean isNoTest() {
        return runCount == 0;
    }

    public String summary() {
        return "Tests run: " + runCount + ", Failures: " + failureCount + ", Errors: " + errorCount + ", Ignored: " + ignoreCount;
    }
}
