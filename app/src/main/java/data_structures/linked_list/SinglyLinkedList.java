package data_structures.linked_list;

public class SinglyLinkedList<T> {
    private Node<T> head;
    public int size;

    public void addAt(int index, T value) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (isEmpty() || index == 0) {
            addFirst(value);
            return;
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
        size++;
    }

    public void addLast(T value) {
        if (head == null) {
            addFirst(value);
            return;
        }

        var curr = head;

        while (curr.next != null) {
            curr = curr.next;
        }

        curr.next = new Node<>(value);
        size++;
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

    public T get(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        var curr = head;

        // element 3 - index 2 --

        // 0 - head
        // for 0 - head.next == element 1
        // for 1 - element 1.next - element 2
        // for 2 - <2>.next -- element 3

        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr.value;
    }

    public T getFirst() {
        return get(0);
    }

    public T getLast() {
        return get(size - 1);
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

        @Override
        public String toString() {
            return "Node - {" +
                    "value = " + value +
                    ", next = " + next +
                    '}';
        }
    }
}
