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
import com.thoughtworks.fireworks.core.AutoRunTestConfigurationListener;
import com.thoughtworks.fireworks.core.FireworksConfig;
import com.thoughtworks.shadow.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class FireworksConfigurationImpl extends FireworksConfiguration implements FireworksConfig {

    private static final String DEFAULT_TEST_CLASS_NAME_REGEX = ".*Test";

    public String expectedTestCaseNameRegex = null;
    public String maxMemory = null;
    public int maxSize = 5;
    public boolean enable = true;
    public boolean enableAutoTask = true;
    public int autoRunTestsDelayTime = 1000;
    public String jvmArgs;
    public boolean clearLogConsole = true;
    public boolean autoShowErrorsInEditorAfterCompile = true;

    private List<AutoRunTestConfigurationListener> listeners = new ArrayList<AutoRunTestConfigurationListener>();

    public void apply() throws ConfigurationException {
        enable = getConfigurationUI().isEnable();
        resetEnableFireworks();

        enableAutoTask = getConfigurationUI().isAutoTaskEnabled();
        fireAutoRunTestConfigurationListener();

        maxMemory = getConfigurationUI().maxMemory();
        expectedTestCaseNameRegex = getConfigurationUI().expectedTestCaseNameRegex();
        setMaxSize(getConfigurationUI().maxSize());
        autoRunTestsDelayTime = getConfigurationUI().autoRunTestsDelayTime();
        jvmArgs = getConfigurationUI().jvmArgs();
        clearLogConsole = getConfigurationUI().getClearLogConsole();
        autoShowErrorsInEditorAfterCompile = getConfigurationUI().getAutoShowErrorsInEditorAfterCompile();
    }

    public void reset() {
        getConfigurationUI().setEnable(isEnabled());
        getConfigurationUI().setAutoTaskEnabled(isAutoRunTestsEnabled());
        getConfigurationUI().setMaxMemory(maxMemory());
        getConfigurationUI().setExpectedTestCaseNameRegex(expectedTestCaseNameRegex());
        getConfigurationUI().setMaxSize(maxSize());
        getConfigurationUI().setAutoRunTestsDelayTime(autoRunTestsDelayTime());
        getConfigurationUI().setJvmArgs(jvmArgs());
        getConfigurationUI().setClearLogConsole(clearLogConsole());
        getConfigurationUI().setAutoShowErrorsInEditorAfterCompile(autoShowErrorsInEditorAfterCompile());
    }

    public void switchAutoRunTestsConfiguration() {
        enableAutoTask = !enableAutoTask;
        fireAutoRunTestConfigurationListener();
    }

    protected void fireAutoRunTestConfigurationListener() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).change();
        }
    }

    protected void clearConfigurationListeners() {
        listeners.clear();
    }

    public void addAutoRunTestConfigurationListener(AutoRunTestConfigurationListener listener) {
        listeners.add(listener);
    }

    public boolean autoShowErrorsInEditorAfterCompile() {
        return autoShowErrorsInEditorAfterCompile;
    }

    protected abstract void resetEnableFireworks();

    public boolean isEnabled() {
        return enable;
    }

    public boolean isAutoRunTestsEnabled() {
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
