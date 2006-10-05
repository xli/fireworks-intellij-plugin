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

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

public class Utils {

    public static final Color TRANSPARENT_WHITE = new Color(255, 255, 255, 0);
    public static final String LINE_SEP = com.thoughtworks.shadow.Utils.LINE_SEP;

    public static String getJavaVersion(String javaVersion) {
        if (!javaVersion.matches("[^\\d]*(\\d+\\.\\d+)[^\\d]*.*")) {
            throw new IllegalArgumentException("invalid java version str: " + javaVersion);
        }
        String[] num = javaVersion.split("[^0-9]+");
        if ("".equals(num[0])) {
            return num[1] + "." + num[2];
        }
        return num[0] + "." + num[1];
    }

    public static URL toURL(String presentableUrl) {
        return com.thoughtworks.shadow.Utils.toURL(presentableUrl);
    }

    public static String toString(Throwable t) {
        if (t == null) {
            return "";
        }
        StringWriter buffer = new StringWriter();
        t.printStackTrace(new PrintWriter(buffer));
        return buffer.toString();
    }

    public static boolean isEmpty(String str) {
        return com.thoughtworks.shadow.Utils.isEmpty(str);
    }
}
