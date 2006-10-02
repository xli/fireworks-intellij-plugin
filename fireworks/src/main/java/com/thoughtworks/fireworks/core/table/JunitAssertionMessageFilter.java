package com.thoughtworks.fireworks.core.table;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;

public class JunitAssertionMessageFilter {

    private final static String[] MESSAGES = new String[]{
            AssertionFailedError.class.getName(),
            ComparisonFailure.class.getName()
    };

    public String doFilter(String message) {
        if (message == null) {
            return "";
        }

        for (int i = 0; i < MESSAGES.length; i++) {
            if (message.startsWith(MESSAGES[i]) && message.length() > MESSAGES[i].length()) {
                return message.substring(MESSAGES[i].length());
            }
        }
        return message;
    }
}
