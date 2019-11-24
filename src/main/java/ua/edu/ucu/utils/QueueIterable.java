package ua.edu.ucu.utils;

import java.util.Iterator;

public class QueueIterable implements Iterable<String> {

    private final Queue<String> queue;
    private final Iterator<String> iterator = new Iterator<String>() {
        @Override
        public boolean hasNext() {
            return ! queue.empty();
        }

        @Override
        public String next() {
            return queue.dequeue();
        }
    };

    public QueueIterable( final Queue<String> queue ) {
        this.queue = queue;
    }

    public Iterator<String> iterator() {
        return iterator;
    }

}
