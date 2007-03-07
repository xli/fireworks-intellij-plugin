/*
 *    Copyright (c) 2006 LiXiao
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
package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.turtlemock.Mock;
import com.thoughtworks.turtlemock.Turtle;
import junit.framework.TestCase;

public class ErrorTestCaseTest extends TestCase {
    public void testShouldDoNothingWhenAcceptAVisitor() throws Exception {
        Mock visitor = Turtle.mock(ShadowVisitor.class);
        TestShadow shadow = new ErrorTestCase("name", null);
        shadow.accept((ShadowVisitor) visitor.mockTarget());
        visitor.assertDid("visitTestClassName").with("name");
        visitor.assertDid("visitTestMethodName").with("");
        visitor.assertDid("end");
    }
}
