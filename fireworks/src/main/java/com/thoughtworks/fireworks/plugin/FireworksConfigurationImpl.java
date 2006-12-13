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
package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.options.ConfigurationException;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.Utils;

public abstract class FireworksConfigurationImpl extends FireworksConfiguration implements FireworksConfig {

    private static final String DEFAULT_TEST_CLASS_NAME_REGEX = ".*Test";

    public String expectedTestCaseNameRegex = null;
    public String maxMemory = null;
    public int maxSize = 5;
    public boolean enable = true;
    public boolean enableAutoTask = true;
    public int autoRunTestsDelayTime = 4000;
    public String jvmArgs;
    public boolean clearLogConsole = true;

    public void apply() throws ConfigurationException {
        enable = getConfigurationUI().isEnable();
        resetEnableFireworks();

        enableAutoTask = getConfigurationUI().isAutoTaskEnabled();
        resetAutoTaskEnabled();

        maxMemory = getConfigurationUI().maxMemory();
        expectedTestCaseNameRegex = getConfigurationUI().expectedTestCaseNameRegex();
        setMaxSize(getConfigurationUI().maxSize());
        autoRunTestsDelayTime = getConfigurationUI().autoRunTestsDelayTime();
        jvmArgs = getConfigurationUI().jvmArgs();
        clearLogConsole = getConfigurationUI().getClearLogConsole();
    }

    public void reset() {
        getConfigurationUI().setEnable(isEnable());
        getConfigurationUI().setAutoTaskEnabled(isAutoTaskEnabled());
        getConfigurationUI().setMaxMemory(maxMemory());
        getConfigurationUI().setExpectedTestCaseNameRegex(expectedTestCaseNameRegex());
        getConfigurationUI().setMaxSize(maxSize());
        getConfigurationUI().setAutoRunTestsDelayTime(autoRunTestsDelayTime());
        getConfigurationUI().setJvmArgs(jvmArgs());
        getConfigurationUI().setClearLogConsole(clearLogConsole());
    }

    public void changeAutoTaskEnabled() {
        enableAutoTask = !enableAutoTask;
        resetAutoTaskEnabled();
    }

    protected abstract void resetAutoTaskEnabled();

    protected abstract void resetEnableFireworks();

    public boolean isEnable() {
        return enable;
    }

    public boolean isAutoTaskEnabled() {
        return enableAutoTask;
    }

    public String maxMemory() {
        if (maxMemory == null || !maxMemory.matches("\\d+")) {
            return null;
        }
        return maxMemory;
    }

    public String expectedTestCaseNameRegex() {
        if (Utils.isEmpty(expectedTestCaseNameRegex)) {
            return DEFAULT_TEST_CLASS_NAME_REGEX;
        }
        return expectedTestCaseNameRegex;
    }

    public int maxSize() {
        return maxSize;
    }

    public int autoRunTestsDelayTime() {
        return autoRunTestsDelayTime;
    }

    public String jvmArgs() {
        return jvmArgs;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean clearLogConsole() {
        return clearLogConsole;
    }
}
