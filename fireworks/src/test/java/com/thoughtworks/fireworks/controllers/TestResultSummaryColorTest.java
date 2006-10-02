package com.thoughtworks.fireworks.controllers;

import junit.framework.TestCase;

import java.awt.*;

public class TestResultSummaryColorTest extends TestCase implements TestResultSummaryBgColorListener {
    private Color bgColor;
    private TestResultSummaryBgColor color;

    protected void setUp() throws Exception {
        color = new TestResultSummaryBgColor(new TestResultSummaryBgColorListener[]{this});
    }

    public void testShouldBeRedIfRunCountIsZero() throws Exception {
        color.testResult(0, 0, 0);
        assertEquals(Color.RED, bgColor);
    }

    public void testShouldChangeGreenToYellowWhenFirstTestFailed() throws Exception {
        color.testResult(1, 0, 0);
        color.testResult(2, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(2, 0, 1);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(255, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(1000, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
    }

    public void testShouldChangeYellowToRedWhenTestFailureCountRateIncreased() throws Exception {
        color.testResult(10, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(10, 1, 1);
        assertEquals(new Color(255, 255 - 255 * 2 / 10, 0), bgColor);
        color.testResult(10, 1, 3);
        assertEquals(new Color(255, 255 - 255 * 4 / 10, 0), bgColor);
        color.testResult(10, 1, 9);
        assertEquals(new Color(255, 255 - 255 * 10 / 10, 0), bgColor);


        color.testResult(20, 19, 0);
        assertEquals(new Color(255, 0, 0), bgColor);
        color.testResult(101, 100, 0);
        assertEquals(new Color(255, 0, 0), bgColor);
    }

    public void testShouldBeGreenIfAllTestsWereSuccessful() throws Exception {
        color.testResult(1, 0, 0);
        assertEquals(Color.GREEN, bgColor);
        color.testResult(255, 0, 0);
        assertEquals(Color.GREEN, bgColor);
        color.testResult(1000, 0, 0);
        assertEquals(Color.GREEN, bgColor);
    }

    public void setBackground(Color color) {
        this.bgColor = color;
    }
}
