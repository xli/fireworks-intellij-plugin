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

import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MyGMail {

    private static final Logger LOG = Logger.getLogger(MyGMail.class);

    public static void main(String[] args) {
        new MyGMail(true).send("message");
    }

    public static void sendToLiXiao(String message) {
        new MyGMail(false).send(message);
    }

    private final String user = "dont.make.me.test";
    private final String password = "d0ntmakemet1st";

    private final String to = "swing1979@gmail.com";
    private final String from = user + "@gmail.com";
    private final String host = "smtp.gmail.com";
    private final String subject = "Feedbacks of fireworks";

    private Session session;

    public MyGMail(boolean debug) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.user", user);
        props.put("mail.from", from);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", debug);

        session = Session.getInstance(props);
        session.setDebug(debug);
    }

    public void send(String message) {
        try {
            send(createMessage(message));
        } catch (MessagingException e) {
            LOG.error("Send message '" + message + "' failed.", e);
        }
    }

    private void send(Message msg) throws MessagingException {
        Transport transport = session.getTransport("smtp");
        transport.connect(host, user, password);
        transport.sendMessage(msg, msg.getAllRecipients());
    }

    private Message createMessage(String message) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);
        return msg;
    }
}
