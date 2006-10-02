package com.thoughtworks.shadow.tests;

import junit.framework.TestCase;

import java.text.MessageFormat;

public class Success extends TestCase {
    public static String getTestName() {
        return MessageFormat.format("testSuccess({0})", new Object[]{Success.class.getName()});
    }

    public Success() {
        super("testSuccess");
    }

    public void testSuccess() {
    }

}
