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

import com.thoughtworks.fireworks.controllers.ShadowCabinetControllerListener;
import com.thoughtworks.fireworks.core.Chronograph;
import com.thoughtworks.fireworks.core.developer.ReschedulableTask;

import java.util.Timer;

public class TimerScheduler implements ShadowCabinetControllerListener {
    private final static int FIVE_SECONDS = 5000;

    private long delayTime = -1;
    private final Chronograph chronograph = new Chronograph();

    private final TimerTaskManager taskManager;

    public TimerScheduler(TimerTaskManager taskManager) {
        this.taskManager = taskManager;
    }

    synchronized public void setDelayTime(long delayTime) {
        if (isInFiveSecondsAfterFireCabinetAction()) {
            return;
        }
        this.delayTime = delayTime;
    }

    synchronized public void schedule(Timer timer, ReschedulableTask task) {
        if (delayTime < 0) {
            return;
        }
        timer.schedule(taskManager.getTask(task), delayTime);
    }

    public void cancelTasks() {
        setDelayTime(-1);
        taskManager.cancelTask();
    }

    public void actionStarted() {
        cancelTasks();
    }

    public void actionFinished() {
        chronograph.restart();
    }

    private boolean isInFiveSecondsAfterFireCabinetAction() {
        return chronograph.measurement() < FIVE_SECONDS;
    }
}
