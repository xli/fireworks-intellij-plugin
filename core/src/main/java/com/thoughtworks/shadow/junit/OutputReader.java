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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class OutputReader {
    private static final String TRACE_LOG_SPLIT_REGEX = "(^|" + Utils.LINE_SEP + ")\\d+\\) ";

    private final LogTestSuite details;

    private final BufferedReader reader;

    public OutputReader(JUnitAdapter testClass, String output) {
        details = new LogTestSuite(testClass);
        reader = new BufferedReader(new StringReader(output));
    }

    public String readTime() {
        return filterUntillGetTime();
    }

    public LogTestSuite readTestDetailLog() {
        readLine();
        return details;
    }

    private OutputLine readLine() {
        OutputLine line = readLineFromReader();

        if (line.isErrorTraceLog() || line.isFailureTraceLog()) {
            details.add(new TraceLogIterator(readTraceLog(), line.isFailureTraceLog()));
            return new OutputLine("");
        }

        return line;
    }

    private String[] readTraceLog() {
        StringBuffer buffer = new StringBuffer();
        while (true) {
            OutputLine line = readLine();
            if (line.isEmptyOrEnd()) {
                break;
            }
            line.appendTo(buffer);
        }

        String[] traceLogs = buffer.toString().split(TRACE_LOG_SPLIT_REGEX);

        return traceLogs;
    }

    private String filterUntillGetTime() {
        while (true) {
            OutputLine line = readLineFromReader();
            if (line.isEnd()) {
                throw new TestRunnerError("the output string does not contain test time info line.");
            }
            if (line.isTimeInfo()) {
                return line.distillTimeInfo();
            }
        }
    }

    private OutputLine readLineFromReader() {
        try {
            return new OutputLine(reader.readLine());
        } catch (IOException e) {
            return new OutputLine("");
        }
    }
}
