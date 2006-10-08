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

import com.thoughtworks.shadow.TestUtils;
import junit.framework.TestCase;
import org.apache.tools.ant.DefaultLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AntSunshineTest extends TestCase {
    private final String encoding = "UTF-8";
    private final String jvm = "java.exe";
    private final String jvmVersion = "1.4";
    private final String maxMemory = "2M";
    private final File baseDir = new File(".").getAbsoluteFile().getParentFile();

    private ByteArrayOutputStream log;
    private DefaultLogger buildListener;
    private AntSunshine sunshine;

    protected void setUp() throws Exception {
        log = new ByteArrayOutputStream();
        buildListener = TestUtils.simpleBuildLogger(log);
        sunshine = new AntSunshine(TestUtils.classpaths(), encoding, baseDir, jvm, jvmVersion, maxMemory);
        sunshine.addBuildListener(buildListener);
    }

    public void testShine() throws Exception {
        sunshine.shine(AntJavaTaskAdapterHelper.class.getName());

        TestUtils.assertContain(log, "-Dfile.encoding=" + encoding);
        TestUtils.assertContain(log, baseDir.getAbsolutePath());
        TestUtils.assertContain(log, "Executing '" + jvm + "' with arguments:");
        TestUtils.assertContain(log, "'-Xmx" + maxMemory + "'");
    }

    public void testAddJvmArgs() throws Exception {
        String arg1 = "-Dkey1=jvmArg1Value";
        String arg2 = "-Dkey2=jvmArg2Value";
        sunshine.addJvmArgs(arg1 + " " + arg2);
        sunshine.shine(AntJavaTaskAdapterHelper.class.getName());

        TestUtils.assertContain(log, "jvmArg1Value");
        TestUtils.assertContain(log, "jvmArg2Value");
        TestUtils.assertContain(log, arg1);
        TestUtils.assertContain(log, arg2);

        TestUtils.assertContain(log, "-Dfile.encoding=" + encoding);
    }

    public void testShouldIgnoreNullAndEmptyArgsWhenAddJvmArgs() throws Exception {
        sunshine.addJvmArgs(null);
        sunshine.addJvmArgs("");

        sunshine.shine(AntJavaTaskAdapterHelper.class.getName());
        TestUtils.assertContain(log, "-Dfile.encoding=" + encoding);
    }
}
