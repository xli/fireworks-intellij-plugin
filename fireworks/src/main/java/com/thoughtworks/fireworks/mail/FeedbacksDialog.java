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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FeedbacksDialog extends JDialog {

    public static final void main(String[] args) {
        new FeedbacksDialog().setVisible(true);
    }

    public FeedbacksDialog() {
        setTitle("Feedbacks for Fireworks");
        setModal(true);
        setLocation(100, 100);
        setSize(700, 400);

        add(createPanel());
    }

    private JPanel createPanel() {
        String[] quickMessages = quickMessages();
        JPanel pane = new JPanel(new GridLayout(quickMessages.length + 1, 1, 10, 10));
        pane.setBorder(BorderFactory.createTitledBorder(title()));
        for (int i = 0; i < quickMessages.length; i++) {
            pane.add(new MailButton(this, quickMessages[i]));
        }
        pane.add(messagePanel());
        return pane;
    }

    private String title() {
        return "You can contact me by email(swing1979@gmial.com), " +
                "or just click the following buttons to send me a quick message:";
    }

    private String[] quickMessages() {
        return new String[]{
                "I love it.",
                "It's ok, I use it sometimes.",
                "Idea is cool, but it works bad.",
                "The task of auto run test is always triggered at wrong time.",
                "I like to run test by hotkeys.",
                "It works bad, just wastes my time! I'll remove it."
        };
    }

    private Component messagePanel() {
        JTextArea textArea = createTextArea();

        JPanel pane = new JPanel(new BorderLayout());
        pane.add(textArea, BorderLayout.CENTER);
        pane.add(createSendMessageButton(textArea), BorderLayout.EAST);

        return pane;
    }

    private JButton createSendMessageButton(final JTextArea textArea) {
        JButton sendButton = new JButton("send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MailAction(FeedbacksDialog.this, textArea.getText()).execute();
            }
        });
        return sendButton;
    }

    private JTextArea createTextArea() {
        final JTextArea textArea = new JTextArea();
        textArea.setText("Type in your comment and send to me:)");
        textArea.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                textArea.selectAll();
            }
        });
        return textArea;
    }
}
