package ua.edu.ucu.collections.immutable;


public final class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private int size;

    private static class Node {
        private final Object value;
        private Node next;

        private Node(Object value) {
            this.value = value;
        }

        private Object getValue() {
            return this.value;
        }

        private void setNext(Node nextNode) {
            this.next = nextNode;
        }

        public Node getNext() {
            return next;
        }
    }

    private ImmutableLinkedList(Node head, int size) {
        this.head = head;
        this.size = size;
    }


    public ImmutableLinkedList() {
        this.size = 0;
    }

    public ImmutableLinkedList(Object[] array) {
        if (array.length > 0) {
            this.head = new Node(array[0]);
            Node currNode = this.head;
            this.size = 1;

            for (int i = 1; i < array.length; i++) {
                Node node = new Node(array[i]);
                currNode.setNext(node);
                currNode = node;
                this.size++;
            }
        }
    }

    @Override
    public ImmutableList add(Object e) {
        if (this.isEmpty()) {
            return new ImmutableLinkedList(new Node(e), 1);
        }
        Object[] c = {e};
        return myAdd(this.size(), c);
    }

    @Override
    public ImmutableList add(int index, Object e) {
        if (this.isEmpty()) {
            return new ImmutableLinkedList(new Node(e), 1);
        }
        checkerIndex(index);
        Object[] c = {e};
        return myAdd(index, c);
    }

    @Override
    public ImmutableList addAll(Object[] c) {
        if (this.size() == 0) {
            return new ImmutableLinkedList(c);
        }
        return myAdd(this.size(), c);
    }

    @Override
    public ImmutableList addAll(int index, Object[] c) {
        checkerIndex(index);
        return myAdd(index, c);
    }

    private ImmutableList myAdd(int index, Object[] c) {
        Node newHead = copyLinkedNodes(this.head);
        Node currNode = newHead;
        Node nextNode = newHead;
        int pointer = 0;
        if (index == 0) {
            currNode = new Node(c[0]);
            newHead = currNode;
            pointer = 1;
        } else {
            int ind = 0;
            while (ind + 1 != index) {
                currNode = currNode.getNext();
                ind++;
            }
            nextNode = currNode.getNext();
        }
        for (int i = pointer; i < c.length; i++) {
            Node newNode = new Node(c[i]);
            currNode.setNext(newNode);
            currNode = newNode;
        }
        currNode.setNext(nextNode);

        return new ImmutableLinkedList(newHead, this.size() + c.length);
    }

    private Node copyLinkedNodes(Node node) {
        if (node == null) {
            return null;
        }
        Node newHead = new Node(node.getValue());
        Node currNodeOld = node;
        Node currNodeNew = newHead;
        while (currNodeOld.getNext() != null) {
            currNodeNew.setNext(new Node(currNodeOld.getNext().getValue()));
            currNodeNew = currNodeNew.getNext();
            currNodeOld = currNodeOld.getNext();
        }
        return newHead;
    }


    @Override
    public Object get(int index) {
        checkerIndex(index);
        int i = 0;
        Node currNode = head;
        while (i < this.size()) {
            if (i == index) {
                break;
            }
            currNode = currNode.getNext();
            i++;
        }
        return currNode.getValue();
    }

    @Override
    public ImmutableList remove(int index) {
        checkerIndex(index);
        Node newHead;

        if (index == 0) {
            newHead = copyLinkedNodes(this.head.getNext());
        } else {
            Node currNode = copyLinkedNodes(this.head);
            newHead = currNode;
            int i = 0;
            while (i + 1 < index) {
                currNode = currNode.getNext();
                i++;
            }
            currNode.setNext(currNode.getNext().getNext());
        }
        return new ImmutableLinkedList(newHead, this.size() - 1);
    }

    @Override
    public ImmutableList set(int index, Object e) {
        checkerIndex(index);

        Node newHead;
        if (index == 0) {
            Node secondNode = copyLinkedNodes(this.head.getNext());
            newHead = new Node(e);
            newHead.setNext(secondNode);
        } else {

            Node currNode = copyLinkedNodes(this.head);
            newHead = currNode;
            int i = 0;
            while (i + 1 < index) {
                currNode = currNode.getNext();
                i++;
            }
            Node nextNext = currNode.getNext().getNext();
            currNode.setNext(new Node(e));
            currNode = currNode.getNext();
            currNode.setNext(nextNext);
        }
        return new ImmutableLinkedList(newHead, this.size());
    }

    @Override
    public int indexOf(Object e) {
        Node currNode = this.head;
        for (int i = 0; i < this.size(); i++) {
            if (currNode.getValue() == e) {
                return i;
            }
            currNode = currNode.getNext();
        }
        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ImmutableList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[this.size()];

        Node currNode = this.head;
        int i = 0;
        while (i < this.size()) {
            newArray[i] = currNode.getValue();
            currNode = currNode.getNext();
            i++;
        }
        return newArray;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("{");
        Node currNode = this.head;
        for (int i = 0; i < this.size(); i++) {
            if (i == 0) {
                res.append(currNode.getValue());
            } else {
                res.append(", ").append(currNode.getValue());
            }
            currNode = currNode.getNext();
        }
        res.append("}");
        return res.toString();
    }

    private void checkerIndex(int index) {
        if (index >= this.size() || index < 0) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutableLinkedList addFirst(Object e) {
        return (ImmutableLinkedList) this.add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return (ImmutableLinkedList) this.add(e);
    }

    public Object getFirst() {
        return this.get(0);
    }

    public Object getLast() {
        return this.get(this.size() - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return (ImmutableLinkedList) this.remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return (ImmutableLinkedList) this.remove(this.size() - 1);
    }
}
