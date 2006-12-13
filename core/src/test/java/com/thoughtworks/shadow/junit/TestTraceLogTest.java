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
package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.Utils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class TestTraceLogTest extends TestCase {
    public void testParseSpecifyExceptionLog() throws Exception {
        String log = "ex(com.ex.Ex)" + Utils.LINE_SEP +
                "com.ex.Exception" + Utils.LINE_SEP +
                "\tat com.ex(Utils.java:36)" + Utils.LINE_SEP +
                "Caused by: com.ex.ex.Exception" + Utils.LINE_SEP +
                "\t... 21 more";

        assertIsAnErrorTest(new ErrorTestTraceLog(log).testCase());
    }

    private void assertIsAnErrorTest(Test test) {
        assertEquals(1, test.countTestCases());
        TestResult result = new TestResult();
        test.run(result);
        assertEquals(1, result.runCount());
        assertEquals(1, result.errorCount());
    }

    public void testParseOneLineTraceExceptionLog() throws Exception {
        String[] logs = new String[]{"ex(com.ex.Ex)" + Utils.LINE_SEP + "com.ex.Exception", "ex(com.ex.Ex)" + Utils.LINE_SEP + "com.ex.Exception: msg"};
        for (int i = 0; i < logs.length; i++) {
            assertIsAnErrorTest(new ErrorTestTraceLog(logs[i]).testCase());
        }
    }
}
