package data_structures.stack;

import data_structures.linked_list.SinglyLinkedList;

import java.util.NoSuchElementException;

public class LinkedStack<T> implements Stack<T> {

    private SinglyLinkedList<T> list = new SinglyLinkedList<>();

    /**
     * Pushes an element onto the top of the stack
     *
     * @param value the element to push
     */
    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    /**
     * Removes and returns the element at the top of the stack
     *
     * @return the element at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }

        return list.deleteFirst();
    }

    /**
     * Returns the element at the top of the stack without removing it
     *
     * @return the element at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }

        return list.get(0);
    }

    /**
     * Returns the number of elements in the stack
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the stack contains no elements
     *
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Searches for an element in the stack and returns its 1-based position from the top
     *
     * @param value the element to search for
     * @return the 1-based position from the top, or -1 if not found
     */
    public int search(T value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns an iterator over the elements in this stack (from top to bottom)
     *
     * @return an iterator
     */
    public java.util.Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns a string representation of the stack
     *
     * @return string representation
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns true if the stack contains the specified element
     *
     * @param value the element to check
     * @return true if contains, false otherwise
     */
    public boolean contains(T value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Returns an array containing all elements in the stack (from bottom to top)
     *
     * @return array of elements
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
