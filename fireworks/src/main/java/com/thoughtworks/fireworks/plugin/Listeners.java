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

import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.thoughtworks.fireworks.adapters.DeleteTestShadowListener;
import org.picocontainer.Startable;

public class Listeners implements Startable {

    private final EditorFactoryListener editorFactoryListener;
    private final FileDocumentManagerListener fileDocumentManagerListener;
    private final DeleteTestShadowListener deleteTestShadowListener;

    public Listeners(EditorFactoryListener editorFactoryListener,
                     FileDocumentManagerListener fileDocumentManagerListener,
                     DeleteTestShadowListener deleteTestShadowListener) {
        this.editorFactoryListener = editorFactoryListener;
        this.fileDocumentManagerListener = fileDocumentManagerListener;
        this.deleteTestShadowListener = deleteTestShadowListener;
    }

    public void start() {
        editorFactory().addEditorFactoryListener(editorFactoryListener);
        fileDocumentManager().addFileDocumentManagerListener(fileDocumentManagerListener);
        VirtualFileManager.getInstance().addVirtualFileListener(deleteTestShadowListener);
    }

    public void stop() {
        editorFactory().removeEditorFactoryListener(editorFactoryListener);
        fileDocumentManager().removeFileDocumentManagerListener(fileDocumentManagerListener);
        VirtualFileManager.getInstance().removeVirtualFileListener(deleteTestShadowListener);
    }

    private FileDocumentManager fileDocumentManager() {
        return FileDocumentManager.getInstance();
    }

    private EditorFactory editorFactory() {
        return EditorFactory.getInstance();
    }
}
