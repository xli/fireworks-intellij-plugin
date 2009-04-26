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
package com.thoughtworks.fireworks.controllers;

import org.picocontainer.Startable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.thoughtworks.fireworks.core.FireworksRunningStatus;

public class ShadowCabinetController implements Startable, CabinetController {
    private final ShadowCabinetView view;
    private final AllTestsRunner runAllTests;
    private final RecentTestListRunner recentTestListRunner;
    private final ShadowCabinetControllerListener[] listeners;
    private final FireworksRunningStatus status;

    public ShadowCabinetController(ShadowCabinetControllerListener[] listeners,
                                   ShadowCabinetView view,
                                   AllTestsRunner runAllTests,
                                   RecentTestListRunner recentTestListRunner,
                                   FireworksRunningStatus status) {
        this.listeners = listeners;
        this.view = view;
        this.runAllTests = runAllTests;
        this.recentTestListRunner = recentTestListRunner;
        this.status = status;
    }

    public void start() {
        view.registerToolWindow();
        view.addRunTestListActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireRunTestListActionEvent();
            }
        });
        view.addRunAllTestsActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireRunAllTestsActionEvent();
            }
        });
    }

    public void stop() {
        view.unregisterToolWindow();
        view.removeAllActionListeners();
    }

    public synchronized void fireRunAllTestsActionEvent() {
        if (this.status.working()) {
            return;
        }
        fireActionStartedEvent();
        runAllTests.run();
        fireActionFinishedEvent();
    }

    public synchronized void fireRunTestListActionEvent() {
        if (this.status.working()) {
            return;
        }
        fireActionStartedEvent();
        recentTestListRunner.run();
        fireActionFinishedEvent();
    }

    private void fireActionStartedEvent() {
        for (int i = 0; i < listeners.length; i++) {
            ShadowCabinetControllerListener listener = listeners[i];
            listener.actionStarted();
        }
    }

    private void fireActionFinishedEvent() {
        for (int i = 0; i < listeners.length; i++) {
            ShadowCabinetControllerListener listener = listeners[i];
            listener.actionFinished();
        }
    }
}
