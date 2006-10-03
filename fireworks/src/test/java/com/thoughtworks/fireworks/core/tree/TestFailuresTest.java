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

import com.thoughtworks.fireworks.core.Utils;
import com.thoughtworks.fireworks.stubs.SuccessTestShadow;
import junit.framework.TestCase;

import java.io.StringWriter;

public class TestFailuresTest extends TestCase {
    private Throwable throwable;
    private TestFailures failures;
    private SuccessTestShadow shadow;
    private ShadowTreeNode parent;

    protected void setUp() throws Exception {
        throwable = new Throwable();
        failures = new TestFailures();
        shadow = new SuccessTestShadow();
        parent = new ShadowSummaryTreeNode();

        failures.addIntoBuffer(shadow, throwable);
        failures.commitBuffer(parent);
    }

    public void testOutputByNode() throws Exception {
        ShadowMethodTreeNode node = new ShadowMethodTreeNode(shadow, parent);
        StringWriter writer = new StringWriter();
        failures.output(node, new ConsoleViewBuffer(writer));

        assertEquals(Utils.toString(throwable).trim(), writer.toString().trim());
    }

    public void testOutputByParentNode() throws Exception {
        StringWriter writer = new StringWriter();
        failures.output(parent, new ConsoleViewBuffer(writer));

        assertEquals(Utils.toString(throwable).trim(), writer.toString().trim());
    }
}
