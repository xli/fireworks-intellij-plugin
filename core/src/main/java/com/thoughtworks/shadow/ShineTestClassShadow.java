/*
 *    Copyright (c) 2006 LiXiao
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
package com.thoughtworks.shadow;

import junit.framework.Test;
import junit.framework.TestResult;

public class ShineTestClassShadow implements Test {
    private final String testClassName;
    private final Sunshine sunshine;

    public ShineTestClassShadow(String testClassName, Sunshine sunshine) {
        this.testClassName = testClassName;
        this.sunshine = sunshine;
    }

    public int countTestCases() {
        return sunshine.shine(testClassName).countTestCases();
    }

    public void run(TestResult result) {
        sunshine.shine(testClassName).run(result);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ShineTestClassShadow that = (ShineTestClassShadow) o;

        return testClassName.equals(that.testClassName);
    }

    public int hashCode() {
        return testClassName.hashCode();
    }

    public String toString() {
        int index = testClassName.lastIndexOf(".");
        if (index < 0) {
            return testClassName;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(testClassName.substring(index + 1));
        buffer.append("(");
        buffer.append(testClassName.substring(0, index));
        buffer.append(")");
        return buffer.toString();
    }

}
