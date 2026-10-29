package com.taskflow.algo;

import java.util.List;

public final class Searching {

    private Searching() {
    }

    /** Binary search in a sorted list, O(log n). Returns the index or -1 if not found. */
    public static <T extends Comparable<? super T>> int binarySearch(List<T> sorted, T target) {
        int low = 0;
        int high = sorted.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2; // avoids int overflow
            int c = sorted.get(mid).compareTo(target);
            if (c == 0) return mid;
            if (c < 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }
}
