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

import com.thoughtworks.fireworks.controllers.CabinetController;
import com.thoughtworks.fireworks.core.developer.ReschedulableTask;

public class ReschedulableTaskAdapter implements ReschedulableTask {
    private final CabinetControllerActionTimer timer;
    private final CabinetController controller;

    public ReschedulableTaskAdapter(CabinetControllerActionTimer timer, CabinetController controller) {
        this.timer = timer;
        this.controller = controller;
    }

    public void reschedule() {
        timer.reschedule();
    }

    public void run() {
        controller.fireRunTestListActionEvent();
    }
}
