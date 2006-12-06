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

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

import java.net.URL;
import java.io.File;

public class AntUtils {
    public static Path toPath(Project antProject, URL[] pathURLs) {
        if (pathURLs == null || pathURLs.length == 0) {
            return null;
        }
        Path path = new Path(antProject, getPath(pathURLs[0]));
        for (int i = 1; i < pathURLs.length; i++) {
            path.append(new Path(antProject, getPath(pathURLs[i])));
        }
        return path;
    }

    private static String getPath(URL url) {
        return new File(url.getFile()).getPath();
    }
}
