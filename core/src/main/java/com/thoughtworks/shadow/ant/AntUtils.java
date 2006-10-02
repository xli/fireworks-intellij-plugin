package com.thoughtworks.shadow.ant;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

import java.net.URL;

public class AntUtils {
    public static Path toPath(Project antProject, URL[] pathURLs) {
        if (pathURLs == null || pathURLs.length == 0) {
            return null;
        }
        Path path = new Path(antProject, pathURLs[0].getFile());
        for (int i = 1; i < pathURLs.length; i++) {
            path.append(new Path(antProject, pathURLs[i].getFile()));
        }
        return path;
    }
}
