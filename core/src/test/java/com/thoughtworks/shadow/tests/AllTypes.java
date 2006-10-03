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
package com.thoughtworks.shadow.tests;

import junit.framework.TestCase;

public class AllTypes extends TestCase {

    public static String[] getTestMethodNames() {
        return new String[]{"testSuccess",
                "testShouldFailed",
                "testShouldThrowException"};
    }

    public void testShouldThrowException() throws Exception {
        throw new Exception("cause a junit error");
    }

    public void testShouldFailed() throws Exception {
        fail("cause a junit failure");
    }

    public void testSuccess() {
    }

}
