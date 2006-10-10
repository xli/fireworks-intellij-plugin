package com.thoughtworks.fireworks.adapters.compatibility;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.thoughtworks.fireworks.core.CompileStatusNotificationAdaptee;

public class CompileStatusNotificationAdapter implements CompileStatusNotification {
    private final CompileStatusNotificationAdaptee notification;

    public CompileStatusNotificationAdapter(CompileStatusNotificationAdaptee notification) {
        this.notification = notification;
    }

    public void finished(boolean aborted, int errors, int warnings) {
        notification.finished(aborted, errors, warnings);
    }

    /**
     * for IDEA 6
     *
     * @param aborted
     * @param errors
     * @param warnings
     * @param compileContext
     */
    public void finished(boolean aborted, int errors, int warnings, CompileContext compileContext) {
        finished(aborted, errors, warnings);
    }
}
