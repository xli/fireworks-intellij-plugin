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
package com.thoughtworks.shadow.junit;

import com.thoughtworks.shadow.ShadowVisitor;
import com.thoughtworks.shadow.TestShadow;
import com.thoughtworks.shadow.Utils;
import junit.framework.Protectable;
import junit.framework.TestResult;

public class BaseTestCase implements TestShadow {
    private final Protectable protectable;
    protected final String testClassName;
    protected final String testMethodName;

    public BaseTestCase(String testClassName, String testMethodName, Protectable protectable) {
        this.testClassName = testClassName;
        this.testMethodName = testMethodName;
        this.protectable = protectable;
    }

    public void accept(ShadowVisitor visitor) {
        visitor.visitTestMethodName(testMethodName);
        visitor.visitTestClassName(testClassName);
        visitor.end();
    }

    public void run(TestResult result) {
        result.startTest(this);
        result.runProtected(this, protectable);
        result.endTest(this);
    }

    public int countTestCases() {
        return 1;
    }

    public String toString() {
        if (testMethodName == null) {
            return testClassName;
        }
        return testMethodName + "(" + testClassName + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (!(o instanceof BaseTestCase))) return false;

        final BaseTestCase that = (BaseTestCase) o;

        return Utils.isEqual(testMethodName, that.testMethodName) && Utils.isEqual(testClassName, that.testClassName);
    }

    public int hashCode() {
        int result;
        result = testClassName.hashCode();
        result = 29 * result + (testMethodName != null ? testMethodName.hashCode() : 0);
        return result;
    }
}
