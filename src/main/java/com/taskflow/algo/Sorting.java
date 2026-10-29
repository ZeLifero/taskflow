package com.taskflow.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Hand-written stable merge sort, O(n log n). Used by the sort strategies. */
public final class Sorting {

    private Sorting() {
    }

    public static <T> List<T> mergeSort(List<T> input, Comparator<? super T> cmp) {
        if (input.size() <= 1) {
            return new ArrayList<>(input);
        }
        int mid = input.size() / 2;
        List<T> left = mergeSort(input.subList(0, mid), cmp);
        List<T> right = mergeSort(input.subList(mid, input.size()), cmp);
        return merge(left, right, cmp);
    }

    private static <T> List<T> merge(List<T> left, List<T> right, Comparator<? super T> cmp) {
        List<T> result = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            // "<=" keeps equal elements in original order -> stable sort
            if (cmp.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        return result;
    }
}
