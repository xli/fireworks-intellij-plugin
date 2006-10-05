package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.TestResultAssert;
import com.thoughtworks.shadow.TestShadowResult;
import com.thoughtworks.shadow.tests.JU4Ignore;
import com.thoughtworks.shadow.tests.JU4Success;
import com.thoughtworks.shadow.tests.Success;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.textui.TestRunner;
import org.junit.runner.JUnitCore;

public class JUnitAdapterTest extends TestCase {
    public void testShouldUseTextTestRunnerAsTestCaseRunner() throws Exception {
        JUnitAdapter adapter = new JUnitAdapter(Success.class);
        assertEquals(TestRunner.class, adapter.runnerClass());
    }

    public void testShouldUseJUnitCoreAsJUnit4TestRunner() throws Exception {
        JUnitAdapter adapter = new JUnitAdapter(JU4Success.class);
        assertEquals(JUnitCore.class, adapter.runnerClass());
    }

    public void testShouldCreateAllJUnit4TestMethodsAsTests() throws Exception {
        JUnitAdapter adapter = new JUnitAdapter(JU4Success.class);
        Test[] tests = adapter.createTests();
        assertEquals(1, tests.length);
        TestResultAssert.verifySuccess(tests[0]);
    }

    public void testCreateIgnoredJUnit4Test() throws Exception {
        JUnitAdapter adapter = new JUnitAdapter(JU4Ignore.class);
        Test[] tests = adapter.createTests();
        TestShadowResult result = new TestShadowResult();
        tests[0].run(result);

        assertEquals(0, result.runCount());
        assertEquals(0, result.failureCount());
        assertEquals(0, result.errorCount());
        assertEquals(1, result.ignoreCount());
    }

    public void testShouldGreateAllJUnit3testMethodsAsTests() throws Exception {
        JUnitAdapter adapter = new JUnitAdapter(Success.class);
        Test[] tests = adapter.createTests();
        assertEquals(1, tests.length);
        TestResultAssert.verifySuccess(tests[0]);
    }
}
