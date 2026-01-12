package data_structures.linked_list;

public class SinglyLinkedList<T> {
    private Node<T> head;
    public int size;

    public void addAt(int index, T value) {
        if (isEmpty()) {
            addFirst(value);
            return;
        }

        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        var curr = head;

        for (int i = 0; i < (index - 1); i++) {
            curr = curr.next;
        }

        Node<T> oldNode = curr.next;
        curr.next = new Node<>(value, oldNode);
        size++;
    }

    public void addFirst(T value) {
        Node<T> newHead = new Node<>(value);

        if (head != null) {
            Node<T> oldHead = head;
            head = newHead;
            head.next = oldHead;
            size++;
            return;
        }

        head = newHead;
    }

    public void addLast(T value) {
        if (head == null) {
            addFirst(value);
        }

        addAt(size, value);
    }

    public T deleteAt(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("List is empty - operation failed.");
        }

        var curr = head;

        for (int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }

        Node<T> deletedNode = curr.next;

        curr.next = null;

        return deletedNode.value;
    }

    public T deleteFirst() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List is empty - operation failed.");
        }

        return deleteAt(0);
    }

    public T deleteLast() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List is empty - operation failed.");
        }

        return deleteAt(size - 1);
    }

    public T getFirst() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public T getLast() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public T get(int index) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void set(int index, T value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int indexOf(T value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }
}
