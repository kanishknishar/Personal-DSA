package data_structures.queue;

import java.util.Iterator;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull", "unused"})
public class ArrayQueue<T> implements Queue<T>, Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int front;
    private int rear;
    private int size;

    //region - Constructors
    public ArrayQueue() {
        // TODO
    }

    public ArrayQueue(int initialCapacity) {
        // TODO
    }
    //endregion

    //region - Queue Operations
    @Override
    public void enqueue(T value) {
        // TODO
    }

    @Override
    public T dequeue() {
        // TODO
        return null;
    }

    @Override
    public T peek() {
        // TODO
        return null;
    }

    public T peekRear() {
        // TODO
        return null;
    }
    //endregion

    //region - Utilities
    @Override
    public int size() {
        // TODO
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO
        return false;
    }

    public boolean isFull() {
        // TODO
        return false;
    }

    @Override
    public boolean contains(T value) {
        // TODO
        return false;
    }

    @Override
    public void clear() {
        // TODO
    }

    public Object[] toArray() {
        // TODO
        return null;
    }
    //endregion

    //region - Private Helpers
    private void ensureCapacity() {
        // TODO
    }

    private void resize(int newCapacity) {
        // TODO
    }
    //endregion

    //region - Iterator
    @Override
    public Iterator<T> iterator() {
        // TODO
        return null;
    }
    //endregion

    @Override
    public String toString() {
        // TODO
        return "[]";
    }
}
