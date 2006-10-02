package com.thoughtworks.shadow.ant;

import com.thoughtworks.shadow.TestUtils;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AntJavaTaskAdapterTest extends TestCase {
    private AntJavaTaskAdapter task;
    private ByteArrayOutputStream log;

    protected void setUp() throws Exception {
        log = new ByteArrayOutputStream();
        task = new AntJavaTaskAdapter(AntJavaTaskAdapterHelper.class.getName());
        task.addBuildListener(TestUtils.simpleBuildLogger(log));
        task.appendClasspaths(TestUtils.classpaths());
    }

    public void testSetFileEncoding() throws Exception {
        String encoding = "UTF-8";
        task.setFileEncodeing(encoding);
        task.execute();
        TestUtils.assertContain(log, "-Dfile.encoding=" + encoding);
    }

    public void testSetBaseDir() throws Exception {
        File baseDir = new File(".").getAbsoluteFile().getParentFile();
        task.setBaseDir(baseDir);
        task.execute();
        TestUtils.assertContain(log, baseDir.getAbsolutePath());
    }

    public void testSetJvm() throws Exception {
        String jvm = "java.exe";
        task.setJvm(jvm);
        task.execute();
        TestUtils.assertContain(log, "Executing '" + jvm + "' with arguments:");
    }

    public void testSetJvmVersion() throws Exception {
        String jvmVersion = "1.1";
        String maxMemory = "2M";
        task.setJvmVersion(jvmVersion);
        task.setMaxMemory(maxMemory);
        task.execute();
        TestUtils.assertContain(log, "'-mx" + maxMemory + "'");
    }
}
