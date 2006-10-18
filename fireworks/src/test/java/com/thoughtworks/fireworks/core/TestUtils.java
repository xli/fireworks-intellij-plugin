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
package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.ant.AntSunshine;
import junit.framework.Assert;
import junit.framework.TestResult;

import java.net.URL;
import java.io.File;

public class TestUtils {

    public static String baseDir() {
        if(new File("fireworks").isDirectory()) {
            return "fireworks/";
        }
        return "";
    }

    public static Sunshine sunshine() {
        return new AntSunshine(classpaths());
    }

    public static URL[] classpaths() {
        URL[] classpaths = new URL[2];
        classpaths[0] = Utils.toURL(baseDir() + "target/test-classes");
        classpaths[1] = Utils.toURL(baseDir() + "src/test/libs/junit-3.8.2.jar");
        return classpaths;
    }

    public static void assertTestResultRunOnceSuccessfully(TestResult result) {
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static void assertTestResultRunOnceFailed(TestResult result) {
        Assert.assertEquals(1, result.runCount());
        Assert.assertEquals(1, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }

    public static void assertNoTestWasRan(TestResult result) {
        Assert.assertEquals(0, result.runCount());
        Assert.assertEquals(0, result.failureCount());
        Assert.assertEquals(0, result.errorCount());
    }
}
