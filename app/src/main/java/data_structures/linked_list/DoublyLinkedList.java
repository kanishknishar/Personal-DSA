package data_structures.linked_list;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull", "unused"})
public class DoublyLinkedList<T> {

    public final Node<T> head;
    public final Node<T> tail;
    public int size;

    public DoublyLinkedList(T headValue, T tailValue) {
        head = new Node<>(headValue);
        tail = new Node<>(tailValue);
        head.next = tail;
        tail.prev = head;
    }

    //region - Add
    public void addAt(int index, T value) {
        if (index < 0 || (index > size)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> prevNode;
        if (index <= size / 2) {
            prevNode = nodeBackwardTraversal(index - 1);
        } else {
            prevNode = valueBackwardTraversal(index - 1);
        }

        new Node<>(value, prevNode, prevNode.next);

        size++;
    }

    public void addFirst(T value) {
        addAt(0, value);
    }

    public void addLast(T value) {
        var previousLast = tail.prev;

        new Node<>(value, previousLast, tail);
        size++;
    }

    public void addBefore(Node<T> node, T value) {
        new Node<>(value, node.prev, node);
        size++;
    }

    public void addAfter(Node<T> node, T value) {
        new Node<>(value, node, node.next);
        size++;
    }
    //endregion

    //region - Delete
    public T deleteAt(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> deletedNode = getNode(index);

        T deletedVal = deletedNode.value;

        deletedNode.prev.next = deletedNode.next;
        deletedNode.next.prev = deletedNode.prev;

        size--;

        return deletedVal;
    }

    public T deleteFirst() {
        return deleteAt(0);
    }

    public T deleteLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }

        var deletedNode = tail.prev;
        var retainPreviousNode = deletedNode.prev;

        retainPreviousNode.next = tail;
        tail.prev = retainPreviousNode;
        size--;
        return deletedNode.value;
    }

    public boolean deleteValue(T value) {
        var curr = head;

        while (curr.next != tail) {
            curr = curr.next;
            if (curr.value.equals(value)) {
                curr.next.prev = curr.prev;
                curr.prev.next = curr.next;
                size--;
                return true;
            }
        }

        return false;
    }
    //endregion

    //region - Get
    public T get(int index) {
        if (index <= (size / 2)) {
            return nodeForwardTraversal(index);
        }

        return valueForwardTraversal(index);
    }

    public T getFirst() {
        return get(0);
    }

    public T getLast() {
        if (tail.prev == head) {
            throw new IndexOutOfBoundsException();
        }

        return tail.prev.value;
    }

    public List<Integer> getAllIndices(T value) {
        var matches = new ArrayList<Integer>();
        var curr = head;

        for (int i = 0; i < size; i++) {
            curr = curr.next;

            if (curr.value.equals(value)) {
                matches.add(i);
            }
        }

        return matches;
    }

    public int getFirstIndexOf(T value) {
        var curr = head;

        var matches = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            curr = curr.next;

            if (curr.value.equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public int getLastIndexOf(T value) {
        List<Integer> matches = getAllIndices(value);

        if (matches.isEmpty()) {
            return -1;
        }

        return matches.getLast();
    }

    public Node<T> getNode(int index) {
        if (index <= size / 2) {
            return nodeBackwardTraversal(index);
        } else {
            return valueBackwardTraversal(index);
        }
    }
    //endregion

    //region - Set
    public void setAt(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        getNode(index).value = value;
    }

    public void setFirst(T value) {
        setAt(0, value);
    }

    public void setLast(T value) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        tail.prev.value = value;
    }
    //endregion

    //region - Iterator
    public T nodeForwardTraversal(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        var curr = nodeBackwardTraversal(index);

        return curr.value;
    }

    public Node<T> nodeBackwardTraversal(int index) {
        System.out.println("Getting node straight");
        var curr = head;

        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    public T valueForwardTraversal(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        var curr = valueBackwardTraversal(index);

        return curr.value;
    }

    public Node<T> valueBackwardTraversal(int index) {
        System.out.println("Getting node reverse");

        var curr = tail;

        for (int i = size; i > index; i--) {
            curr = curr.prev;
        }

        return curr;
    }
    //endregion

    //region - Utilities
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T value) {
        return getFirstIndexOf(value) != -1;
    }

    public T[] toArray() {
        var curr = head;

        T[] array = (T[]) new Object[size];

        for (int i = 0; i < size; i++) {
            curr = curr.next;
            array[i] = curr.value;
        }

        return array;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        var curr = head;
        StringBuilder sb = new StringBuilder();

        while (curr.next != tail) {
            sb.append("[").append(curr.next).append("]").append(" <-> ");
            curr = curr.next;
        }

        return sb.substring(0, sb.length() - 5);
    }

    //endregion
    public static class Node<T> {

        public T value;
        public Node<T> prev;
        public Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            Node<T> oldNext = prev.next;
            prev.next = this;
            this.prev = prev;
            this.next = oldNext;
            oldNext.prev = this;
        }

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
