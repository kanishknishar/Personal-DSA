package data_structures.stack;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull", "unused"})
public class ArrayStack<T> implements Stack<T>, Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    //region - Constructors
    public ArrayStack() {
        // TODO
    }

    public ArrayStack(int initialCapacity) {
        // TODO
    }
    //endregion

    //region - Stack Operations
    @Override
    public void push(T value) {
        // TODO
    }

    public void pushAll(Collection<? extends T> collection) {
        // TODO
    }

    @Override
    public T pop() {
        // TODO
        return null;
    }

    public List<T> popN(int n) {
        // TODO
        return null;
    }

    @Override
    public T peek() {
        // TODO
        return null;
    }

    public T peekAt(int distanceFromTop) {
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
    public int search(T value) {
        // TODO
        return -1;
    }

    public boolean contains(T value) {
        // TODO
        return false;
    }

    public void clear() {
        // TODO
    }

    public Object[] toArray() {
        // TODO
        return null;
    }

    public void reverse() {
        // TODO
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
