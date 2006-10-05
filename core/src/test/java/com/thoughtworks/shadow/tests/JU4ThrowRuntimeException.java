package com.thoughtworks.shadow.tests;

import org.junit.Test;

public class JU4ThrowRuntimeException {
    public static final String FAILED_MESSAGE = RuntimeException.class.getName();

    @Test
    public void shouldThrowRuntimeException() {
        throw new RuntimeException();
    }
}
