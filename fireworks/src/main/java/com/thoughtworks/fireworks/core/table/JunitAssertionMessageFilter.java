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
package com.thoughtworks.fireworks.core.table;

import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;

public class JunitAssertionMessageFilter {

    private final static String[] MESSAGES = new String[]{
            AssertionFailedError.class.getName(),
            ComparisonFailure.class.getName()
    };

    public String doFilter(String message) {
        if (message == null) {
            return "";
        }

        for (int i = 0; i < MESSAGES.length; i++) {
            if (message.startsWith(MESSAGES[i]) && message.length() > MESSAGES[i].length()) {
                return message.substring(MESSAGES[i].length());
            }
        }
        return message;
    }
}
