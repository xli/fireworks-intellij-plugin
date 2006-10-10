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

public class SimpleCompilerManagerAdapter implements CompilerManagerAdaptee {
    private final boolean abort;
    private final int errors;
    private final int warnings;

    public SimpleCompilerManagerAdapter(boolean abort, int errors, int warnings) {
        this.abort = abort;
        this.errors = errors;
        this.warnings = warnings;
    }

    public SimpleCompilerManagerAdapter() {
        this(false, 0, 0);
    }

    public void compile(CompileStatusNotificationAdaptee compileStatusNotification) {
        compileStatusNotification.finished(abort, errors, warnings);
    }
}
