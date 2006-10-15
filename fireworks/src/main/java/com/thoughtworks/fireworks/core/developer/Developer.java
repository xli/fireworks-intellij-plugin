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

import com.thoughtworks.fireworks.core.ApplicationAdaptee;
import org.apache.log4j.Logger;

public class Developer {
    private final static Logger LOG = Logger.getLogger(Developer.class);

    private static void log(String str) {
        LOG.info(str);
    }

    private final ApplicationAdaptee application;
    private final Thought[] thoughts;

    public Developer(ApplicationAdaptee application, Thought[] thoughts) {
        this.application = application;
        this.thoughts = thoughts;
    }

    private boolean isThinkingSomething() {
        for (int i = 0; i < thoughts.length; i++) {
            Thought thought = thoughts[i];
            if (thought.isWorking()) {
                log("Thought works :p");
                return true;
            }
        }
        log("Thought does not work :Q");
        return false;
    }

    public void considersRunning(ReschedulableTask task) {
        if (isThinkingSomething()) {
            task.reschedule();
        } else {
            application.invokeLater(new TimeoutableTask(task));
        }
    }
}
