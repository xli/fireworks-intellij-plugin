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

public class DeveloperTask implements ReschedulableTask {
    private final Developer developer;
    private final ReschedulableTask task;

    public DeveloperTask(Developer developer, ReschedulableTask task) {
        this.developer = developer;
        this.task = task;
    }

    public void reschedule() {
        task.reschedule();
    }

    public void run() {
        developer.considersRunning(task);
    }
}
