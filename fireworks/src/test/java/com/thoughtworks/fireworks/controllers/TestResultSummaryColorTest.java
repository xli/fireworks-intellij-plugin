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
package com.thoughtworks.fireworks.controllers;

import junit.framework.TestCase;

import java.awt.*;

public class TestResultSummaryColorTest extends TestCase implements TestResultSummaryBgColorListener {
    private Color bgColor;
    private TestResultSummaryBgColor color;
    private int ignoreCount;

    protected void setUp() throws Exception {
        color = new TestResultSummaryBgColor(new TestResultSummaryBgColorListener[]{this});
        ignoreCount = 0;
    }

    public void testShouldBeRedIfRunCountIsZero() throws Exception {
        color.testResult(0, 0, 0, ignoreCount);
        assertEquals(Color.RED, bgColor);
    }

    public void testShouldChangeGreenToYellowWhenFirstTestFailed() throws Exception {
        color.testResult(1, 0, 0, ignoreCount);
        color.testResult(2, 1, 0, ignoreCount);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(2, 0, 1, ignoreCount);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(255, 1, 0, ignoreCount);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(1000, 1, 0, ignoreCount);
        assertEquals(Color.YELLOW, bgColor);
    }

    public void testShouldChangeYellowToRedWhenTestFailureCountRateIncreased() throws Exception {
        color.testResult(10, 1, 0, ignoreCount);
        assertEquals(Color.YELLOW, bgColor);
        color.testResult(10, 1, 1, ignoreCount);
        assertEquals(new Color(255, 255 - 255 * 2 / 10, 0), bgColor);
        color.testResult(10, 1, 3, ignoreCount);
        assertEquals(new Color(255, 255 - 255 * 4 / 10, 0), bgColor);
        color.testResult(10, 1, 9, ignoreCount);
        assertEquals(new Color(255, 255 - 255 * 10 / 10, 0), bgColor);


        color.testResult(20, 19, 0, ignoreCount);
        assertEquals(new Color(255, 0, 0), bgColor);
        color.testResult(101, 100, 0, ignoreCount);
        assertEquals(new Color(255, 0, 0), bgColor);
    }

    public void testShouldBeGreenIfAllTestsWereSuccessful() throws Exception {
        color.testResult(1, 0, 0, ignoreCount);
        assertEquals(Color.GREEN, bgColor);
        color.testResult(255, 0, 0, ignoreCount);
        assertEquals(Color.GREEN, bgColor);
        color.testResult(1000, 0, 0, ignoreCount);
        assertEquals(Color.GREEN, bgColor);
    }

    public void setBackground(Color color) {
        this.bgColor = color;
    }
}
