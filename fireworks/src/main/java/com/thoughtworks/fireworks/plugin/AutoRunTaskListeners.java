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

import java.awt.*;
import java.awt.event.AWTEventListener;

public class AutoRunTaskListeners {
    private final DocumentListener documentListener;
    private final AWTEventListener awtListener;
    private boolean enable;

    public AutoRunTaskListeners(DocumentListener documentListener, AWTEventListener awtListener) {
        this.documentListener = documentListener;
        this.awtListener = awtListener;
    }

    synchronized public void enableListenersForAutoRunTask() {
        if (enable) {
            return;
        }
        enable = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_EVENT_MASK);
        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(documentListener);
    }

    synchronized public void disableListenersForAutoRunTask() {
        if (!enable) {
            return;
        }
        enable = false;
        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);
        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(documentListener);
    }
}
