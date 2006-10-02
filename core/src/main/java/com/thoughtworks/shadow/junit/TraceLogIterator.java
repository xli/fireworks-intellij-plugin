package com.thoughtworks.shadow.junit;

public class TraceLogIterator {
    private final String[] array;
    private final boolean isFailures;
    private int index = 1;

    public TraceLogIterator(String[] array, boolean isFailures) {
        this.array = array;
        this.isFailures = isFailures;
    }

    public boolean hasNext() {
        return index < array.length;
    }

    public TestTraceLog next() {
        String next = array[index++];
        if (isFailures) {
            return new FailureTestTraceLog(next);
        }
        return new ErrorTestTraceLog(next);
    }
}
