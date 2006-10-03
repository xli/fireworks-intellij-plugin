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

public class TraceLogIterator {
    private final String[] array;
    private final boolean isFailures;
    private int index = 1;

    public TraceLogIterator(String[] array, boolean isFailures) {
        this.array = array;
        this.isFailures = isFailures;
    }

    public boolean hasNext() {
        return index < array.length;
    }

    public TestTraceLog next() {
        String next = array[index++];
        if (isFailures) {
            return new FailureTestTraceLog(next);
        }
        return new ErrorTestTraceLog(next);
    }
}
