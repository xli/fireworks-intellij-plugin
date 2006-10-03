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

import junit.framework.AssertionFailedError;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AssertionFailedLogError extends AssertionFailedError {
    private String trace;

    public AssertionFailedLogError(String msg, String trace) {
        super(msg);
        this.trace = trace;
    }

    public void printStackTrace(PrintWriter s) {
        s.println(trace);
    }

    public void printStackTrace(PrintStream s) {
        s.println(trace);
    }

}
