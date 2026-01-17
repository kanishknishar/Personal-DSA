package data_structures.linked_list;

@SuppressWarnings({"ConstantConditions", "ReturnOfNull", "unused"})
public class DoublyLinkedList<T> {
	private final Node<T> head;
	private final Node<T> tail;
	public int size;

	public DoublyLinkedList(T headValue, T tailValue) {
		head = new Node<T>(headValue);
		tail = new Node<T>(tailValue);
		head.next = tail;
		tail.prev = head;
	}

	//region - Add
	public void addAt(int index, T value) {
		if (index < 0 || (index > size)) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			new Node<>(value, head, head.next);
		} else if (index == size) {
			new Node<>(value, tail.prev, tail);
		} else {
			Node<T> nodeShiftLeft = getNode(index - 1);

			new Node<>(value, nodeShiftLeft, nodeShiftLeft.next);
		}

		size++;

	}

	public void addFirst(T value) {
		addAt(0, value);
	}

	public void addLast(T value) {
		addAt(size, value);
	}
	//endregion

	//region - Delete
	public T deleteAt(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			Node<T> deletedNode = head.next;
			T deletedVal = deletedNode.value;
			deletedNode.next.prev = head;
			head.next = deletedNode.next;
			size--;
			return deletedVal;
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
		return deleteAt(size - 1);
	}

	public boolean deleteValue(T value) {
		for (int i = 0; i < size; i++) {
			T nodeVal = getNode(i).value;
			if (nodeVal == value) {
				deleteAt(i);
				return true;
			}
		}

		return false;
	}
	//endregion

	//region - Get
	public T get(int index) {
		Node<T> node = getNode(index);

		return node.value;
	}

	private Node<T> getNode(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		var curr = head;

		for (int i = 0; i <= index; i++) {
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

	public int indexOf(T value) {
		throw new UnsupportedOperationException();
	}

	public int lastIndexOf(T value) {
		throw new UnsupportedOperationException();
	}
	//endregion

	//region - Set
	public void setAt(int index, T value) {
		getNode(index).value = value;
	}

	public void setFirst(T value) {
		setAt(0, value);
	}

	public void setLast(T value) {
		setAt(size - 1, value);
	}
	//endregion

	//region - Helpers
	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(T value) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public T[] toArray() {
		throw new UnsupportedOperationException();
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

	private static class Node<T> {
		T value;
		Node<T> prev;
		Node<T> next;

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
	//endregion
}