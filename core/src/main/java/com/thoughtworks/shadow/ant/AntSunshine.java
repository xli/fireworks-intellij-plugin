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

import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.Utils;
import com.thoughtworks.shadow.junit.ErrorTestCase;
import com.thoughtworks.shadow.junit.LogThrowable;
import com.thoughtworks.shadow.junit.ProtectableFactory;
import junit.framework.Protectable;
import junit.framework.Test;
import org.apache.tools.ant.BuildListener;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AntSunshine implements Sunshine {
    private final List listeners = new ArrayList();
    private final URL[] classpaths;
    private final String encoding;
    private final File baseDir;
    private final String jvm;
    private final String jvmVersion;
    private final String maxMemory;
    private String jvmArgs;

    public AntSunshine(URL[] classpaths) {
        this(classpaths, null, null, null, null, null);
    }

    public AntSunshine(URL[] classpaths, String encoding, File baseDir, String jvm, String jvmVersion, String maxMemory) {
        this.classpaths = classpaths;
        this.encoding = encoding;
        this.baseDir = baseDir;
        this.jvm = jvm;
        this.jvmVersion = jvmVersion;
        this.maxMemory = maxMemory;
    }

    public Test shine(String testClassName) {
        Class testClass;
        try {
            testClass = Utils.load(classpaths, testClassName);
        } catch (ClassNotFoundException e) {
            return toTestCase(testClassName, e);
        }

        AntProjectInfoLogger logger = new AntProjectInfoLogger(testClass);
        antJavaTask(testClassName, logger).execute();
        return logger.toTestCase();
    }

    private Test toTestCase(String testClassName, ClassNotFoundException e) {
        String msg = MessageFormat.format("Can''t load test class \"{0}\" from classpaths \"{1}\"",
                new Object[]{testClassName, Utils.toClasspathStr(classpaths)});
        LogThrowable throwable = new LogThrowable(e.getMessage(), msg);
        Protectable protectable = ProtectableFactory.protectable(throwable);
        return new ErrorTestCase(testClassName, protectable);
    }

    public void addBuildListener(BuildListener listener) {
        listeners.add(listener);
    }

    private AntJavaTaskAdapter antJavaTask(String testClassName, AntProjectInfoLogger logger) {
        AntJavaTaskAdapter task = new AntJavaTaskAdapter(testClassName, logger.getRunnerClassName());
        task.addBuildListener(logger);
        addBuildListeners(task);
        setAttributes(task);
        return task;
    }

    private void setAttributes(AntJavaTaskAdapter task) {
        task.appendClasspaths(classpaths);
        if (Utils.isNotEmpty(encoding)) {
            task.setFileEncodeing(encoding);
        }
        if (baseDir != null) {
            task.setBaseDir(baseDir);
        }
        if (Utils.isNotEmpty(jvm)) {
            task.setJvm(jvm);
        }
        if (Utils.isNotEmpty(jvmVersion)) {
            task.setJvmVersion(jvmVersion);
        }
        if (Utils.isNotEmpty(maxMemory)) {
            task.setMaxMemory(maxMemory);
        }
        if (Utils.isNotEmpty(jvmArgs)) {
            task.addJvmArgs(jvmArgs);
        }
    }

    private void addBuildListeners(AntJavaTaskAdapter task) {
        for (int i = 0; i < listeners.size(); i++) {
            task.addBuildListener((BuildListener) listeners.get(i));
        }
    }

    public void addJvmArgs(String jvmArgs) {
        this.jvmArgs = jvmArgs;
    }
}
