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

import com.intellij.openapi.progress.ProgressIndicator;
import com.thoughtworks.fireworks.core.ResultOfTestEndListener;
import com.thoughtworks.shadow.*;
import com.thoughtworks.shadow.junit.ErrorTestCase;
import com.thoughtworks.shadow.junit.ProtectableFactory;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class TestsRunningProgressIndicatorAdapter implements ResultOfTestEndListener, ShadowCabinetListener, RunListenerAdaptee, ShadowVisitor {

    private static class CancelableSunshine implements Sunshine {

        private Test test;
        private Object key = new Object();
        private final Sunshine sunshine;

        public CancelableSunshine(Sunshine sunshine) {
            this.sunshine = sunshine;
        }

        public Test shine(final String testClassName) {
            ProgressIndicatorUtils.displayAsText2(testClassName);
            final ProgressIndicator pi = ProgressIndicatorUtils.getProgressIndicator();
            final Thread shiningThread = new Thread(new Runnable() {
                public void run() {
                    testShined(sunshine.shine(testClassName));
                    notifyWaitingThread();
                }
            });

            Thread monitorThread = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (pi.isRunning() && pi.isCanceled()) {
                            shiningThread.interrupt();
                            notifyWaitingThread();
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });

            monitorThread.start();
            shiningThread.start();

            waitUntilTestShined();

            synchronized (monitorThread) {
                if (monitorThread.isAlive()) {
                    monitorThread.interrupt();
                }
            }

            if (test == null) {
                Exception ie = new InterruptedException("Running test class(" + testClassName + ") is interrupted!");
                return new ErrorTestCase(testClassName, ProtectableFactory.protectable(ie));
            }

            return test;
        }

        private void testShined(Test test) {
            this.test = test;
        }

        private void waitUntilTestShined() {
            synchronized (key) {
                try {
                    key.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void notifyWaitingThread() {
            synchronized (key) {
                key.notify();
            }
        }
    }


    private TestResultStatus status;
    private String testClassName;
    private String testMethodName;

    public void testEnd(TestShadowResult result) {
        status = new TestResultStatus(result);
        displaySummaryAsText();
        if (ProgressIndicatorUtils.isCanceled()) {
            result.stop();
        }
    }

    public void afterAddTest(ComparableTestShadow test) {
    }

    public void afterRemoveTest(ComparableTestShadow test) {
    }

    public void endAction() {
        displaySummaryAsText();
    }

    public void startAction() {
        status = new TestResultStatus(0, 0, 0, 0);
        displaySummaryAsText();
    }

    public void addError(Test test, Throwable t) {
        ProgressIndicatorUtils.displayAsText2("error: " + t.getMessage());
    }

    public void addFailure(Test test, AssertionFailedError t) {
        ProgressIndicatorUtils.displayAsText2("failure: " + t.getMessage());
    }

    public void testIgnored(TestShadow testShadow) {
        ProgressIndicatorUtils.displayAsText2("ignored");
    }

    public void endTest(Test test) {
    }

    public void startTest(Test test) {
        ((Shadow) test).accept(this);
    }

    public void visitTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public void visitTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public void end() {
        ProgressIndicatorUtils.displayAsText2("Running: " + testClassName + "#" + testMethodName);
    }

    public Sunshine decorate(final Sunshine sunshine) {
        return new CancelableSunshine(sunshine);
    }

    private void displaySummaryAsText() {
        ProgressIndicatorUtils.displayAsText(status.summary());
    }

}
