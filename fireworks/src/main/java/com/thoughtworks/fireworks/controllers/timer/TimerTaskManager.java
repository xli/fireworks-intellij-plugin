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

import com.thoughtworks.fireworks.core.developer.ReschedulableTask;

import java.util.TimerTask;

public class TimerTaskManager {

    private final TimerTaskFactory factory;
    private TimerTask task;

    public TimerTaskManager(TimerTaskFactory factory) {
        this.factory = factory;
    }

    synchronized TimerTask getTask(ReschedulableTask reschedulableTask) {
        cancelTask();
        task = factory.createTimerTask(reschedulableTask);
        return task;
    }

    synchronized boolean cancelTask() {
        if (task != null) {
            return task.cancel();
        }
        return false;
    }
}
