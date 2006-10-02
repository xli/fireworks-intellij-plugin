package com.thoughtworks.shadow.ant;

import com.thoughtworks.shadow.TestUtils;
import junit.framework.TestCase;
import org.apache.tools.ant.DefaultLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AntSunshineTest extends TestCase {
    private ByteArrayOutputStream log = new ByteArrayOutputStream();
    private DefaultLogger buildListener = TestUtils.simpleBuildLogger(log);
    private String encoding = "UTF-8";
    private String jvm = "java.exe";
    private String jvmVersion = "1.4";
    private String maxMemory = "2M";
    private File baseDir = new File(".").getAbsoluteFile().getParentFile();

    public void testShine() throws Exception {
        AntSunshine sunshine = new AntSunshine(TestUtils.classpaths(), encoding, baseDir, jvm, jvmVersion, maxMemory);
        sunshine.addBuildListener(buildListener);

        sunshine.shine(AntJavaTaskAdapterHelper.class.getName());

        TestUtils.assertContain(log, "-Dfile.encoding=" + encoding);
        TestUtils.assertContain(log, baseDir.getAbsolutePath());
        TestUtils.assertContain(log, "Executing '" + jvm + "' with arguments:");
        TestUtils.assertContain(log, "'-Xmx" + maxMemory + "'");
    }
}
