package com.thoughtworks.fireworks.adapters;

import com.thoughtworks.fireworks.core.TestCounterListener;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.TestResultStatus;

public class StatusBarAdapter implements TestCounterListener, ShadowCabinetListener {
    private final ProjectAdapter project;
    private TestResultStatus status;

    public StatusBarAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public void testResult(int runCount, int failureCount, int errorCount) {
        status = new TestResultStatus(runCount, failureCount, errorCount);
        project.setStatusBarInfo(status.summary());
    }

    public void afterAddTest(ComparableTestShadow test) {
        project.setStatusBarInfo("Added \"" + test + "\" into Fireworks");
    }

    public void afterRemoveTest(ComparableTestShadow test) {
        project.setStatusBarInfo("Removed \"" + test + "\" from Fireworks");
    }

    public void endAction() {
        project.setStatusBarInfo(status.summary());
    }

    public void startAction() {
        status = new TestResultStatus(0, 0, 0);
        project.setStatusBarInfo("Bang went the fireworks.");
    }
}
