package com.thoughtworks.fireworks.adapters.compatibility;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class RunProcessWithProgressSyn {
    private final static Logger LOG = Logger.getLogger(RunProcessWithProgressSyn.class);

    private final static String methodName = "runProcessWithProgressSynchronously";
    private final static Class[] paramTypes = new Class[]{Runnable.class, String.class, boolean.class, Project.class};

    private final Runnable process;
    private final String title;
    private final boolean canBeCanceled;
    private final Project project;
    private Method method;
    private Object obj;

    public RunProcessWithProgressSyn(Runnable process, String title, boolean canBeCanceled, Project project) {
        this.process = process;
        this.title = title;
        this.canBeCanceled = canBeCanceled;
        this.project = project;
        try {
            method = Application.class.getMethod(methodName, paramTypes);
            obj = ApplicationManager.getApplication();
        } catch (NoSuchMethodException e) {
            try {
                method = ProgressManager.class.getMethod(methodName, paramTypes);
                obj = ProgressManager.getInstance();
            } catch (NoSuchMethodException e1) {
                throw new UnsupportedOperationException("Can't find " + methodName + " from Application and ProgressManager!");
            }
        }
    }

    public void execute() {
        try {
            method.invoke(obj, new Object[]{process, title, canBeCanceled, project});
        } catch (Exception e) {
            LOG.error("catch exception when invoking " + methodName + ": " + method, e);
        }

    }
}
