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
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ApplicationListener;
import com.thoughtworks.fireworks.adapters.DeleteTestShadowListener;
import org.picocontainer.Startable;

import java.awt.*;
import java.awt.event.AWTEventListener;

public class Listeners implements Startable {

    private final EditorFactoryListener editorFactoryListener;
    private final FileDocumentManagerListener fileDocumentManagerListener;
    private final DocumentListener documentListener;
    private final AWTEventListener awtListener;
    private final DeleteTestShadowListener deleteTestShadowListener;

    public Listeners(EditorFactoryListener editorFactoryListener,
                     FileDocumentManagerListener fileDocumentManagerListener,
                     DocumentListener documentListener,
                     AWTEventListener awtListener,
                     DeleteTestShadowListener deleteTestShadowListener) {
        this.editorFactoryListener = editorFactoryListener;
        this.fileDocumentManagerListener = fileDocumentManagerListener;
        this.documentListener = documentListener;
        this.awtListener = awtListener;
        this.deleteTestShadowListener = deleteTestShadowListener;
    }

    public void start() {
        editorFactory().addEditorFactoryListener(editorFactoryListener);
        fileDocumentManager().addFileDocumentManagerListener(fileDocumentManagerListener);

        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_EVENT_MASK);

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(documentListener);
        VirtualFileManager.getInstance().addVirtualFileListener(deleteTestShadowListener);
    }

    public void stop() {
        editorFactory().removeEditorFactoryListener(editorFactoryListener);
        fileDocumentManager().removeFileDocumentManagerListener(fileDocumentManagerListener);

        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);

        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(documentListener);
        VirtualFileManager.getInstance().removeVirtualFileListener(deleteTestShadowListener);
    }

    private FileDocumentManager fileDocumentManager() {
        return FileDocumentManager.getInstance();
    }

    private EditorFactory editorFactory() {
        return EditorFactory.getInstance();
    }
}
