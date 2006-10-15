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
package com.thoughtworks.fireworks.controllers.timer;

import com.thoughtworks.fireworks.core.developer.Developer;
import com.thoughtworks.fireworks.core.developer.ReschedulableTask;

class TaskRunner {
    private final ReschedulableTask task;
    private final Developer developer;
    private final AllEditorsOpenedAdaptee editors;

    TaskRunner(ReschedulableTask task, Developer developer, AllEditorsOpenedAdaptee editors) {
        this.task = task;
        this.developer = developer;
        this.editors = editors;
    }

    public void run() {
        if (!editors.documentsInSourceOrTestContentAreValidAndTheyAreNotXmlOrDtdFiles()) {
            return;
        }
        developer.considersRunning(task);
    }
}
