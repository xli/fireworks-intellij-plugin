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

import com.thoughtworks.shadow.TestShadowResult;
import junit.framework.TestCase;

import java.awt.*;

public class TestResultSummaryByColorTest extends TestCase implements TestResultSummaryBgColorListener {
    private Color bgColor;
    private TestResultSummaryBgColor color;

    protected void setUp() throws Exception {
        color = new TestResultSummaryBgColor(new TestResultSummaryBgColorListener[]{this});
    }

    public void testShouldBeRedIfRunCountIsZero() throws Exception {
        color.testEnd(new TestShadowResult());
        assertEquals(Color.RED, bgColor);

        color.fireEvent(0, 0, 0);
        assertEquals(Color.RED, bgColor);
    }

    public void testShouldChangeGreenToYellowWhenFirstTestFailed() throws Exception {
        color.fireEvent(1, 0, 0);
        color.fireEvent(2, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
        color.fireEvent(2, 0, 1);
        assertEquals(Color.YELLOW, bgColor);
        color.fireEvent(255, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
        color.fireEvent(1000, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
    }

    public void testShouldChangeYellowToRedWhenTestFailureCountRateIncreased() throws Exception {
        color.fireEvent(10, 1, 0);
        assertEquals(Color.YELLOW, bgColor);
        color.fireEvent(10, 1, 1);
        assertEquals(new Color(255, 255 - 255 * 2 / 10, 0), bgColor);
        color.fireEvent(10, 1, 3);
        assertEquals(new Color(255, 255 - 255 * 4 / 10, 0), bgColor);
        color.fireEvent(10, 1, 9);
        assertEquals(new Color(255, 255 - 255 * 10 / 10, 0), bgColor);


        color.fireEvent(20, 19, 0);
        assertEquals(new Color(255, 0, 0), bgColor);
        color.fireEvent(101, 100, 0);
        assertEquals(new Color(255, 0, 0), bgColor);
    }

    public void testShouldBeGreenIfAllTestsWereSuccessful() throws Exception {
        color.fireEvent(1, 0, 0);
        assertEquals(Color.GREEN, bgColor);
        color.fireEvent(255, 0, 0);
        assertEquals(Color.GREEN, bgColor);
        color.fireEvent(1000, 0, 0);
        assertEquals(Color.GREEN, bgColor);
    }

    public void setBackground(Color color) {
        this.bgColor = color;
    }
}
