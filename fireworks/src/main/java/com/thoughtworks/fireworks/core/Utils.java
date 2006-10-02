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
}
