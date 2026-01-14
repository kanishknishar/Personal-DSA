package data_structures.linked_list;

public class DoublyLinkedList<T> {
    public DoublyLinkedList(T headValue, T tailValue) {
    }

    public void addAt(int index, T value) {
    }

    public void addFirst(T value) {
    }

    public void addLast(T value) {
    }

    public T deleteFirst() {
    }

    public T deleteLast() {
    }

    public T deleteAt(int index) {
    }

    public boolean delete(T value) {
    }

    public T getFirst() {
    }

    public T getLast() {
    }

    public T get(int index) {
    }

    public void set(int index, T value) {
    }

    public int indexOf(T value) {
    }

    public int lastIndexOf(T value) {
    }

    public int size() {
    }

    public boolean isEmpty() {
    }

    @Override
    public String toString() {
    }

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        @Override
        public String toString() {
            return value.toString();
        }
    }
}