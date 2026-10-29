package com.taskflow.algo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchingTest {

    private final List<Integer> data = List.of(1, 3, 5, 7, 9, 11);

    @Test
    void findsExistingElement() {
        assertEquals(0, Searching.binarySearch(data, 1));
        assertEquals(3, Searching.binarySearch(data, 7));
        assertEquals(5, Searching.binarySearch(data, 11));
    }

    @Test
    void returnsMinusOneWhenMissing() {
        assertEquals(-1, Searching.binarySearch(data, 4));
        assertEquals(-1, Searching.binarySearch(List.<Integer>of(), 4));
    }
}
