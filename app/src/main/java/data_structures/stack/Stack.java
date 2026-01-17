package data_structures.stack;

public interface Stack<T> {

	void push(T value);

	T pop();

	T peek();

	int size();

	boolean isEmpty();

	int search(T value);
}
