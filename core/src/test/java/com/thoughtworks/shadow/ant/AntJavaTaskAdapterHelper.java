package com.thoughtworks.shadow.ant;

import junit.framework.TestCase;

import java.io.File;

public class AntJavaTaskAdapterHelper extends TestCase {
    public void testPrintBaseDir() throws Exception {
        System.out.println(new File(".").getAbsolutePath());
    }
}
