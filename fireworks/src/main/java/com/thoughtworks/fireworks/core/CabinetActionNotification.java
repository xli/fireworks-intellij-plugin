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

import com.intellij.openapi.compiler.CompileStatusNotification;
import com.thoughtworks.shadow.Cabinet;
import junit.framework.TestResult;

public class CabinetActionNotification implements CompileStatusNotification {
    private final Cabinet cabinet;
    private final TestResult result;

    public CabinetActionNotification(Cabinet cabinet, TestResult result) {
        this.cabinet = cabinet;
        this.result = result;
    }

    public void finished(boolean aborted, int errors, int warnings) {
        if (aborted || errors > 0) {
            return;
        }
        cabinet.action(result);
    }
}
