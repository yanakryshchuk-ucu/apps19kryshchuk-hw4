package ua.edu.ucu.utils;

import java.util.NoSuchElementException;

public class Queue<T> {
    private ImmutableLinkedList array;

    public Queue() {
        array = new ImmutableLinkedList();
    }

    public boolean empty() {
        return array.isEmpty();
    }

    public T peek() {
        try {
            return (T) array.getFirst();
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public T dequeue() throws NoSuchElementException {
        try {
            Object res = array.getFirst();
            array = array.removeFirst();
            return (T) res;
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public void enqueue(T element) {
        array = array.addLast(element);
    }

}
