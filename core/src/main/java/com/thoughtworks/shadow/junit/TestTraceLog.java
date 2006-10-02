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

        msg = log.substring(testNameEndIndex, log.indexOf(Utils.LINE_SEP));

        String testNameInfo = log.substring(0, testNameEndIndex).trim();
        testClassName = testNameInfo.substring(testNameInfo.indexOf("(") + 1, testNameInfo.indexOf(")"));
        testMethodName = testNameInfo.substring(0, testNameInfo.indexOf("("));

        trace = log.substring(testNameEndIndex);
    }

    public Test testCase() {
        Protectable protectable = ProtectableFactory.protectable(testCaseWillThrow(msg, trace));
        return new BaseTestCase(testClassName, testMethodName, protectable);
    }

    protected abstract Throwable testCaseWillThrow(String msg, String trace);

}
