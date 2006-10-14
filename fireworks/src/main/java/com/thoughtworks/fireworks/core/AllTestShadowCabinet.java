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
package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.Sunshine;
import junit.framework.TestResult;

public class AllTestShadowCabinet implements TestCollection {
    private final ShadowCabinetListener listener = new RemoveSuccessfulTestShadow();
    private final IntellijShadowCabinet cabinet;
    private final FireworksConfig config;
    private int configMaxSize;

    public AllTestShadowCabinet(IntellijShadowCabinet cabinet, FireworksConfig config) {
        this.cabinet = cabinet;
        this.config = config;
    }

    public void init() {
        configMaxSize = config.maxSize();
        cabinet.addListener(listener);
    }

    public void add(Sunshine sunshine, String testClassName) {
        if (cabinet.size() == config.maxSize()) {
            config.setMaxSize(config.maxSize() + 1);
        }
        cabinet.addTestShadow(sunshine, testClassName);
    }

    public void action(TestResult result) {
        cabinet.action(result);
    }

    private class RemoveSuccessfulTestShadow implements ShadowCabinetListener {
        public void afterAddTest(ComparableTestShadow test) {
            test.addListener(new RemoveSelfIfSuccessTestStateListener());
        }

        public void afterRemoveTest(ComparableTestShadow test) {
        }

        public void endAction() {
            config.setMaxSize(Math.max(cabinet.size(), configMaxSize));
            cabinet.removeListener(this);
        }

        public void startAction() {
        }
    }
}