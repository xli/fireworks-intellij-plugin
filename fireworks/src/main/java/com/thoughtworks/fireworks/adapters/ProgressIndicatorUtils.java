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

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;

public class ProgressIndicatorUtils {
    public static void displayAsText2(String text2) {
        if (isRunning()) {
            getProgressIndicator().setText2(text2);
        }
    }

    public static void displayAsText(String text) {
        if (isRunning()) {
            getProgressIndicator().setText(text);
        }
    }

    public static boolean isRunning() {
        return getProgressIndicator() != null && getProgressIndicator().isRunning();
    }

    public static boolean isCanceled() {
        if (isRunning()) {
            return getProgressIndicator().isCanceled();
        }
        return false;
    }

    private static ProgressIndicator getProgressIndicator() {
        return ProgressManager.getInstance().getProgressIndicator();
    }
}
