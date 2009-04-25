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

import com.thoughtworks.fireworks.core.ApplicationAdaptee;
import com.thoughtworks.fireworks.core.CompileStatusNotificationAdaptee;
import com.thoughtworks.fireworks.core.CompilerManagerAdaptee;

public class CompilerManagerAdapter implements CompilerManagerAdaptee {
    private final ApplicationAdaptee application;
    private final ProjectAdapter project;

    public CompilerManagerAdapter(ApplicationAdaptee application, ProjectAdapter project) {
        this.application = application;
        this.project = project;
    }

    public void compile(final CompileStatusNotificationAdaptee compileStatusNotification) {
        CompileStatusNotificationAdaptee notification = new CompileStatusNotificationAdaptee() {
            public void finished(boolean aborted, int errors, int warnings) {
                run(process(aborted, errors, warnings, compileStatusNotification));
            }
        };
        project.make(notification);
    }

    private Runnable process(final boolean aborted,
                             final int errors,
                             final int warnings,
                             final CompileStatusNotificationAdaptee compileStatusNotification) {
        return new Runnable() {
            public void run() {
                compileStatusNotification.finished(aborted, errors, warnings);
            }
        };
    }

    private void run(final Runnable process) {
        Thread thread = new Thread(process);
        thread.start();
    }
}
