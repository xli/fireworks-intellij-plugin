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

import com.intellij.openapi.command.CommandAdapter;
import com.intellij.openapi.command.CommandEvent;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentListener;
import com.thoughtworks.fireworks.adapters.ProjectAdapter;
import com.thoughtworks.fireworks.adapters.RunContentListenerTimerAdapter;

import java.awt.*;
import java.awt.event.AWTEventListener;

public class AutoRunTaskListeners {
    public static final CommandAdapter LISTENER = new CommandAdapter() {
        public void commandStarted(CommandEvent event) {
//            System.out.println("cccccc name: " + event.getCommandName());
//            System.out.println("cccccc cmd : " + event.getCommand());
        }
    };
    private final ProjectAdapter project;
    private final DocumentListener documentListener;
    private final AWTEventListener awtListener;
    private final RunContentListenerTimerAdapter timerAdapter;
    private boolean enable;

    public AutoRunTaskListeners(ProjectAdapter project, DocumentListener documentListener, AWTEventListener awtListener, RunContentListenerTimerAdapter timerAdapter) {
        this.project = project;
        this.documentListener = documentListener;
        this.awtListener = awtListener;
        this.timerAdapter = timerAdapter;
    }

    synchronized public void enableListenersForAutoRunTask() {
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

        //todo
        CommandProcessor.getInstance().addCommandListener(LISTENER);
    }

    synchronized public void disableListenersForAutoRunTask() {
        if (!enable) {
            return;
        }
        enable = false;
        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);
        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(documentListener);
        project.getExecutionManager().getContentManager().removeRunContentListener(timerAdapter);
    }
}
