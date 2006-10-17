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

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.thoughtworks.fireworks.core.developer.Thought;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class LiveTemplateAdapter implements Thought {

    private final static Logger LOG = Logger.getLogger(LiveTemplateAdapter.class);

    public static final String TEMPLATE_MANAGER_IMPL_CLASS_NAME = "com.intellij.codeInsight.template.impl.TemplateManagerImpl";
    public static final String GET_TEMPLATE_STATE_METHOD_NAME = "getTemplateState";

    private final Method getTemplateStateMethod;

    public LiveTemplateAdapter() {
        this.getTemplateStateMethod = getTemplateStateMethod();
    }

    public boolean isWorking() {
        Editor activeEditor = getActiveEditor();
        if (activeEditor == null) {
            return false;
        }

        return thereIsLiveTemplateWorkingInActiveEditor(activeEditor);
    }

    private boolean thereIsLiveTemplateWorkingInActiveEditor(Editor activeEditor) {
        try {
            Object templateState = getLiveTemplateState(activeEditor);
            if (templateState == null) {
                return Boolean.FALSE;
            }
            return templateStateIsNotFinished(templateState);
        } catch (Exception e) {
            LOG.error("unexpected exception", e);
            return false;
        }
    }

    private Editor getActiveEditor() {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();
        for (int i = 0; i < editors.length; i++) {
            Editor editor = editors[i];
            if (!editor.isViewer() && editor.getContentComponent().isFocusOwner()) {
                return editor;
            }
        }
        return null;
    }

    private Object getLiveTemplateState(Editor editor) throws Exception {
        return getTemplateStateMethod.invoke(null, new Object[]{editor});
    }

    private boolean templateStateIsNotFinished(Object templateState) throws Exception {
        return isFalse(isFinished(templateState).invoke(templateState, new Object[0]));
    }

    private boolean isFalse(java.lang.Object obj) {
        return Boolean.FALSE.equals(obj);
    }

    private Method isFinished(Object templateState) throws NoSuchMethodException {
        return templateState.getClass().getMethod("isFinished", new Class[0]);
    }

    private Class<?> getTemplateManagerClass() throws ClassNotFoundException {
        return Class.forName(TEMPLATE_MANAGER_IMPL_CLASS_NAME);
    }

    private Method getTemplateStateMethod() {
        try {
            return getTemplateManagerClass().getMethod(GET_TEMPLATE_STATE_METHOD_NAME, new Class[]{Editor.class});
        } catch (Exception e) {
            LOG.error("unexpected exception, can't find the method of 'getTemplateStateMethod'.", e);
            return null;
        }
    }

}
