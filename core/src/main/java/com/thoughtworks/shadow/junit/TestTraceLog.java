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
import junit.framework.Protectable;
import junit.framework.Test;

public abstract class TestTraceLog {
    private String msg;
    private String testClassName;
    private String testMethodName;
    private String trace;

    public TestTraceLog(String log) {
        int testNameEndIndex = log.indexOf(")", 2) + 1;

        String testNameInfo = log.substring(0, testNameEndIndex).trim();
        testClassName = testNameInfo.substring(testNameInfo.indexOf("(") + 1, testNameInfo.indexOf(")"));
        testMethodName = testNameInfo.substring(0, testNameInfo.indexOf("("));

        trace = log.substring(testNameEndIndex).trim();

        msg = getMessage(log, testNameEndIndex);
    }

    private String getMessage(String log, int testNameEndIndex) {
        int firstLineEndIndex = log.indexOf(Utils.LINE_SEP);
        if (testNameEndIndex < firstLineEndIndex) {
            return log.substring(testNameEndIndex, firstLineEndIndex);
        } else {
            return getMessageFromTrace();
        }
    }

    private String getMessageFromTrace() {
        String splitStr = ": ";
        int index = trace.indexOf(splitStr);
        if (index < 0) {
            return trace.substring(0, trace.indexOf(Utils.LINE_SEP));
        }
        return trace.substring(index + splitStr.length(), trace.indexOf(Utils.LINE_SEP));
    }

    public Test testCase() {
        Protectable protectable = ProtectableFactory.protectable(testCaseWillThrow(msg, trace));
        return new BaseTestCase(testClassName, testMethodName, protectable);
    }

    protected abstract Throwable testCaseWillThrow(String msg, String trace);

}
