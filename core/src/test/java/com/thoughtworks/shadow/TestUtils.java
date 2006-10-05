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

import com.thoughtworks.shadow.ant.AntSunshine;
import junit.framework.Assert;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;

public class TestUtils {
    public static DefaultLogger simpleBuildLogger(ByteArrayOutputStream log) {
        DefaultLogger buildListener = new DefaultLogger();
        buildListener.setMessageOutputLevel(Project.MSG_DEBUG);
        buildListener.setOutputPrintStream(new PrintStream(log));
        buildListener.setErrorPrintStream(new PrintStream(log));
        return buildListener;
    }

    public static Sunshine sunshine() {
        return new AntSunshine(classpaths());
    }

    public static URL[] classpaths() {
        URL[] classpaths = new URL[2];
        classpaths[0] = Utils.toURL("target/test-classes");
        classpaths[1] = Utils.toURL("libs/junit/junit/4.1/junit-4.1.jar");
        return classpaths;
    }

    public static void assertContain(ByteArrayOutputStream os, String str) {
        String message = "\"" + os + "\" should contain \"" + str + "\"";
        Assert.assertTrue(message, os.toString().indexOf(str) > 0);
    }
}
