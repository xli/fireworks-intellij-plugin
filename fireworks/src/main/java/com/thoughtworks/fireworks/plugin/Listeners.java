package com.thoughtworks.fireworks.plugin;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFileManager;
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
//        Toolkit.getDefaultToolkit().addAWTEventListener(documentListener, AWTEvent.MOUSE_EVENT_MASK);

        EditorFactory.getInstance().getEventMulticaster().addDocumentListener(documentListener);

        VirtualFileManager.getInstance().addVirtualFileListener(deleteTestShadowListener);
    }

    public void stop() {
        editorFactory().removeEditorFactoryListener(editorFactoryListener);
        fileDocumentManager().removeFileDocumentManagerListener(fileDocumentManagerListener);

        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);

        EditorFactory.getInstance().getEventMulticaster().removeDocumentListener(documentListener);
//        ApplicationManager.getApplication().invokeLater(removeDocumentListenerFromEditors());

        VirtualFileManager.getInstance().removeVirtualFileListener(deleteTestShadowListener);
    }

    /*
     * 为什么以前需要这个方法？现在却发现不需要removeDocumentListener，而且如果重复添加相同的documentListener会有Logger#assert fail
     */
    private Runnable removeDocumentListenerFromEditors() {
        return new Runnable() {
            public void run() {
                Editor[] allEditors = EditorFactory.getInstance().getAllEditors();
                for (int i = 0; i < allEditors.length; i++) {
                    System.out.println("editor: " + allEditors[i].getDocument().getText());
                    boolean isNonDisposedDocEditor = !allEditors[i].isViewer() && !allEditors[i].isDisposed();
                    if (isNonDisposedDocEditor && allEditors[i].getDocument().isWritable()) {
                        System.out.println("remove doc listener");
                        try {
                            allEditors[i].getDocument().removeDocumentListener(documentListener);
                        } catch (Throwable e) {
                            System.out.println("xx: " + e);
                        }
                    }
                }
            }
        };
    }

    private FileDocumentManager fileDocumentManager() {
        return FileDocumentManager.getInstance();
    }

    private EditorFactory editorFactory() {
        return EditorFactory.getInstance();
    }
}
