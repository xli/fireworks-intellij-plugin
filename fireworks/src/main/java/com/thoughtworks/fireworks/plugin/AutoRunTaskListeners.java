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
package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentListener;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.adapters.RunContentListenerTimerAdapter;
import com.thoughtworks.fireworks.core.AutoRunTestConfigurationListener;
import com.thoughtworks.fireworks.core.FireworksConfig;
import org.picocontainer.Startable;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

public class AutoRunTaskListeners implements Startable {
    private final ProjectAdapter project;
    private final DocumentListener documentListener;
    private final AWTEventListener awtListener;
    private final RunContentListenerTimerAdapter timerAdapter;
    private FireworksConfig config;
    private boolean enable;

    public AutoRunTaskListeners(ProjectAdapter project, DocumentListener documentListener, AWTEventListener awtListener, RunContentListenerTimerAdapter timerAdapter, FireworksConfig config) {
        this.project = project;
        this.documentListener = documentListener;
        this.awtListener = awtListener;
        this.timerAdapter = timerAdapter;
        this.config = config;
    }

    synchronized private void enableListenersForAutoRunTask() {
        if (enable) {
            return;
        }
        enable = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(documentListener);
        project.getExecutionManager().getContentManager().addRunContentListener(timerAdapter);
    }

    synchronized private void disableListenersForAutoRunTask() {
        if (!enable) {
            return;
        }
        enable = false;
        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);
        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(documentListener);
        project.getExecutionManager().getContentManager().removeRunContentListener(timerAdapter);
    }

    public void start() {
        config.addAutoRunTestConfigurationListener(new AutoRunTestConfigurationListener() {
            public void change() {
                if (config.isAutoRunTestsEnabled() && config.isEnabled()) {
                    enableListenersForAutoRunTask();
                } else {
                    disableListenersForAutoRunTask();
                }
            }
        });

    }

    public void stop() {
        disableListenersForAutoRunTask();
    }
}
