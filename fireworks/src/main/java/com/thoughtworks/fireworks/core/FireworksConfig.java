package com.thoughtworks.fireworks.core;

public interface FireworksConfig {
    String maxMemory();

    String expectedTestCaseNameRegex();

    int maxSize();

    int autoRunTestsDelayTime();

    void setMaxSize(int maxSize);
}
