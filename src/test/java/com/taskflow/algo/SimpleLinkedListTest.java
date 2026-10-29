package com.taskflow.algo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleLinkedListTest {

    @Test
    void addsAndReadsElements() {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        list.addLast("b");
        list.addLast("c");
        list.addFirst("a");
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("c", list.get(2));
    }

    @Test
    void reverseChangesOrderAndKeepsListUsable() {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        for (int i = 1; i <= 3; i++) list.addLast(i);
        list.reverse();
        list.addLast(0); // tail must still be correct after reverse

        List<Integer> result = new ArrayList<>();
        for (int v : list) result.add(v);
        assertEquals(List.of(3, 2, 1, 0), result);
    }

    @Test
    void getThrowsOnBadIndex() {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }
}
