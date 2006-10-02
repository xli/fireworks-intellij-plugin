package com.thoughtworks.fireworks.core;

import com.intellij.openapi.compiler.CompileStatusNotification;
import com.thoughtworks.shadow.Cabinet;
import junit.framework.TestResult;

public class CabinetActionNotification implements CompileStatusNotification {
    private final Cabinet cabinet;
    private final TestResult result;

    public CabinetActionNotification(Cabinet cabinet, TestResult result) {
        this.cabinet = cabinet;
        this.result = result;
    }

    public void finished(boolean aborted, int errors, int warnings) {
        if (aborted || errors > 0) {
            return;
        }
        cabinet.action(result);
    }
}
