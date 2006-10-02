package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.*;
import junit.framework.TestResult;

public class IntellijShadowCabinet implements TestShadowMap, Cabinet {
    private final ShadowCabinet shadowCabinet = new ShadowCabinet();
    private final CompilerManagerAdaptee compilerManager;
    private final TestStateListener stateListener;

    public IntellijShadowCabinet(ShadowCabinetListener[] listeners,
                                 CompilerManagerAdaptee compilerManager,
                                 TestStateListener stateListener) {
        this.compilerManager = compilerManager;
        this.stateListener = stateListener;
        for (int i = 0; i < listeners.length; i++) {
            shadowCabinet.addListener(listeners[i]);
        }
    }

    public void action(final TestResult result) {
        compilerManager.compile(new CabinetActionNotification(shadowCabinet, result));
    }

    public void addTestShadow(Sunshine sunshine, String testClassName) {
        shadowCabinet.add(newComparableTestShadow(sunshine, testClassName));
    }

    public void setMaxSize(int maxSize) {
        shadowCabinet.setMaxSize(maxSize);
    }

    public void removeTestShadow(Sunshine sunshine, String testClassName) {
        shadowCabinet.remove(newComparableTestShadow(sunshine, testClassName));
    }

    private ComparableTestShadow newComparableTestShadow(Sunshine sunshine, String testClassName) {
        ComparableTestShadow shadow = new ComparableTestShadow(new ShineTestClassShadow(testClassName, sunshine), testClassName);
        shadow.addListener(stateListener);
        return shadow;
    }

    public int size() {
        return shadowCabinet.size();
    }

    public void addListener(ShadowCabinetListener listener) {
        shadowCabinet.addListener(listener);
    }

    public void removeListener(ShadowCabinetListener listener) {
        shadowCabinet.removeListener(listener);
    }
}
