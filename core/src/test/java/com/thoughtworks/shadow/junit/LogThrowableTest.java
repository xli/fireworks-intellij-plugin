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
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class LogThrowableTest extends TestCase {
    private final String exceptionMessage = "Throwable";
    private final String exceptionTrace = exceptionMessage + Utils.LINE_SEP + "\tat shadow.Failure.testShouldFailed(Failure.java:7)";
    private LogThrowable error;

    protected void setUp() throws Exception {
        error = new LogThrowable(exceptionMessage, exceptionTrace);
    }

    public void testPrintStackTraceWithPrintStream() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        error.printStackTrace(new PrintStream(out, true));
        assertEquals(exceptionTrace + Utils.LINE_SEP, out.toString());
    }

    public void testPrintStackTraceWithPrintWriter() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        error.printStackTrace(new PrintWriter(out, true));
        assertEquals(exceptionTrace + Utils.LINE_SEP, out.toString());
    }

}
