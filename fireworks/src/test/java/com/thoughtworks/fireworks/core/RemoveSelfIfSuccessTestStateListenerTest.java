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

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.shadow.TestStateListener;
import junit.framework.TestCase;

public class RemoveSelfIfSuccessTestStateListenerTest extends TestCase {
    private TestStateListener listener;
    private ComparableTestShadow shadow;
    private ShadowCabinet cabinet;

    protected void setUp() throws Exception {
        listener = new RemoveSelfIfSuccessTestStateListener();
        shadow = new ComparableTestShadow(this);
        cabinet = new ShadowCabinet();
        cabinet.add(shadow);
    }

    public void testShouldRemoveSelfFromCabinetIfTestIsSuccessfully() throws Exception {
        listener.endTestShadow(shadow, true);
        assertEquals(0, cabinet.size());
    }

    public void testShouldNotRemoveSelfIfTestIsFailure() throws Exception {
        listener.endTestShadow(shadow, false);
        assertEquals(1, cabinet.size());
    }
}
