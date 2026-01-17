package data_structures.linked_list;

import java.util.NoSuchElementException;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull"})
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

		Node<T> curr = head;

		if (index == 0) {
			var deletedValue = curr.value;
			head = curr.next;
			size--;
			return deletedValue;
		}

		for (int i = 0; i < index - 1; i++) {
			System.out.println("Loop number: " + i);
			System.out.println("Current value before reassignment: " + curr.value);

			curr = curr.next;

			System.out.println("Reassigned curr to: " + curr.value);
		}

		Node<T> deletedNode = curr.next;
		curr.next = curr.next.next;

		size--;
		return deletedNode.value;
	}

	public T deleteFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException("List is empty - operation failed.");
		}

		return deleteAt(0);
	}

	public T deleteLast() {
		if (isEmpty()) {
			throw new NoSuchElementException("List is empty - operation failed.");
		}

		return deleteAt(size - 1);
	}

	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		return getNode(index).value;
	}

	private Node<T> getNode(int index) {
		var curr = head;

		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}

		return curr;
	}

	public T getFirst() {
		return get(0);
	}

	public T getLast() {
		return get(size - 1);
	}

	public void set(int index, T value) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		getNode(index).value = value;

	}

	public int indexOf(T value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public int lastIndexOf(T value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean contains(T value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public void clear() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public T[] toArray() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean deleteValue(T value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public void setFirst(T value) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public void setLast(T value) {
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
		if (head == null) {
			return "[]";
		}

		Node<T> curr = head;

		var sb = new StringBuilder("[").append(head.value);
		var count = 1;

		while (curr.next != null) {
			System.out.println("Loop iteration: " + count++);
			sb.append(curr.value).append(" -> ");
		}

		sb.append("]");

		return sb.toString();
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
			return value + " -> ";
		}
	}
}
