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
package com.thoughtworks.shadow.ant;

import junit.framework.TestCase;

import java.net.URL;
import java.util.List;
import java.util.Arrays;
import java.io.File;

import org.apache.tools.ant.types.Path;

public class UtilsTest extends TestCase {
    public void testShouldReturnNullIfPathStrsIsNullOrEmpty() throws Exception {
        assertNull(AntUtils.toPath(null, (URL[]) null));
        assertNull(AntUtils.toPath(null, new URL[0]));
    }

    public void testToPath() throws Exception {
        String[] files = new String[]{"file:/c:/file0", "file:/e:/file1"};
        URL[] urls = new URL[]{new URL(files[0]), new URL(files[1])};
        Path path = AntUtils.toPath(null, urls);

        assertTrue(path.toString().contains("c:"));
        assertTrue(path.toString().contains("file0"));
        assertTrue(path.toString().contains("e:"));
        assertTrue(path.toString().contains("file1"));
    }
}
