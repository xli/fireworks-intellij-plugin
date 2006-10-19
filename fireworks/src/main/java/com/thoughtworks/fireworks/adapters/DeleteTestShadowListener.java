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

import com.intellij.openapi.vfs.VirtualFileEvent;
import com.thoughtworks.fireworks.core.TestShadowMap;
import com.thoughtworks.shadow.Sunshine;

import java.io.FileNotFoundException;

public class DeleteTestShadowListener extends com.intellij.openapi.vfs.VirtualFileAdapter {
    private final TestShadowMap testShadowMap;
    private final ProjectAdapter project;

    public DeleteTestShadowListener(TestShadowMap testShadowMap, ProjectAdapter project) {
        this.testShadowMap = testShadowMap;
        this.project = project;
    }

    public void beforeFileDeletion(final VirtualFileEvent event) {
        VirtualFileAdaptee file;
        try {
            file = VirtualFileAdapter.getFile(event.getFile(), project);
        } catch (FileNotFoundException e) {
            return;
        }
        if (file.isInSourceOrTestContent()) {
            Sunshine sunshine = file.createSunshine();
            String className = file.getJavaFileClassName();
            testShadowMap.removeTestShadow(sunshine, className);
        }
    }
}
