package com.thoughtworks.shadow.junit;

import junit.framework.Protectable;

public class SuccessfulTestCase extends BaseTestCase {
    public SuccessfulTestCase() {
        this("Successful Test");
    }

    public SuccessfulTestCase(String testClassName) {
        this(testClassName, null);
    }

    public SuccessfulTestCase(String testClassName, String testMethodName) {
        super(testClassName, testMethodName, new Protectable() {
            public void protect() throws Throwable {
            }
        });
    }

}
