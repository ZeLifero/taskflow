package com.taskflow.algo;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortingTest {

    @Test
    void sortsNumbersAscending() {
        assertEquals(List.of(1, 2, 3, 5, 8), Sorting.mergeSort(List.of(5, 2, 8, 1, 3), Comparator.naturalOrder()));
    }

    @Test
    void handlesEmptyAndSingleElement() {
        assertEquals(List.of(), Sorting.mergeSort(List.of(), Comparator.<Integer>naturalOrder()));
        assertEquals(List.of(7), Sorting.mergeSort(List.of(7), Comparator.<Integer>naturalOrder()));
    }

    @Test
    void isStable() {
        // sort by length only: equal-length words must keep their original order
        List<String> result = Sorting.mergeSort(List.of("bb", "aa", "c", "dd"), Comparator.comparingInt(String::length));
        assertEquals(List.of("c", "bb", "aa", "dd"), result);
    }

    @Test
    void doesNotModifyInput() {
        List<Integer> input = List.of(3, 1, 2);
        Sorting.mergeSort(input, Comparator.naturalOrder());
        assertEquals(List.of(3, 1, 2), input);
    }
}
