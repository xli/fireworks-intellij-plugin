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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Utils {
    public final static String LINE_SEP = System.getProperty("line.separator");

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static Class load(URL[] classpaths, String className) throws ClassNotFoundException {
        if (className == null) {
            throw new NullPointerException("className should not be null!");
        }
        return new URLClassLoader(classpaths).loadClass(className);
    }

    public static String toClasspathStr(URL[] classpaths) {
        StringBuffer buffer = new StringBuffer();
        for (int k = 0; k < classpaths.length; k++) {
            URL classpath = classpaths[k];
            buffer.append(classpath.getFile());
            buffer.append(";");
        }
        return buffer.toString();
    }

    public static URL toURL(String file) {
        try {
            return new File(file).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Format file \"" + file + "\" to URL failed.");
        }
    }

    public static boolean isEqual(String str1, String str2) {
        if (str1 == str2) {
            return true;
        }
        if (str1 != null) {
            return str1.equals(str2);
        }
        return str2.equals(str1);
    }
}
