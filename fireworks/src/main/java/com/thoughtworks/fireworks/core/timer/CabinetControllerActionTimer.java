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

import com.thoughtworks.fireworks.controllers.CabinetController;

import java.util.Timer;

public class CabinetControllerActionTimer {
    private final Timer timer = new Timer();
    private final ReschedulableTask reschedulableTask;
    private final TimerScheduler scheduler;

    public CabinetControllerActionTimer(TimerScheduler scheduler, CabinetController controller) {
        this.scheduler = scheduler;
        this.reschedulableTask = new ReschedulableTaskAdapter(this, controller);
    }

    public void schedule(long delayTime) {
        scheduler.setDelayTime(delayTime);
        scheduler.schedule(timer, reschedulableTask);
    }

    public void reschedule() {
        scheduler.schedule(timer, reschedulableTask);
    }

    public void cancelTasks() {
        scheduler.cancelTasks();
    }
}
