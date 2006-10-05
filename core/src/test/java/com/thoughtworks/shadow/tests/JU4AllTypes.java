package com.thoughtworks.shadow.tests;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class JU4AllTypes {
    @Test
    @Ignore
    public void shouldBeIgnored() {
    }

    @Test
    public void shouldBeFailed() {
        Assert.fail();
    }

    @Test
    public void aTest() {

    }
}
