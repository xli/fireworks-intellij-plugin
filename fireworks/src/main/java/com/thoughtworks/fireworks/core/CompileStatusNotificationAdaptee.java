package com.thoughtworks.fireworks.core;

public interface CompileStatusNotificationAdaptee {
    void finished(boolean aborted, int errors, int warnings);
}
