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
package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.stubs.FailureTestShadow;
import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinet;
import junit.framework.TestCase;
import junit.framework.TestFailure;
import junit.framework.TestResult;

import java.io.StringWriter;

public class ShadowTreeModelAsLogTest extends TestCase {
    private ComparableTestShadow shadow;
    private SuccessTestShadow grandchildTest;
    private ShadowTreeModel model;
    private ShadowSummaryTreeNode root;

    private ShadowCabinet cabinet;
    private TestResult result;

    private StringWriter buffer;
    private FailureTestShadow failureTest;
    private ComparableTestShadow failureShadow;

    protected void setUp() throws Exception {
        root = new ShadowSummaryTreeNode();
        model = new ShadowTreeModel(root);
        grandchildTest = new SuccessTestShadow();
        shadow = new ComparableTestShadow(grandchildTest);

        cabinet = new ShadowCabinet();
        cabinet.addListener(model);
        result = new TestResult();
        result.addListener(model);

        buffer = new StringWriter();

        failureTest = new FailureTestShadow();
        failureShadow = new ComparableTestShadow(failureTest);
    }

    public void testShouldOutputEmptyAfterInit() throws Exception {
        model.output(root, new ConsoleViewBuffer(buffer));
        assertEquals("", buffer.toString());
    }

    public void testShouldBeEmptyIfTestIsSuccessful() throws Exception {
        ShadowClassTreeNode child = new ShadowClassTreeNode(shadow, root);
        ShadowMethodTreeNode grandchild = new ShadowMethodTreeNode(grandchildTest, child);
        cabinet.add(shadow);
        cabinet.action(result);

        model.output(root, new ConsoleViewBuffer(buffer));
        model.output(child, new ConsoleViewBuffer(buffer));
        model.output(grandchild, new ConsoleViewBuffer(buffer));
        assertEquals("", buffer.toString());
    }

    public void testShouldOutputStackTraceOfThrowableIfTestFailed() throws Exception {
        cabinet.add(failureShadow);
        cabinet.action(result);

        model.output(new ShadowMethodTreeNode(failureTest), new ConsoleViewBuffer(buffer));
        TestFailure failure = (TestFailure) result.failures().nextElement();
        assertEquals(failure.trace().trim(), buffer.toString().trim());
    }

    public void testShouldOutputStackTraceOfChildrensIfTestFailed() throws Exception {
        cabinet.add(failureShadow);
        cabinet.action(result);

        model.output(new ShadowClassTreeNode(failureShadow, root), new ConsoleViewBuffer(buffer));
        TestFailure failure = (TestFailure) result.failures().nextElement();
        assertEquals(failure.trace().trim(), buffer.toString().trim());

        buffer = new StringWriter();
        model.output(root, new ConsoleViewBuffer(buffer));
        assertEquals(failure.trace().trim(), buffer.toString().trim());
    }

    public void testShouldClearFailuresAddedByPreTestRunTime() throws Exception {
        cabinet.add(failureShadow);
        cabinet.action(result);

        cabinet.remove(failureShadow);
        cabinet.add(shadow);
        cabinet.action(result);

        model.output(root, new ConsoleViewBuffer(buffer));
        assertEquals("", buffer.toString().trim());
    }
}
