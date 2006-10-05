package com.thoughtworks.shadow.tests;

import org.junit.Assert;
import org.junit.Test;

public class JU4Failure {
    public static final String FAILED_MESSAGE = "failed message";

    @Test
    public void shouldBeFailed() {
        Assert.fail(FAILED_MESSAGE);
    }
}
