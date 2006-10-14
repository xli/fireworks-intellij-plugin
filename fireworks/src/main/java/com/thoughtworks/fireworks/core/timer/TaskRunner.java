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
import org.apache.log4j.Logger;

import javax.swing.*;

class TaskRunner {

    private final static Logger LOG = Logger.getLogger(CabinetControllerActionTimer.class);

    private static void log(String str) {
        LOG.info(str);
    }

    private final ReschedulableTask task;
    private final ApplicationAdaptee application;
    private final CodeCompletionAdaptee codeCompletion;
    private final AllEditorsOpenedAdaptee editors;

    TaskRunner(ReschedulableTask task, ApplicationAdaptee application, CodeCompletionAdaptee codeCompletion, AllEditorsOpenedAdaptee editors) {
        this.task = task;
        this.application = application;
        this.codeCompletion = codeCompletion;
        this.editors = editors;
    }

    public void run() {
        if (!editors.documentsInSourceOrTestContentAreValidAndTheyAreNotXmlOrDtdFiles()) {
            log("documentsInSourceOrTestContentAndTheyAreNotXmlOrDtdFiles has error");
            return;
        }
        if (developerIsThinkingSomething()) {
            log("developerIsThinkingSomething, reschedule");
            task.reschedule();
            return;
        }
        log("run task");
        application.invokeLater(new TimeoutableTask(task));
    }


    private boolean developerIsThinkingSomething() {
        boolean isSelectingMenu = MenuSelectionManager.defaultManager().getSelectedPath().length > 0;
        return isSelectingMenu || codeCompletion.isWorking();
    }
}
