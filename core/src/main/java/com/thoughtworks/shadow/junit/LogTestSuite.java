package com.thoughtworks.shadow.junit;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.lang.reflect.Method;
import java.util.*;

public class LogTestSuite implements Test {
    private Map tests;

    public LogTestSuite(Class testClass) {
        tests = new HashMap();
        String[] testMethods = getTestMethods(testClass);
        for (int i = 0; i < testMethods.length; i++) {
            add(new SuccessfulTestCase(testClass.getName(), testMethods[i]));
        }
    }

    public int countTestCases() {
        return testSuite().countTestCases();
    }

    public void run(TestResult result) {
        testSuite().run(result);
    }

    public void add(TraceLogIterator iterator) {
        while (iterator != null && iterator.hasNext()) {
            add(iterator.next().testCase());
        }
    }

    private Test testSuite() {
        TestSuite suite = new TestSuite();
        for (Iterator iter = tests.values().iterator(); iter.hasNext();) {
            suite.addTest((Test) iter.next());
        }
        return suite;
    }

    private void add(Test test) {
        tests.put(test, test);
    }

    private String[] getTestMethods(Class clazz) {
        Method[] methods = clazz.getMethods();
        Set testMethods = new HashSet();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            if (methodName.startsWith("test")) {
                testMethods.add(methodName);
            }
        }
        return (String[]) testMethods.toArray(new String[testMethods.size()]);
    }
}
