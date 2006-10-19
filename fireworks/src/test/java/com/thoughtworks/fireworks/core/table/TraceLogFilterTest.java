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

import junit.framework.TestCase;

public class TraceLogFilterTest extends TestCase {
    private TraceLogFilter filter;

    protected void setUp() throws Exception {
        filter = new TraceLogFilter();
    }

    public void testShouldFilterOrgJUnitPackageTraceLog() throws Exception {
        assertTrue(filter.shouldFilter("\tat org.junit.xxxx"));
        assertFalse(filter.shouldFilter("\tat not.org.junit"));
    }

    public void testShouldFilterJunitFrameworkPackageTraceLog() throws Exception {
        assertTrue(filter.shouldFilter("\tat junit.framework.xxxx"));
        assertFalse(filter.shouldFilter("\tat not.junit.framework"));
    }

    public void testShouldFilterLogWhichIsNotStartWithTracePrefix() throws Exception {
        assertTrue(filter.shouldFilter("no atxxxx"));
        assertFalse(filter.shouldFilter("\tat xxx"));
    }

    public void testShouldFilterNull() throws Exception {
        assertTrue(filter.shouldFilter(null));
    }
}
