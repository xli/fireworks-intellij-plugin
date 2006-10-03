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

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.thoughtworks.fireworks.controllers.CabinetController;
import com.thoughtworks.fireworks.controllers.ShadowCabinetControllerListener;
import com.thoughtworks.fireworks.core.FireworksConfig;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.util.Timer;

public class DocumentListenerAdapter implements DocumentListener, AWTEventListener, ShadowCabinetControllerListener {

    private static final Logger LOG = Logger.getLogger(DocumentListenerAdapter.class);

    private final CabinetController controller;
    private final FireworksConfig config;
    private Timer timer;
    private boolean isFiringCabinetActionByMyself;
    private boolean autoRunTaskIsScheduled;
    private long lastTimeOfFiringCabinetAcion;

    public DocumentListenerAdapter(CabinetController controller, FireworksConfig config) {
        this.controller = controller;
        this.config = config;
        this.controller.addListener(this);
    }

    public void actionWasFired() {
        if (isFiringCabinetActionByMyself) {
            return;
        }
        cancelTimer();
    }

    public void documentChanged(DocumentEvent event) {
        if (isNonViewerWritableDoc(event.getDocument())) {
            LOG.info("isNonViewerWritableDoc Changed");
            scheduleAutoRunTestsTask();
        }
    }

    synchronized public void eventDispatched(AWTEvent event) {
        LOG.info("eventDispatched: " + status());
        if (autoRunTaskIsScheduled) {
            scheduleAutoRunTestsTask();
        }
    }

    synchronized private void scheduleAutoRunTestsTask() {
        LOG.info("scheduleAutoRunTestsTask: " + status());
        if (isNotEnabled() || isFiringCabinetActionByMyself || isTooFrequent()) {
            return;
        }

        LOG.info("schedule a new task...");
        cancelTimer();
        autoRunTaskIsScheduled = true;
        timer = new Timer();
        timer.schedule(new AutoRunTestsTimerTask(this), config.autoRunTestsDelayTime());
    }

    synchronized private void cancelTimer() {
        if (timer != null) {
            LOG.info("cancel timer");
            timer.cancel();
            timer = null;
            autoRunTaskIsScheduled = false;
        }
    }

    synchronized void fireCabinetActionEvent() {
        isFiringCabinetActionByMyself = true;
        controller.fireRunTestListActionEvent();
        isFiringCabinetActionByMyself = false;
        lastTimeOfFiringCabinetAcion = System.currentTimeMillis();
        autoRunTaskIsScheduled = false;
    }

    public void beforeDocumentChange(DocumentEvent event) {
    }

    private String status() {
        StringBuilder builder = new StringBuilder();
        builder.append("[auto run tests delay time: " + config.autoRunTestsDelayTime() + "]");
        builder.append("[fireInControl: " + isFiringCabinetActionByMyself + "]");
        builder.append("[lastFireTime: " + lastTimeOfFiringCabinetAcion + "]");
        builder.append("[scheduled: " + autoRunTaskIsScheduled + "]");
        return builder.toString();
    }

    private boolean isNotEnabled() {
        boolean isNotEnabled = config.autoRunTestsDelayTime() <= 0;
        return isNotEnabled;
    }

    private boolean isTooFrequent() {
        long timePassedAfterLastTimeOfFiringCabinetAction = System.currentTimeMillis() - lastTimeOfFiringCabinetAcion;
        boolean isTooFrequent = timePassedAfterLastTimeOfFiringCabinetAction < config.autoRunTestsDelayTime();
        return isTooFrequent;
    }

    private boolean isNonViewerWritableDoc(Document doc) {
        if (!doc.isWritable()) {
            return false;
        }
        Editor[] editors = EditorFactory.getInstance().getEditors(doc);
        for (int i = 0; i < editors.length; i++) {
            if (editors[i].isViewer()) {
                continue;
            }
            return true;
        }
        return false;
    }

}
