package com.thoughtworks.fireworks.core;

import junit.framework.Test;
import com.thoughtworks.shadow.Sunshine;
import com.thoughtworks.shadow.junit.ErrorTestCase;
import com.thoughtworks.shadow.junit.ProtectableFactory;

public class CancelableSunshine implements Sunshine {

    private static boolean canceled = false;

    public synchronized static void cancel() {
        canceled = true;
    }
    public synchronized static void clearCancel() {
        canceled = false;
    }
    public synchronized static boolean canceled() {
        return canceled;
    }

    private Test test;
    private Object key = new Object();
    private final Sunshine sunshine;

    public CancelableSunshine(Sunshine sunshine) {
        this.sunshine = sunshine;
    }

    public Test shine(final String testClassName) {
        final Thread shiningThread = new Thread(new Runnable() {
            public void run() {
                testShined(sunshine.shine(testClassName));
                notifyWaitingThread();
            }
        });

        Thread monitorThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (CancelableSunshine.canceled()) {
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

        CancelableSunshine.clearCancel();
        
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
