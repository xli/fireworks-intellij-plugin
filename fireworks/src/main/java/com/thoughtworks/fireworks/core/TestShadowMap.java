package com.thoughtworks.fireworks.core;

import com.thoughtworks.shadow.Sunshine;

public interface TestShadowMap {
    void addTestShadow(Sunshine sunshine, String testClassName);

    void setMaxSize(int maxSize);

    void removeTestShadow(Sunshine sunshine, String testClassName);
}
