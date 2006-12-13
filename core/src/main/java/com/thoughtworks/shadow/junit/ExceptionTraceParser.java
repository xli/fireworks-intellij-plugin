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

public class ExceptionTraceParser {
    private static final String TRACE_MSG_SPLIT_STR = ": ";

    private String trace;

    public ExceptionTraceParser(String trace) {
        this.trace = trace;
    }

    public String getMessageFromTrace() {
        if (isOneLineTrace()) {
            return trace;
        }
        if (traceHasMessage()) {
            return parseMessageFromTrace();
        }
        return firstLine(trace);
    }

    private String parseMessageFromTrace() {
        return trace.substring(traceMsgSplitStrIndex() + TRACE_MSG_SPLIT_STR.length(), firstLineSepOfTraceIndex());
    }

    private boolean traceHasMessage() {
        return traceMsgSplitStrIndex() >= 0;
    }

    private int traceMsgSplitStrIndex() {
        return firstLine(trace).indexOf(TRACE_MSG_SPLIT_STR);
    }

    private boolean isOneLineTrace() {
        return firstLineSepOfTraceIndex() < 0;
    }

    private int firstLineSepOfTraceIndex() {
        return trace.indexOf(Utils.LINE_SEP);
    }

    private String firstLine(String trace) {
        return trace.substring(0, firstLineSepOfTraceIndex());
    }
}
