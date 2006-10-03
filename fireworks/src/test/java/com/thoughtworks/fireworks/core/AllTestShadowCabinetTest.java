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
package com.thoughtworks.fireworks.core;

import com.thoughtworks.fireworks.stubs.Failure;
import com.thoughtworks.fireworks.stubs.Success;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.TestStateListener;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class AllTestShadowCabinetTest extends TestCase implements TestStateListener, FireworksConfig {

    private AllTestShadowCabinet cabinet;
    private TestResult result;
    private Sunshine sunshine;
    private IntellijShadowCabinet icabinet;
    private int maxSize;

    protected void setUp() throws Exception {
        result = new TestResult();
        sunshine = TestUtils.sunshine();
        ShadowCabinetListener[] listeners = new ShadowCabinetListener[0];
        CompilerManagerAdaptee compilerManager = new SimpleCompilerManagerAdapter();
        icabinet = new IntellijShadowCabinet(listeners, compilerManager, this);
        cabinet = new AllTestShadowCabinet(icabinet, this);
    }

    public void testTestShadowShouldRemoveSelfFromCabinetAfterRunSuccessfully() throws Exception {
        setMaxSize(1);
        cabinet.init();
        cabinet.addTestShadow(sunshine, Success.class.getName());
        cabinet.action(result);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
        assertEquals(0, icabinet.size());
    }

    public void testShouldIncreaseMaxSizeIfTheNumOfTestsAfterAddIsMoreThanCabinetMaxSize() throws Exception {
        setMaxSize(0);
        cabinet.addTestShadow(sunshine, Success.class.getName());
        assertEquals(1, maxSize);
    }

    public void testShouldSetMaxSizeAsSizeOfFailedTestsAndTestsAddedBefore() throws Exception {
        setMaxSize(1);
        icabinet.addTestShadow(sunshine, Success.class.getName());
        cabinet.init();
        cabinet.addTestShadow(sunshine, Failure.class.getName());
        cabinet.action(result);
        assertEquals(1 + 1, maxSize);
    }

    public void testMaxSizeShouldNotLessThanSizeBeforeAction() throws Exception {
        setMaxSize(1);
        cabinet.init();
        cabinet.addTestShadow(sunshine, Success.class.getName());
        cabinet.action(result);
        assertEquals(1, maxSize);
    }

    public void testMaxSizeShouldNotLargerThanSizeBeforeActionIfThereIsNoTestAddedByCabinetFailed() throws Exception {
        setMaxSize(1);
        icabinet.addTestShadow(sunshine, Failure.class.getName());
        cabinet.init();
        cabinet.addTestShadow(sunshine, Success.class.getName());
        cabinet.action(result);
        assertEquals(1, maxSize);
    }

    public void testTestAddedAfterActionShouldNotRemoveSelfFromCabinet() throws Exception {
        setMaxSize(1);
        cabinet.init();
        cabinet.addTestShadow(sunshine, Success.class.getName());
        cabinet.action(result);

        icabinet.addTestShadow(sunshine, Success.class.getName());
        assertEquals(1, icabinet.size());
        icabinet.action(new TestResult());
        assertEquals(1, icabinet.size());
    }

    public void endTestShadow(ComparableTestShadow shadow, boolean wasSuccessful, int times) {
    }

    public String maxMemory() {
        return null;
    }

    public String expectedTestCaseNameRegex() {
        return null;
    }

    public int maxSize() {
        return maxSize;
    }

    public int autoRunTestsDelayTime() {
        return 0;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        icabinet.setMaxSize(maxSize);
    }
}
