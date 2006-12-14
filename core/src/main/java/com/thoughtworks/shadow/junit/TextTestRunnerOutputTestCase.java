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

import java.text.NumberFormat;
import java.text.ParseException;

public class TextTestRunnerOutputTestCase implements Test {

    private final long time;
    private final LogTestSuite details;

    public TextTestRunnerOutputTestCase(JUnitAdapter testClass, String output) {
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
        try {
            return 1000 * NumberFormat.getInstance().parse(runTime).longValue();
        } catch (ParseException e) {
            return 0;
        }
    }
}
