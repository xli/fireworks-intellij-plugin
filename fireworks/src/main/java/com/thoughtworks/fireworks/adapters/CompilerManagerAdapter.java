package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.compiler.CompileStatusNotification;
import com.thoughtworks.fireworks.core.CompilerManagerAdaptee;

public class CompilerManagerAdapter implements CompilerManagerAdaptee {
    private final ProjectAdapter project;

    public CompilerManagerAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public void compile(final CompileStatusNotification compileStatusNotification) {
        project.make(new CompileStatusNotification() {
            public void finished(boolean aborted, int errors, int warnings) {
                run(process(aborted, errors, warnings, compileStatusNotification));
            }
        });
    }

    private Runnable process(final boolean aborted,
                             final int errors,
                             final int warnings,
                             final CompileStatusNotification compileStatusNotification) {
        return new Runnable() {
            public void run() {
                compileStatusNotification.finished(aborted, errors, warnings);
            }
        };
    }

    private void run(Runnable process) {
        project.runProcessWithProgressSynchronously(process, "Running Tests...", true);
    }
}
