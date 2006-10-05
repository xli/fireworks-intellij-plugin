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
package com.thoughtworks.fireworks.core.table;

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.shadow.junit.BaseTestCase;
import com.thoughtworks.shadow.junit.IgnoredTestCase;
import com.thoughtworks.shadow.junit.SuccessfulTestCase;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.Protectable;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class ShadowTableModelAsListenerTest extends TestCase {
    private ShadowTableModel model;
    private TestShadow failureShadow;
    private TestResult result;
    private static final String TEST_CLASS_NAME = "test class name";
    private static final String TEST_METHOD_NAME = "test method name";
    private static final String FAILURE_MESSAGE = "must fail";
    private Mock logViewer;

    protected void setUp() throws Exception {
        logViewer = Turtle.mock(TraceLogViewer.class);
        model = new ShadowTableModel((TraceLogViewer) logViewer.mockTarget());
        failureShadow = new BaseTestCase(TEST_CLASS_NAME, TEST_METHOD_NAME, new Protectable() {
            public void protect() throws Throwable {
                fail(FAILURE_MESSAGE);
            }
        });
        result = new TestResult();
        result.addListener(model);
    }

    public void testShouldCountFailureTestShadow() throws Exception {
        failureShadow.run(result);

        assertEquals(1, model.getRowCount());
        assertEquals(TEST_METHOD_NAME, model.getValueAt(0, ShadowTableModel.TEST_METHOD).toString());
        assertEquals(TEST_CLASS_NAME, model.getValueAt(0, ShadowTableModel.TEST_CLASS).toString());
        assertEquals(FAILURE_MESSAGE, model.getValueAt(0, ShadowTableModel.MESSAGE).toString());
        String expected1 = "(ShadowTableModelAsListenerTest.java:";
        String expected2 = ")com.thoughtworks.fireworks.core.table.ShadowTableModelAsListenerTest$1.protect";
        String resultLogMsg = model.getValueAt(0, ShadowTableModel.TRACE_LOG).toString();
        assertTrue(resultLogMsg.contains(expected1));
        assertTrue(resultLogMsg.contains(expected2));
    }

    public void testShouldThrowIndexOutOfBoundsExceptionWhenGetValueAtAnInvalidColumn() throws Exception {
        failureShadow.run(result);
        try {
            model.getValueAt(0, 5);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e.getMessage());
        }
    }

    public void testShouldNotCountSuccessfulTestShadow() throws Exception {
        TestShadow successful = new SuccessfulTestCase();
        successful.run(result);
        assertEquals(0, model.getRowCount());
    }

    public void testShouldNotCountIgnoredTestShadow() throws Exception {
        TestShadow successful = new IgnoredTestCase("", "");
        successful.run(result);
        assertEquals(0, model.getRowCount());
    }

    public void testShouldCanBeReused() throws Exception {
        ShadowCabinet cabinet = new ShadowCabinet();
        cabinet.addListener(model);
        ComparableTestShadow shadow = new ComparableTestShadow(failureShadow);
        cabinet.add(shadow);

        cabinet.action(result);
        assertEquals(1, model.getRowCount());

        cabinet.remove(shadow);
        cabinet.add(new ComparableTestShadow(new SuccessfulTestCase()));
        cabinet.action(result);
        assertEquals(0, model.getRowCount());
    }

}
