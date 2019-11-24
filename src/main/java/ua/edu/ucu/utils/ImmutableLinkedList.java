package ua.edu.ucu.utils;

public class ImmutableLinkedList implements ImmutableList {

    private final Node head;

    private class Node {
        private Object data;
        private Node next;

        private Node(Object data) {
            this.data = data;
            next = null;
        }

        private Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        private Node copy() {
            if (next == null) {
                return new Node(data);
            } else {
                return new Node(data, next.copy());
            }
        }

        private void setNext(Node newNext) {
            newNext.next = this.next;
            this.next = newNext;
        }
    }

    public ImmutableLinkedList() {
        head = null;
    }

    public ImmutableLinkedList(Node head) {
        this.head = head;
    }

    private static Node getNodeByIndex(Node head, int index) {
        if (head == null) {
            throw new IndexOutOfBoundsException("Data is empty!");
        }
        Node pointer = head;
        for (int i = 0; i != index; i++) {
            pointer = pointer.next;
            if (pointer == null) {
                throw new IndexOutOfBoundsException("Out of boundaries");
            }
        }
        return pointer;
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size() - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size() - 1);
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size(), e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative!");
        }
        if (index > size()) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
        if (this.head == null) {
            return new ImmutableLinkedList(new Node(e));
        } else {
            Node newHead = this.head.copy();
            if (index == 0) {
                return new ImmutableLinkedList(new Node(e, newHead));
            } else {
                getNodeByIndex(newHead, index - 1).setNext(new Node(e));
                return new ImmutableLinkedList(newHead);
            }
        }
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        ImmutableLinkedList l = this;
        for (Object x : c) {
            l = l.add(x);
        }
        return l;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        ImmutableLinkedList l = this;
        for (int i = 0; i < c.length; i++) {
            l = l.add(index + i, c[i]);
        }
        return l;
    }

    @Override
    public Object get(int index) {
        return getNodeByIndex(this.head, index).data;
    }

    @Override
    public ImmutableLinkedList remove(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative!");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }

        if (size() == 1) {
            return new ImmutableLinkedList();
        } else {
            Node newHead = this.head.copy();
            if (index == 0) {
                return new ImmutableLinkedList(newHead.next);
            } else { //remove element from new list
                Node prev = getNodeByIndex(newHead, index - 1);
                prev.next = prev.next.next;
                return new ImmutableLinkedList(newHead);
            }
        }
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative!");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }

        Node newHead = this.head.copy();
        Node n = getNodeByIndex(newHead, index);
        n.data = e;
        return new ImmutableLinkedList(newHead);
    }

    @Override
    public int indexOf(Object e) {
        int index = 0;
        Node pointer = this.head;
        while (pointer != null && !pointer.data.equals(e)) {
            pointer = pointer.next;
            index++;
        }
        if (pointer == null) {
            return -1;
        } else {
            return index;
        }
    }

    @Override
    public int size() {
        int count = 0;
        Node pointer = this.head;
        while (pointer != null) {
            pointer = pointer.next;
            count++;
        }
        return count;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size()];
        int index = 0;
        Node pointer = this.head;
        while (pointer != null) {
            array[index] = pointer.data;
            pointer = pointer.next;
            index++;
        }
        return array;
    }

    @Override
    public String toString() {
        if (this.head == null) {
            return "";
        } else {
            StringBuilder res = new StringBuilder("");
            Node current = this.head;
            while (current != null) {
                res.append(current.data);
                if (current.next != null) {
                    res.append(", ");
                }
                current = current.next;
            }
            return res.toString();
        }
    }

}
