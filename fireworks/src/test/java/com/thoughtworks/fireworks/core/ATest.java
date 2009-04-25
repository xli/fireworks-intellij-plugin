package com.thoughtworks.fireworks.core;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: xli
 * Date: Mar 28, 2008
 * Time: 3:33:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ATest extends TestCase {

    public void testA() {
        assertTrue("XUnitTest".matches("(.*UnitTest)|((?!Abstract).*IntegrationTest)"));
        assertTrue("IntegrationTest".matches("(.*UnitTest)|((?!Abstract).*IntegrationTest)"));
        assertTrue("XIntegrationTest".matches("(.*UnitTest)|((?!Abstract).*IntegrationTest)"));
        assertFalse("AbstractXxIntegrationTest".matches("(.*UnitTest)|((?!Abstract).*IntegrationTest)"));
        assertFalse("AbstractIntegrationTest".matches("(.*UnitTest)|((?!Abstract).*IntegrationTest)"));
    }
}
