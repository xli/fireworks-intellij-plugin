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

import com.thoughtworks.shadow.Utils;
import com.thoughtworks.shadow.junit.JUnitAdapter;
import com.thoughtworks.shadow.junit.LogThrowable;
import com.thoughtworks.shadow.junit.TestRunnerError;
import com.thoughtworks.shadow.junit.TextTestRunnerOutputTestCase;
import junit.framework.Test;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

public class AntProjectInfoLogger implements BuildListener {
    private final StringBuffer log = new StringBuffer();
    private final StringBuffer allLevelLog = new StringBuffer();
    private final JUnitAdapter junitAdapter;

    public AntProjectInfoLogger(Class testClass) {
        junitAdapter = new JUnitAdapter(testClass);
    }

    public void messageLogged(BuildEvent event) {
        if (event.getPriority() == Project.MSG_INFO) {
            log.append(event.getMessage() + Utils.LINE_SEP);
        }
        allLevelLog.append(event.getMessage() + Utils.LINE_SEP);
    }

    public Test toTestCase() {
        try {
            return new TextTestRunnerOutputTestCase(junitAdapter, log.toString());
        } catch (TestRunnerError error) {
            LogThrowable throwable = new LogThrowable(error.getMessage(), allLevelLog.toString());
            return junitAdapter.toTestCase(throwable);
        }
    }

    public void buildStarted(BuildEvent event) {
    }

    public void buildFinished(BuildEvent event) {
    }

    public void targetStarted(BuildEvent event) {
    }

    public void targetFinished(BuildEvent event) {
    }

    public void taskStarted(BuildEvent event) {
    }

    public void taskFinished(BuildEvent event) {
    }

    public String getRunnerClassName() {
        return junitAdapter.runnerClass().getName();
    }
}
