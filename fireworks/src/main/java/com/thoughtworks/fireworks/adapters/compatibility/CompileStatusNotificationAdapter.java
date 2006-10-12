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
package com.thoughtworks.fireworks.adapters.compatibility;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileStatusNotification;
import com.thoughtworks.fireworks.core.CompileStatusNotificationAdaptee;

public class CompileStatusNotificationAdapter implements CompileStatusNotification {
    private final CompileStatusNotificationAdaptee notification;

    public CompileStatusNotificationAdapter(CompileStatusNotificationAdaptee notification) {
        this.notification = notification;
    }

    public void finished(boolean aborted, int errors, int warnings) {
        notification.finished(aborted, errors, warnings);
    }

    /**
     * for CompileStatusNotification in IntelliJ IDEA 6
     *
     * @param aborted
     * @param errors
     * @param warnings
     * @param compileContext
     */
    public void finished(boolean aborted, int errors, int warnings, CompileContext compileContext) {
        finished(aborted, errors, warnings);
    }
}
