package challenges.linked_list;

import java.util.*;

/**
 * Instrumented Node for tracking algorithmic compliance.
 * 
 * This wrapper tracks:
 * - How many times each node is accessed (for "one pass" verification)
 * - How many nodes are created (for "no new nodes" / "in-place" verification)
 * - Pointer assignments (for "O(1) space" verification)
 * 
 * STUDENT: Do NOT modify this class. Your solution is tested against it.
 */
public class InstrumentedNode<T> {

	//region - Core Fields

	private T value;
	private InstrumentedNode<T> next;
	private InstrumentedNode<T> prev; // For doubly linked
	private InstrumentedNode<T> child; // For multilevel problems

	//endregion

	//region - Tracking Fields (Package-Private for Test Access)

	final int nodeId;
	int accessCount = 0;

	static int totalNodesCreated = 0;
	static int pointerAssignments = 0;
	static Map<Integer, Integer> nodeAccessCounts = new HashMap<>();
	static List<String> accessLog = new ArrayList<>();
	static boolean trackingEnabled = true;

	//endregion

	//region - Constructors

	public InstrumentedNode(T value) {
		this.value = value;
		this.nodeId = totalNodesCreated++;
		nodeAccessCounts.put(nodeId, 0);

		if (trackingEnabled) {
			accessLog.add("CREATE node[" + nodeId + "] = " + value);
		}
	}

	public InstrumentedNode(T value, InstrumentedNode<T> next) {
		this(value);
		this.next = next;
		pointerAssignments++;
	}

	//endregion

	//region - Tracked Getters (Count Access)

	public T getValue() {
		recordAccess("getValue");
		return value;
	}

	public InstrumentedNode<T> getNext() {
		recordAccess("getNext");
		return next;
	}

	public InstrumentedNode<T> getPrev() {
		recordAccess("getPrev");
		return prev;
	}

	public InstrumentedNode<T> getChild() {
		recordAccess("getChild");
		return child;
	}

	//endregion

	//region - Tracked Setters (Count Pointer Assignments)

	public void setValue(T value) {
		recordAccess("setValue");
		this.value = value;
	}

	public void setNext(InstrumentedNode<T> next) {
		recordAccess("setNext");
		pointerAssignments++;
		this.next = next;
	}

	public void setPrev(InstrumentedNode<T> prev) {
		recordAccess("setPrev");
		pointerAssignments++;
		this.prev = prev;
	}

	public void setChild(InstrumentedNode<T> child) {
		recordAccess("setChild");
		pointerAssignments++;
		this.child = child;
	}

	//endregion

	//region - Access Recording

	private void recordAccess(String operation) {
		if (!trackingEnabled) return;

		accessCount++;
		nodeAccessCounts.put(nodeId, accessCount);
		accessLog.add(operation + " on node[" + nodeId + "]=" + value + " (access #" + accessCount + ")");
	}

	//endregion

	//region - Static Reset & Query Methods (For Tests)

	public static void resetTracking() {
		totalNodesCreated = 0;
		pointerAssignments = 0;
		nodeAccessCounts.clear();
		accessLog.clear();
		trackingEnabled = true;
	}

	public static int getTotalNodesCreated() {
		return totalNodesCreated;
	}

	public static int getPointerAssignments() {
		return pointerAssignments;
	}

	public static int getMaxAccessCount() {
		return nodeAccessCounts.values().stream()
			.mapToInt(Integer::intValue)
			.max()
			.orElse(0);
	}

	public static boolean wasOnePass() {
		// One pass = no node accessed more than a small constant (allow 2-3 for edge cases)
		return getMaxAccessCount() <= 3;
	}

	public static boolean wasConstantSpace(int allowedPointers) {
		// This is approximate - real check would need bytecode analysis
		return pointerAssignments <= allowedPointers * 2;
	}

	public static List<String> getAccessLog() {
		return new ArrayList<>(accessLog);
	}

	public static void printAccessLog() {
		System.out.println("=== Access Log ===");
		accessLog.forEach(System.out::println);
		System.out.println("==================");
	}

	//endregion

	//region - Utility

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Build a singly linked list from values.
	 * Tracking is disabled during construction so it doesn't count against the student.
	 */
	@SafeVarargs
	public static <T> InstrumentedNode<T> buildList(T... values) {
		if (values.length == 0) return null;

		boolean wasTracking = trackingEnabled;
		trackingEnabled = false;

		InstrumentedNode<T> head = new InstrumentedNode<>(values[0]);
		InstrumentedNode<T> current = head;

		for (int i = 1; i < values.length; i++) {
			current.next = new InstrumentedNode<>(values[i]);
			current = current.next;
		}

		trackingEnabled = wasTracking;
		resetTracking(); // Reset so student's work starts fresh

		return head;
	}

	/**
	 * Build a doubly linked list from values.
	 */
	@SafeVarargs
	public static <T> InstrumentedNode<T> buildDoublyList(T... values) {
		if (values.length == 0) return null;

		boolean wasTracking = trackingEnabled;
		trackingEnabled = false;

		InstrumentedNode<T> head = new InstrumentedNode<>(values[0]);
		InstrumentedNode<T> current = head;

		for (int i = 1; i < values.length; i++) {
			InstrumentedNode<T> newNode = new InstrumentedNode<>(values[i]);
			current.next = newNode;
			newNode.prev = current;
			current = newNode;
		}

		trackingEnabled = wasTracking;
		resetTracking();

		return head;
	}

	/**
	 * Convert list to array for easy assertion.
	 */
	public static <T> List<T> toList(InstrumentedNode<T> head) {
		boolean wasTracking = trackingEnabled;
		trackingEnabled = false;

		List<T> result = new ArrayList<>();
		InstrumentedNode<T> current = head;

		while (current != null) {
			result.add(current.value);
			current = current.next;
		}

		trackingEnabled = wasTracking;
		return result;
	}

	//endregion
}
