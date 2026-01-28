package data_structures.dynamic_array;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull", "unused", "unchecked"})
public class DynamicArray<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    //region - Constructors
    public DynamicArray() {
        // TODO
    }

    public DynamicArray(int initialCapacity) {
        // TODO
    }

    public DynamicArray(T[] array) {
        // TODO
    }
    //endregion

    //region - Add
    public void add(T value) {
        // TODO
    }

    public void addAt(int index, T value) {
        // TODO
    }

    public void addFirst(T value) {
        // TODO
    }

    public void addLast(T value) {
        // TODO
    }

    public void addAll(Collection<? extends T> collection) {
        // TODO
    }

    public void addAll(T[] array) {
        // TODO
    }
    //endregion

    //region - Delete
    public T removeAt(int index) {
        // TODO
        return null;
    }

    public T removeFirst() {
        // TODO
        return null;
    }

    public T removeLast() {
        // TODO
        return null;
    }

    public boolean removeValue(T value) {
        // TODO
        return false;
    }

    public boolean removeAll(Collection<? extends T> collection) {
        // TODO
        return false;
    }

    public void clear() {
        // TODO
    }
    //endregion

    //region - Get
    public T get(int index) {
        // TODO
        return null;
    }

    public T getFirst() {
        // TODO
        return null;
    }

    public T getLast() {
        // TODO
        return null;
    }

    public int indexOf(T value) {
        // TODO
        return -1;
    }

    public int lastIndexOf(T value) {
        // TODO
        return -1;
    }

    public List<Integer> allIndicesOf(T value) {
        // TODO
        return null;
    }
    //endregion

    //region - Set
    public void set(int index, T value) {
        // TODO
    }

    public void setFirst(T value) {
        // TODO
    }

    public void setLast(T value) {
        // TODO
    }
    //endregion

    //region - Utilities
    public int size() {
        // TODO
        return 0;
    }

    public int capacity() {
        // TODO
        return 0;
    }

    public boolean isEmpty() {
        // TODO
        return false;
    }

    public boolean contains(T value) {
        // TODO
        return false;
    }

    public void ensureCapacity(int minCapacity) {
        // TODO
    }

    public void trimToSize() {
        // TODO
    }

    public T[] toArray() {
        // TODO
        return null;
    }

    public DynamicArray<T> subList(int fromIndex, int toIndex) {
        // TODO
        return null;
    }

    public void reverse() {
        // TODO
    }

    public void sort(Comparator<? super T> comparator) {
        // TODO
    }
    //endregion

    //region - Private Helpers
    private void ensureCapacityInternal(int minCapacity) {
        // TODO
    }

    private void grow(int minCapacity) {
        // TODO
    }

    private void rangeCheck(int index) {
        // TODO
    }

    private void rangeCheckForAdd(int index) {
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
