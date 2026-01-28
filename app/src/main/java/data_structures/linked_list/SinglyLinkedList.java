package data_structures.linked_list;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull"})
public class SinglyLinkedList<T> {
	public Node<T> head;
	public Node<T> tail;
	public int size;

	//region - Add
	public void addAt(int index, T value) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		if (isEmpty() || index == 0) {
			addFirst(value);
			return;
		}

		// index = 0
		var curr = getNode(index - 1);

		Node<T> newNode = new Node<>(value, curr.next);
		curr.next = newNode;

		if (curr.next.next == null) {
			tail = newNode;
		}

		size++;
	}

	public void addFirst(T value) {
		if (isEmpty()) {
			head = new Node<>(value);
			tail = head;
			size++;
			return;
		}

		var oldHead = head;
		head = new Node<>(value, oldHead);

		if (oldHead.next == null) {
			tail = oldHead;
		}

		size++;
	}

	public void addLast(T value) {
		if (isEmpty()) {
			addFirst(value);
			return;
		}

		var curr = head;

		while (curr.next != null) {
			curr = curr.next;
		}

		Node<T> newTail = new Node<>(value);
		curr.next = newTail;
		tail = newTail;
		size++;
	}

	public void addBefore(Node<T> node, T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		var curr = head;

		for (int i = 0; i < size; i++) {
			if (curr.equals(node)) {
				addAt(i, value);
				return;
			}

			curr = curr.next;
		}

		throw new NoSuchElementException();
	}

	public void addAfter(Node<T> node, T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		var curr = head;

		for (int i = 0; i < size; i++) {
			if (curr.equals(node)) {
				addAt(i + 1, value);
				return;
			}

			curr = curr.next;
		}

		throw new NoSuchElementException();
	}
//endregion

	//region - Delete
	public T deleteFirst() {
		var oldHead = head;

		switch (size) {
			case 0 -> throw new IllegalStateException();
			case 1 -> {
				head = null;
				tail = null;
			}
			case 2 -> head = tail;
			default -> head = head.next;
		}

		size--;
		return oldHead.value;
	}

	public T deleteLast() {
		return switch (size) {
			case 0 -> throw new IllegalStateException();
			case 1 -> deleteFirst();
			case 2 -> {
				var deletedNode = tail;
				tail = head;
				tail.next = null;
				size--;
				yield deletedNode.value;
			}
			default -> {
				var curr = getNode(size - 2);

				var deletedNode = curr.next;
				tail = curr;
				tail.next = null;
				size--;
				yield deletedNode.value;
			}
		};
	}

	public T deleteAt(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == size - 1) {
			return deleteLast();
		}

		return switch (size) {
			case 0 -> throw new IllegalStateException();
			case 1 -> deleteFirst();
			default -> {
				var curr = head;

				if (index == 0) {
					yield deleteFirst();
				}

				for (int i = 0; i < index - 1; i++) {
					curr = curr.next;
				}

				var deletedNode = curr.next;

				curr.next = curr.next.next;

				size--;

				yield deletedNode.value;
			}
		};
	}


	public boolean deleteValue(T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		var curr = head;
		var prevNode = curr;

		for (int i = 0; i < size; i++) {
			if (curr.value.equals(value)) {
				if (i == 0) {
					deleteFirst();
					return true;
				}

				if (i == size - 1) {
					deleteLast();
					return true;
				}

				prevNode.next = curr.next;
				size--;
				return true;
			}

			prevNode = curr;
			curr = curr.next;
		}

		return false;
	}
//endregion

	//region - Get
	public T getFirst() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		return head.value;
	}

	public T getLast() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		return tail.value;
	}

	public T getAt(int index) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		var curr = getNode(index);

		return curr.value;
	}

	public int getFirstIndexOf(T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		var curr = head;

		for (int i = 0; i < size; i++) {
			if (curr.value.equals(value)) return i;

			curr = curr.next;
		}

		return -1;
	}

	public int getLastIndexOf(T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		var matches = getAllIndices(value);

		return matches.isEmpty() ? -1 : matches.getLast();
	}

	public List<Integer> getAllIndices(T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		var curr = head;

		List<Integer> integers = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			if (curr.value.equals(value)) integers.add(i);
			curr = curr.next;
		}

		return integers;
	}

	public Node<T> getNode(int index) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		var curr = head;

		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}

		return curr;
	}
//endregion

	//region - Set
	public void setFirst(T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		head.value = value;
	}

	public void setLast(T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		tail.value = value;
	}

	public void setAt(int index, T value) {
		if (isEmpty()) {
			throw new IllegalStateException();
		}

		getNode(index).value = value;
	}
//endregion

	//region - Utilities
	public int size() {
		// TODO
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public T[] toArray() {
		var curr = head;

		ArrayList<T> asArrayList = new ArrayList<>();

		while (curr != null) {
			asArrayList.add(curr.value);

			curr = curr.next;
		}

		return (T[]) asArrayList.toArray();
	}

	private List<Node<T>> getAllNodes() {
		var curr = head;

		ArrayList<Node<T>> nodesList = new ArrayList<>();

		while (curr.next != null) {
			nodesList.add(curr);
			curr = curr.next;
		}

		return nodesList;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "[]";
		}

		var curr = head;

		var sb = new StringBuilder("[").append(curr.value);

		while (curr.next != null) {
			sb.append(" -> ").append(curr.next.value);
			curr = curr.next;
		}

		sb.append("]");

		return sb.toString();
	}
//endregion

	//region - Node
	public static class Node<T> {
		public T value;
		public Node<T> next;

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
//endregion
}
