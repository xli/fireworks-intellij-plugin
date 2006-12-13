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
package com.thoughtworks.fireworks.core.tree;

import com.thoughtworks.fireworks.core.ConsoleViewAdaptee;
import com.thoughtworks.shadow.Utils;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ConsoleViewBuffer implements ConsoleViewAdaptee {
    private final PrintWriter writer;

    public ConsoleViewBuffer(StringWriter writer) {
        this.writer = new PrintWriter(writer);
    }

    public JComponent getComponent() {
        return null;
    }

    public void clearAndPrint(String text) {
        if (Utils.isEmpty(text)) {
            return;
        }
        writer.println(text);
    }

    public void dispose() {
    }

    public void clear() {
    }

    public void printlnSystemOutput(String message) {
    }

    public void printlnErrorOutput(String message) {
    }
}
