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
package com.thoughtworks.fireworks.mail;

import java.awt.*;

public class MailAction {
    private final Window window;
    private final String message;

    public MailAction(Window window, String message) {
        this.window = window;
        this.message = message;
    }

    public void execute() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                MyGMail.sendToLiXiao(message);
            }
        });
        thread.start();
        window.setVisible(false);
        window.dispose();
    }
}
