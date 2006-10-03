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

import java.text.MessageFormat;

public class Err extends TestCase {
    public static String getTestName() {
        return MessageFormat.format("testShouldThrowException({0})", new Object[]{Err.class.getName()});
    }

    public Err() {
        super("testShouldThrowException");
    }

    public void testShouldThrowException() throws Exception {
        throw new Exception("cause a junit error");
    }
}
