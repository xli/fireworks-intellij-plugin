package com.thoughtworks.shadow.junit;

import junit.framework.Test;
import junit.framework.TestResult;

public class TextTestRunnerOutputTestCase implements Test {

    private final long time;
    private final LogTestSuite details;

    public TextTestRunnerOutputTestCase(Class testClass, String output) {
        OutputReader reader = new OutputReader(testClass, output);
        time = elapsedTimeAsLong(reader.readTime());
        details = reader.readTestDetailLog();
    }

    public int countTestCases() {
        return details.countTestCases();
    }

    public void run(TestResult result) {
        details.run(result);
    }

    public long getTime() {
        return time;
    }

    public long elapsedTimeAsLong(String runTime) {
        return (long) (1000 * Double.parseDouble(runTime));
    }
}
