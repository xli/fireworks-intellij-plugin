package com.thoughtworks.fireworks.core;

import com.intellij.openapi.compiler.CompileStatusNotification;

public class SimpleCompilerManagerAdapter implements CompilerManagerAdaptee {
    private final boolean abort;
    private final int errors;
    private final int warnings;

    public SimpleCompilerManagerAdapter(boolean abort, int errors, int warnings) {
        this.abort = abort;
        this.errors = errors;
        this.warnings = warnings;
    }

    public SimpleCompilerManagerAdapter() {
        this(false, 0, 0);
    }

    public void compile(CompileStatusNotification compileStatusNotification) {
        compileStatusNotification.finished(abort, errors, warnings);
    }
}
