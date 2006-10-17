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
package com.thoughtworks.fireworks.core.developer;

import com.thoughtworks.fireworks.core.Chronograph;
import org.apache.log4j.Logger;

public class TimeoutableTask implements Runnable {
    private final static Logger LOG = Logger.getLogger(TimeoutableTask.class);

    private static void log(String str) {
        LOG.info(str);
    }

    private final ReschedulableTask task;
    private final Chronograph chronograph;
    private static final int TIME_OUT = 100;

    public TimeoutableTask(ReschedulableTask task) {
        this.task = task;
        this.chronograph = new Chronograph();
        chronograph.restart();
    }

    public void run() {
        if (timeout()) {
            log("timeour, reschedule task.");
            task.reschedule();
        } else {
            log("run task.");
            task.run();
        }
    }

    private boolean timeout() {
        return chronograph.measurement() > TIME_OUT;
    }
}
