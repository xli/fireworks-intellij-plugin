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
