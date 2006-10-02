package com.thoughtworks.shadow.ant;

import com.thoughtworks.shadow.Utils;
import com.thoughtworks.shadow.junit.*;
import junit.framework.Protectable;
import junit.framework.Test;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

public class AntProjectInfoLogger implements BuildListener {
    private StringBuffer log = new StringBuffer();
    private StringBuffer allLevelLog = new StringBuffer();
    private final Class testClass;

    public AntProjectInfoLogger(Class testClass) {
        this.testClass = testClass;
    }

    public void messageLogged(BuildEvent event) {
        if (event.getPriority() == Project.MSG_INFO) {
            log.append(event.getMessage() + Utils.LINE_SEP);
        }
        allLevelLog.append(event.getMessage() + Utils.LINE_SEP);
    }

    public Test toTestCase() {
        try {
            return new TextTestRunnerOutputTestCase(testClass, log.toString());
        } catch (TestRunnerError error) {
            return toTestCase(error);
        }
    }

    private Test toTestCase(TestRunnerError error) {
        LogThrowable throwable = new LogThrowable(error.getMessage(), allLevelLog.toString());
        Protectable protectable = ProtectableFactory.protectable(throwable);
        return new ErrorTestCase(testClass.getName(), protectable);
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
}
