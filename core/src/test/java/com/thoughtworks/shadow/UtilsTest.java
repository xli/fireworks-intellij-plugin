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
package com.thoughtworks.shadow;

import junit.framework.TestCase;

import java.io.File;
import java.net.URL;

public class UtilsTest extends TestCase {
    public void testLoadClassFromClasspaths() throws Exception {
        URL[] classpaths = new URL[]{new File(TestUtils.baseDir() + "src/test/class").toURL()};
        Class outClasspathClass = Utils.load(classpaths, "ClassOutOfClasspath");
        assertNotNull(outClasspathClass);
    }

    public void testShouldThrowNullpointerException() throws Exception {
        try {
            Utils.load(new URL[0], null);
            fail();
        } catch (NullPointerException e) {
            assertNotNull(e.getMessage());
        }
    }
}
