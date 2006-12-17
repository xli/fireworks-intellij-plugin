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

import com.thoughtworks.fireworks.core.AutoRunTestConfigurationListener;
import com.thoughtworks.fireworks.core.FireworksConfig;

public class ConfiguredTimer implements AutoRunTestConfigurationListener {
    private final FireworksConfig config;
    private final CabinetControllerActionTimer timer;

    public ConfiguredTimer(FireworksConfig config, CabinetControllerActionTimer timer) {
        this.config = config;
        this.timer = timer;
        config.addAutoRunTestConfigurationListener(this);
    }

    public void schedule() {
        timer.schedule(config.autoRunTestsDelayTime());
    }

    public void reschedule() {
        timer.reschedule();
    }

    public void cancelTasks() {
        timer.cancelTasks();
    }

    public void change() {
        if (config.isEnabled() && config.isAutoRunTestsEnabled()) {
            return;
        }
        cancelTasks();
    }
}
