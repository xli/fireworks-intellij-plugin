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
package com.thoughtworks.fireworks.core.timer;

import com.thoughtworks.fireworks.core.ApplicationAdaptee;

import java.util.TimerTask;

public class TaskRunnerFactory {
    private final ApplicationAdaptee application;
    private final CodeCompletionAdaptee codeCompletion;
    private final AllEditorsOpenedAdaptee editors;

    public TaskRunnerFactory(ApplicationAdaptee application, CodeCompletionAdaptee codeCompletion, AllEditorsOpenedAdaptee editors) {
        this.application = application;
        this.codeCompletion = codeCompletion;
        this.editors = editors;
    }

    public TimerTask createTaskRunner(final ReschedulableTask task) {
        return new TimerTask() {
            public void run() {
                application.invokeLater(new Runnable() {
                    public void run() {
                        new TaskRunner(task, application, codeCompletion, editors).run();
                    }
                });
            }
        };
    }
}
