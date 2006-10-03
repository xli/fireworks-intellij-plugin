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

public class OutputLine {
    private static final String TIME_INFO_REGEX = "^Time: [\\d]+(\\.[\\d]+)?$";
    private static final String ERROR_TRACE_LOG_REGEX = "^There (was|were) \\d+ errors?:";
    private static final String FAILURE_TRACE_LOG_REGEX = "^There (was|were) \\d+ failures?:";
    private static final String DISTILL_NUM_SPLIT_REGEX = "[^\\d]+";

    private final String line;

    public OutputLine(String line) {
        this.line = line;
    }

    public void appendTo(StringBuffer buffer) {
        buffer.append(line + Utils.LINE_SEP);
    }

    public String distillNum() {
        return line.split(DISTILL_NUM_SPLIT_REGEX)[1];
    }

    public boolean isEnd() {
        return line == null;
    }

    public boolean isTimeInfo() {
        return line.matches(TIME_INFO_REGEX);
    }

    public boolean isErrorTraceLog() {
        return line.matches(ERROR_TRACE_LOG_REGEX);
    }

    public boolean isFailureTraceLog() {
        return line.matches(FAILURE_TRACE_LOG_REGEX);
    }

    public boolean isEmptyOrEnd() {
        return Utils.isEmpty(line);
    }

    public String toString() {
        return "OutputLine: " + line;
    }
}
