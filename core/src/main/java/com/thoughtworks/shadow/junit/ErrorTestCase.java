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
import junit.framework.Protectable;

public class ErrorTestCase extends BaseTestCase {
    private String testName;

    public ErrorTestCase(String testName, Protectable protectable) {
        super(testName, null, protectable);
        this.testName = testName;
    }

    public void accept(ShadowVisitor visitor) {
        visitor.visitTestMethodName("");
        visitor.visitTestClassName(testName);
        visitor.end();
    }
}
