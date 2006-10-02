package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.ComparableTestShadow;
import com.thoughtworks.shadow.ShadowCabinetListener;
import com.thoughtworks.shadow.Sunshine;
import junit.framework.TestResult;

public class AllTestShadowCabinet {
    private final ShadowCabinetListener listener = new RemoveSuccessfulTestShadow();
    private final IntellijShadowCabinet cabinet;
    private final FireworksConfig config;
    private int configMaxSize;

    public AllTestShadowCabinet(IntellijShadowCabinet cabinet, FireworksConfig config) {
        this.cabinet = cabinet;
        this.config = config;
    }

    public void init() {
        configMaxSize = config.maxSize();
        cabinet.addListener(listener);
    }

    public void addTestShadow(Sunshine sunshine, String testClassName) {
        if (cabinet.size() == config.maxSize()) {
            config.setMaxSize(config.maxSize() + 1);
        }
        cabinet.addTestShadow(sunshine, testClassName);
    }

    public void action(TestResult result) {
        cabinet.action(result);
    }

    private class RemoveSuccessfulTestShadow implements ShadowCabinetListener {
        public void afterAddTest(ComparableTestShadow test) {
            test.addListener(new RemoveSelfIfSuccessTestStateListener());
        }

        public void afterRemoveTest(ComparableTestShadow test) {
        }

        public void endAction() {
            config.setMaxSize(Math.max(cabinet.size(), configMaxSize));
            cabinet.removeListener(this);
        }

        public void startAction() {
        }
    }
}