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

import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentListener;
import com.thoughtworks.fireworks.controllers.timer.ConfiguredTimer;
import org.apache.log4j.Logger;

public class RunContentListenerTimerAdapter implements RunContentListener {
    private final static Logger LOG = Logger.getLogger(RunContentListenerTimerAdapter.class);

    private ProcessAdapter listener;

    public RunContentListenerTimerAdapter(final ConfiguredTimer timer) {
        listener = new ProcessAdapter() {
            public void startNotified(final ProcessEvent event) {
                LOG.info("cancel timer tasks by process listener.");
                timer.cancelTasks();
            }
        };
    }

    public void contentSelected(RunContentDescriptor descriptor) {
        addListenerIntoNotStartedProcessHandler(descriptor);
    }

    public void contentRemoved(RunContentDescriptor descriptor) {
        addListenerIntoNotStartedProcessHandler(descriptor);
    }

    private void addListenerIntoNotStartedProcessHandler(RunContentDescriptor descriptor) {
        if (descriptor != null) {
            ProcessHandler runningProcess = descriptor.getProcessHandler();
            if (runningProcess != null) {
                if (!runningProcess.isStartNotified()) {
                    runningProcess.addProcessListener(listener);
                } else {
                    listener.startNotified(null);
                }
            }
        }
    }

}
