package com.thoughtworks.shadow.ant;

import org.apache.tools.ant.Project;

public class AntProjectExecutor {
    public void executeDefaultTarget(Project project) {
        Throwable error = null;
        try {
            project.fireBuildStarted();
            project.executeTarget(project.getDefaultTarget());
        } catch (RuntimeException re) {
            error = re;
        } finally {
            project.fireBuildFinished(error);
        }
    }
}
