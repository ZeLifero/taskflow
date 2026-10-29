package com.taskflow.algo;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Minimal generic singly linked list: shows generics, inner classes and Iterable. */
public class SimpleLinkedList<T> implements Iterable<T> {

    private static class Node<T> {
        final T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null) tail = node;
        size++;
    }

    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index + ", size " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.value;
    }

    /** Reverses the list in place, O(n). */
    public void reverse() {
        Node<T> prev = null;
        Node<T> current = head;
        tail = head;
        while (current != null) {
            Node<T> next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
