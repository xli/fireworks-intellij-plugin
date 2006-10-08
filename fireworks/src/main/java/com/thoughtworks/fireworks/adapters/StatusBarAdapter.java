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

import com.thoughtworks.fireworks.core.ResultOfTestEndListener;
import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.TestResultStatus;
import com.thoughtworks.shadow.TestShadowResult;

public class StatusBarAdapter implements ResultOfTestEndListener, ShadowCabinetListener {
    private final ProjectAdapter project;
    private TestResultStatus status;

    public StatusBarAdapter(ProjectAdapter project) {
        this.project = project;
    }

    public void testEnd(TestShadowResult result) {
        status = new TestResultStatus(result);
        project.setStatusBarInfo(status.summary());
    }

    public void afterAddTest(ComparableTestShadow test) {
        project.setStatusBarInfo("Added \"" + test + "\" into Fireworks");
    }

    public void afterRemoveTest(ComparableTestShadow test) {
        project.setStatusBarInfo("Removed \"" + test + "\" from Fireworks");
    }

    public void endAction() {
        project.setStatusBarInfo(status.summary());
    }

    public void startAction() {
        status = new TestResultStatus(0, 0, 0, 0);
        project.setStatusBarInfo("Bang went the fireworks.");
    }
}
