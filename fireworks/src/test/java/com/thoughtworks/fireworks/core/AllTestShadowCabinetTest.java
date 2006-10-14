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
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.TestStateListener;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class AllTestShadowCabinetTest extends TestCase {

    private AllTestShadowCabinet allTestShadowCabinet;
    private TestResult result;
    private Sunshine sunshine;
    private IntellijShadowCabinet icabinet;
    private Mock stateListener;
    private Mock config;

    protected void setUp() throws Exception {
        int maxSize = 1;
        result = new TestResult();
        sunshine = TestUtils.sunshine();
        stateListener = Turtle.mock(TestStateListener.class);
        config = Turtle.mock(FireworksConfig.class);
        config.ifCall("maxSize").willReturn(new Integer(maxSize));

        icabinet = new IntellijShadowCabinet(new ShadowCabinetListener[0], new SimpleCompilerManagerAdapter(), (TestStateListener) stateListener.mockTarget());
        icabinet.setMaxSize(maxSize);

        allTestShadowCabinet = new AllTestShadowCabinet(icabinet, (FireworksConfig) config.mockTarget());
    }

    public void testTestShadowShouldRemoveSelfFromCabinetAfterRunSuccessfully() throws Exception {
        allTestShadowCabinet.init();
        allTestShadowCabinet.add(sunshine, Success.class.getName());
        allTestShadowCabinet.action(result);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
        assertEquals(0, icabinet.size());
    }

    public void testShouldIncreaseMaxSizeIfTheNumOfTestsAfterAddIsMoreThanCabinetMaxSize() throws Exception {
        allTestShadowCabinet.add(sunshine, Success.class.getName());
        allTestShadowCabinet.add(sunshine, Failure.class.getName());
        config.assertDid("setMaxSize").with(new Integer(2));
    }

    public void testShouldSetMaxSizeAsSizeOfFailedTestsAndTestsAddedBefore() throws Exception {
        icabinet.addTestShadow(sunshine, Success.class.getName());
        allTestShadowCabinet.init();
        allTestShadowCabinet.add(sunshine, Failure.class.getName());
        allTestShadowCabinet.action(result);
        config.assertDid("setMaxSize").with(new Integer(1 + 1));
    }

    public void testMaxSizeShouldNotLessThanSizeBeforeAction() throws Exception {
        allTestShadowCabinet.init();
        allTestShadowCabinet.add(sunshine, Success.class.getName());
        allTestShadowCabinet.action(result);
        config.assertDid("setMaxSize").with(new Integer(1));
    }

    public void testMaxSizeShouldNotLargerThanSizeBeforeActionIfThereIsNoTestAddedByCabinetFailed() throws Exception {
        icabinet.addTestShadow(sunshine, Failure.class.getName());
        allTestShadowCabinet.init();
        allTestShadowCabinet.add(sunshine, Success.class.getName());
        allTestShadowCabinet.action(result);
        config.assertDid("setMaxSize").with(new Integer(2));
        config.assertDid("setMaxSize").with(new Integer(1));
    }

    public void testTestAddedAfterActionShouldNotRemoveSelfFromCabinet() throws Exception {
        allTestShadowCabinet.init();
        allTestShadowCabinet.add(sunshine, Success.class.getName());
        allTestShadowCabinet.action(result);

        icabinet.addTestShadow(sunshine, Success.class.getName());
        assertEquals(1, icabinet.size());
        icabinet.action(new TestResult());
        assertEquals(1, icabinet.size());
    }
}
