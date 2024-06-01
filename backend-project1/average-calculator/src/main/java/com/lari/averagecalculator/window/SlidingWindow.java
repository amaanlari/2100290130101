package com.lari.averagecalculator.window;

import java.util.LinkedList;
import java.util.List;

public class SlidingWindow {

    private final int windowSize;
    private final LinkedList<Integer> window;

    public SlidingWindow(int windowSize) {
        this.windowSize = windowSize;
        this.window = new LinkedList<>();
    }

    public List<Integer> updateWindow(List<Integer> newNumbers) {
        for (int num : newNumbers) {
            if (!window.contains(num)) {
                if (window.size() >= windowSize) {
                    window.removeFirst();
                }
                window.add(num);
            }
        }
        return window;
    }

    public List<Integer> getWindow() {
        return new LinkedList<>(window);
    }

    public double calculateAverage() {
        if (window.isEmpty()) {
            return 0.0;
        }
        return window.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }
    
}
