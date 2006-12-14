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

import junit.framework.TestCase;

import java.text.NumberFormat;

public class OutputLineTest extends TestCase {
    public void testToString() throws Exception {
        OutputLine line = new OutputLine("line");
        assertEquals("OutputLine: line", line.toString());
    }
    
    public void testIsTimeInfo() throws Exception {
        String t = NumberFormat.getInstance().format((double) 1000000 / 1000);
        long a = NumberFormat.getInstance().parse(t).longValue();
        System.out.println(a);
        OutputLine[] timeLines = new OutputLine[]{new OutputLine("Time: 1"),
                new OutputLine("Time: 2,031"), new OutputLine("Time: 123,123,031"), new OutputLine("Time: 123,031.12")};
        for (int i = 0; i < timeLines.length; i++) {
            OutputLine timeLine = timeLines[i];
            assertTrue(timeLine.isTimeInfo());
        }

        String[] expected = new String[]{"1", "2,031", "123,123,031", "123,031.12"};
        for (int i = 0; i < timeLines.length; i++) {
            OutputLine timeLine = timeLines[i];
            assertEquals(expected[i], timeLine.distillTimeInfo());
        }

    }
}
