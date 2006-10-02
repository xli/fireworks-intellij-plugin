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
        classpaths[1] = Utils.toURL("libs/junit-3.8.2.jar");
        return classpaths;
    }

    public static void assertContain(ByteArrayOutputStream os, String str) {
        String message = "\"" + os + "\" should contain \"" + str + "\"";
        Assert.assertTrue(message, os.toString().indexOf(str) > 0);
    }
}
