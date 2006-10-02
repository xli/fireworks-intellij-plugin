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
