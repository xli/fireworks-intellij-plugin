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

import com.thoughtworks.shadow.Shadow;
import com.thoughtworks.shadow.ShadowVisitor;

public class ShadowStrDecorator implements Shadow {
    private final Shadow shadow;
    private final String str;

    public ShadowStrDecorator(Shadow shadow, String str) {
        this.shadow = shadow;
        this.str = str;
    }

    public void accept(ShadowVisitor visitor) {
        shadow.accept(visitor);
    }

    public String toString() {
        return str;
    }
}
