package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList list = new ImmutableLinkedList();

    public Queue() {

    }

    Object peek() {
        return list.getLast();
    }

    public Object dequeue() {
        Object res = peek();
        list = list.removeLast();
        return res;
    }

    public void enqueue(Object e) {
        list = list.addFirst(e);
    }

    //TODO
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
