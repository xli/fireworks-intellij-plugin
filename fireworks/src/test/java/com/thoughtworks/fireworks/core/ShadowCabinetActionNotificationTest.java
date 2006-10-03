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

import com.thoughtworks.fireworks.stubs.Success;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class ShadowCabinetActionNotificationTest extends TestCase {
    private TestResult result;
    private CabinetActionNotification actionNotification;

    protected void setUp() throws Exception {
        ShadowCabinet cabinet = new ShadowCabinet();
        cabinet.add(new ComparableTestShadow(new Success()));
        result = new TestResult();
        actionNotification = new CabinetActionNotification(cabinet, result);
    }

    public void testShouldActionWhenNotificationIsHappy() throws Exception {
        actionNotification.finished(false, 0, 0);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
    }

    public void testShouldNotActionIfAbort() throws Exception {
        actionNotification.finished(true, 0, 0);
        assertEquals(0, result.runCount());
    }

    public void testShouldNotActionIfHasErrors() throws Exception {
        actionNotification.finished(false, 1, 0);
        assertEquals(0, result.runCount());
    }

    public void testShouldActionEvenIfThereAreWarnings() throws Exception {
        actionNotification.finished(false, 0, 5);
        TestUtils.assertTestResultRunOnceSuccessfully(result);
    }
}
