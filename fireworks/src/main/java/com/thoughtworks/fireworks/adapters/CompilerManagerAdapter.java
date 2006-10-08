/*
 *    Copyright (c) 2006 LiXiao.
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
package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.application.ApplicationManager;
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

    private void run(final Runnable process) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                project.runProcessWithProgressSynchronously(process, "Running Tests...", true);
            }
        });
    }
}
