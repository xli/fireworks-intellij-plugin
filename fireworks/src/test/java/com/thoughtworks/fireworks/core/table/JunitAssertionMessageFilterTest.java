/*
 *    Copyright (c) 2006 LiXiao.
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
package com.thoughtworks.fireworks.core.table;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;
import junit.framework.TestCase;

public class JunitAssertionMessageFilterTest extends TestCase {
    private JunitAssertionMessageFilter filter;
    private String msg;

    protected void setUp() throws Exception {
        msg = ": message";
        filter = new JunitAssertionMessageFilter();
    }

    public void testShouldNotFilterMessageIfItIsEmptyStrAfterFilterMessage() throws Exception {
        assertEquals(AssertionFailedError.class.getName(), filter.doFilter(AssertionFailedError.class.getName()));
        assertEquals(ComparisonFailure.class.getName(), filter.doFilter(ComparisonFailure.class.getName()));
    }

    public void testShouldFilterJunitAssertionErrorClassName() throws Exception {
        assertEquals(msg, filter.doFilter(AssertionFailedError.class.getName() + msg));
    }

    public void testShouldFilterJunit() throws Exception {
        assertEquals(msg, filter.doFilter(ComparisonFailure.class.getName() + msg));
    }

    public void testShouldReturnEmptyStrWhenMessageIsNull() throws Exception {
        assertEquals("", filter.doFilter(null));
    }

    public void testDoFilter() throws Exception {
        assertEquals(msg, filter.doFilter(msg));
    }
}
