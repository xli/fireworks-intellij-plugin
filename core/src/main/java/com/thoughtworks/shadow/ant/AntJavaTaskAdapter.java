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

package com.thoughtworks.shadow.ant;

import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Java;

import java.io.File;
import java.net.URL;

public class AntJavaTaskAdapter {
    private Project project;
    private Java javaTask;

    public AntJavaTaskAdapter(String testClassName, String runnerClassName) {
        project = new Project();
        project.setName(testClassName);
        project.init();

        Target target = target(testClassName, runnerClassName);
        project.addTarget(target);
        project.setDefault(target.getName());
    }

    public void execute() {
        new AntProjectExecutor().executeDefaultTarget(project);
    }

    public void addBuildListener(BuildListener buildListener) {
        project.addBuildListener(buildListener);
    }

    public void appendClasspaths(URL[] classpaths) {
        javaTask.createClasspath().append(AntUtils.toPath(project, classpaths));
    }

    private Target target(String testClassName, String runnerClassName) {
        Target target = new Target();
        target.setName(testClassName);
        target.addTask(javaTask(testClassName, runnerClassName));
        return target;
    }

    private Java javaTask(String testClassName, String runnerClassName) {
        javaTask = (Java) project.createTask("java");
        javaTask.setFork(true);
        javaTask.setNewenvironment(true);
        javaTask.setClassname(runnerClassName);
        javaTask.setFailonerror(true);
        javaTask.createArg().setValue(testClassName);
        return javaTask;
    }

    public void setFileEncodeing(String encoding) {
        String encodingArg = "-Dfile.encoding=" + encoding;
        addJvmArgs(encodingArg);
    }

    public void addJvmArgs(String args) {
        javaTask.createJvmarg().setLine(args);
    }

    public void setBaseDir(File baseDir) {
        project.setBaseDir(baseDir);
    }

    public void setJvm(String jvm) {
        javaTask.setJvm(jvm);
    }

    public void setJvmVersion(String jvmVersion) {
        javaTask.setJVMVersion(jvmVersion);
    }

    public void setMaxMemory(String maxMemory) {
        javaTask.setMaxmemory(maxMemory);
    }
}
