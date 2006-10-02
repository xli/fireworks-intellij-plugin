package com.thoughtworks.fireworks.adapters;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;

public class ProgressIndicatorUtils {
    public static void displayAsText2(String text2) {
        ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();
        if (progressIndicator != null && progressIndicator.isRunning()) {
            progressIndicator.setText2(text2);
        }
    }

    public static void displayAsText(String text) {
        ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();
        if (progressIndicator != null && progressIndicator.isRunning()) {
            progressIndicator.setText(text);
        }
    }
}
