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

import com.intellij.openapi.application.ApplicationManager;
import com.thoughtworks.fireworks.controllers.CabinetController;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class AutoRunTaskTimer {
    private final static Logger LOG = Logger.getLogger(AutoRunTestsTimerTask.class);

    private static void log(String str) {
        LOG.info(str);
//        System.out.println("#" + str);
    }

    public static final int TEN_SECONDS = 10000;

    private Timer timer;
    private long scheduleTime;
    private long delayTime;
    private long lastRunTime;
    private final CabinetController controller;
    private final CodeCompletionAdapter codeCompletion;

    public AutoRunTaskTimer(CabinetController controller, CodeCompletionAdapter codeCompletion) {
        this.controller = controller;
        this.codeCompletion = codeCompletion;
    }

    public void schedule(long delayTime) {
        if (delayTime < -1 || rescheduleInTenSecondsAfterScheduleRanLastTime()) {
            return;
        }
        this.delayTime = delayTime;

        schedule();
    }

    synchronized public void reSchedule() {
        if (timer != null && delayTime >= 0) {
            schedule();
        }
    }

    synchronized private void schedule() {
        if (System.currentTimeMillis() - scheduleTime < 100) {
            return;
        }
        scheduleTime = System.currentTimeMillis();

        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                doRun();
            }
        }, delayTime);
    }

    synchronized private void doRun() {
        log("do run");
        if (developerIsThinkingSomething()) {
            schedule();
            return;
        }
        cancelTimer();
        lastRunTime = System.currentTimeMillis();

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                if (timeout()) {
                    log("timeout: " + (timePeriod()));
                    schedule();
                } else {
                    new AutoRunTestsTimerTask(controller).run();
                }
            }
        });
    }

    private boolean developerIsThinkingSomething() {
        boolean isSelectingMenu = MenuSelectionManager.defaultManager().getSelectedPath().length > 0;
        return isSelectingMenu || codeCompletion.isWorking();
    }

    private boolean timeout() {
        return timePeriod() > 100;
    }

    private boolean rescheduleInTenSecondsAfterScheduleRanLastTime() {
        return timePeriod() < TEN_SECONDS;
    }

    synchronized private long timePeriod() {
        return System.currentTimeMillis() - lastRunTime;
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
