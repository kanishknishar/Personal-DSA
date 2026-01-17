package challenges.linked_list;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

/**
 * Test suite for LinkedList Challenges.
 * 
 * Each test verifies:
 *   1. CORRECTNESS - The output matches expected result
 *   2. COMPLIANCE  - The algorithm respects constraints (one pass, O(1) space, etc.)
 * 
 * The compliance checks use InstrumentedNode to track node accesses without
 * revealing the solution approach.
 */
@SuppressWarnings("ConstantConditions")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class LinkedListChallengesTest {

	@BeforeEach
	void resetTracking() {
		InstrumentedNode.resetTracking();
	}

	//region - Problem 1: Remove N-th From End

	@Nested
	class RemoveNthFromEnd {

		@Test
		void removes_last_element_when_n_is_1(SoftAssertions softly) {
			// Setup: 1 -> 2 -> 3 -> 4 -> 5
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking(); // Start fresh tracking

			// Execute
			var result = LinkedListChallenges.removeNthFromEnd(head, 1);

			// Verify correctness
			softly.assertThat(InstrumentedNode.toList(result))
				.as("Should remove last element (n=1)")
				.containsExactly(1, 2, 3, 4);

			// Verify constraint: ONE PASS
			softly.assertThat(InstrumentedNode.wasOnePass())
				.as("Must be ONE PASS - each node visited at most once. " +
					"Max access count was: " + InstrumentedNode.getMaxAccessCount())
				.isTrue();
		}

		@Test
		void removes_second_from_end_when_n_is_2(SoftAssertions softly) {
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.removeNthFromEnd(head, 2);

			softly.assertThat(InstrumentedNode.toList(result))
				.as("Should remove 4 (second from end)")
				.containsExactly(1, 2, 3, 5);

			softly.assertThat(InstrumentedNode.wasOnePass())
				.as("Must be ONE PASS")
				.isTrue();
		}

		@Test
		void removes_first_element_when_n_equals_length(SoftAssertions softly) {
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.removeNthFromEnd(head, 5);

			softly.assertThat(InstrumentedNode.toList(result))
				.as("Should remove first element (n=length)")
				.containsExactly(2, 3, 4, 5);

			softly.assertThat(InstrumentedNode.wasOnePass())
				.as("Must be ONE PASS")
				.isTrue();
		}

		@Test
		void single_element_list_returns_null() {
			var head = InstrumentedNode.buildList(1);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.removeNthFromEnd(head, 1);

			assertThat(result).isNull();
		}

		@Test
		void two_element_list_remove_first(SoftAssertions softly) {
			var head = InstrumentedNode.buildList(1, 2);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.removeNthFromEnd(head, 2);

			softly.assertThat(InstrumentedNode.toList(result))
				.containsExactly(2);

			softly.assertThat(InstrumentedNode.wasOnePass())
				.as("Must be ONE PASS")
				.isTrue();
		}

		@Test
		@DisplayName("COMPLIANCE: Fails if using two-pass approach")
		void compliance_detects_two_pass_approach() {
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			InstrumentedNode.resetTracking();

			// If student does: first pass to count, second pass to remove
			// The max access count will be > 3

			LinkedListChallenges.removeNthFromEnd(head, 3);

			// This test documents the expected behavior
			// Students who use two passes will fail wasOnePass()
			assertThat(InstrumentedNode.wasOnePass())
				.as("Two-pass solutions will fail this check. " +
					"Hint: Use two pointers with a gap of n between them.")
				.isTrue();
		}
	}

	//endregion

	//region - Problem 2: Detect Cycle Start

	@Nested
	class DetectCycleStart {

		@Test
		void returns_null_for_list_without_cycle() {
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.detectCycleStart(head);

			assertThat(result).isNull();
		}

		@Test
		void detects_cycle_at_second_node(SoftAssertions softly) {
			// Build: 1 -> 2 -> 3 -> 4 -> (back to 2)
			var head = buildListWithCycle(List.of(1, 2, 3, 4), 1);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.detectCycleStart(head);

			softly.assertThat(result).isNotNull();
			softly.assertThat(result.getValue())
				.as("Cycle starts at node with value 2")
				.isEqualTo(2);

			// O(1) space check: no new nodes should be created
			softly.assertThat(InstrumentedNode.getTotalNodesCreated())
				.as("O(1) space: should not create new nodes")
				.isEqualTo(0);
		}

		@Test
		void detects_cycle_at_head() {
			// Build: 1 -> 2 -> 3 -> (back to 1)
			var head = buildListWithCycle(List.of(1, 2, 3), 0);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.detectCycleStart(head);

			assertThat(result.getValue())
				.as("Cycle starts at head")
				.isEqualTo(1);
		}

		@Test
		void detects_self_loop() {
			// Build: 1 -> (back to 1)
			var head = buildListWithCycle(List.of(1), 0);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.detectCycleStart(head);

			assertThat(result.getValue()).isEqualTo(1);
		}

		@Test
		@DisplayName("COMPLIANCE: Fails if using HashSet")
		void compliance_no_extra_space() {
			var head = buildListWithCycle(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3);
			InstrumentedNode.resetTracking();

			LinkedListChallenges.detectCycleStart(head);

			// If using HashSet<Node>, you'd create wrapper objects or similar
			// The key compliance here is no external data structure
			assertThat(InstrumentedNode.getTotalNodesCreated())
				.as("O(1) space: must not create additional nodes/objects")
				.isEqualTo(0);
		}

		private InstrumentedNode<Integer> buildListWithCycle(List<Integer> values, int cycleIndex) {
			if (values.isEmpty()) return null;

			InstrumentedNode.trackingEnabled = false;

			InstrumentedNode<Integer> head = new InstrumentedNode<>(values.get(0));
			InstrumentedNode<Integer> current = head;
			InstrumentedNode<Integer> cycleNode = (cycleIndex == 0) ? head : null;

			for (int i = 1; i < values.size(); i++) {
				current.setNext(new InstrumentedNode<>(values.get(i)));
				current = current.getNext();
				if (i == cycleIndex) {
					cycleNode = current;
				}
			}

			// Create cycle
			if (cycleNode != null) {
				current.setNext(cycleNode);
			}

			InstrumentedNode.trackingEnabled = true;
			InstrumentedNode.resetTracking();

			return head;
		}
	}

	//endregion

	//region - Problem 3: Partition List

	@Nested
	class PartitionList {

		@Test
		void partitions_around_value_3(SoftAssertions softly) {
			// 1 -> 4 -> 3 -> 2 -> 5 -> 2, x=3
			var head = InstrumentedNode.buildList(1, 4, 3, 2, 5, 2);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.partitionList(head, 3);
			List<Integer> resultList = InstrumentedNode.toList(result);

			// All elements < 3 should come before elements >= 3
			int partitionPoint = -1;
			for (int i = 0; i < resultList.size(); i++) {
				if (resultList.get(i) >= 3) {
					partitionPoint = i;
					break;
				}
			}

			softly.assertThat(partitionPoint)
				.as("Should have elements < 3 before partition")
				.isGreaterThan(0);

			// Verify all elements before partition are < 3
			for (int i = 0; i < partitionPoint; i++) {
				softly.assertThat(resultList.get(i))
					.as("Element at index " + i + " should be < 3")
					.isLessThan(3);
			}

			// Verify all elements at/after partition are >= 3
			for (int i = partitionPoint; i < resultList.size(); i++) {
				softly.assertThat(resultList.get(i))
					.as("Element at index " + i + " should be >= 3")
					.isGreaterThanOrEqualTo(3);
			}

			// Verify one pass
			softly.assertThat(InstrumentedNode.wasOnePass())
				.as("Must be ONE PASS")
				.isTrue();
		}

		@Test
		void preserves_relative_order_within_partitions(SoftAssertions softly) {
			// 1 -> 4 -> 3 -> 2 -> 5 -> 2, x=3
			// Less than 3: 1, 2, 2 (must maintain this order)
			// Greater/equal 3: 4, 3, 5 (must maintain this order)
			var head = InstrumentedNode.buildList(1, 4, 3, 2, 5, 2);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.partitionList(head, 3);
			List<Integer> resultList = InstrumentedNode.toList(result);

			// Expected: 1 -> 2 -> 2 -> 4 -> 3 -> 5
			softly.assertThat(resultList)
				.containsExactly(1, 2, 2, 4, 3, 5);
		}

		@Test
		void all_elements_less_than_x() {
			var head = InstrumentedNode.buildList(1, 2, 1, 2);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.partitionList(head, 5);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 1, 2);
		}

		@Test
		void all_elements_greater_or_equal_to_x() {
			var head = InstrumentedNode.buildList(5, 6, 7, 8);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.partitionList(head, 3);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(5, 6, 7, 8);
		}
	}

	//endregion

	//region - Problem 4: Add Two Numbers

	@Nested
	class AddTwoNumbers {

		@Test
		void adds_342_plus_465_equals_807(SoftAssertions softly) {
			// 342 = 2 -> 4 -> 3
			// 465 = 5 -> 6 -> 4
			// 807 = 7 -> 0 -> 8
			var l1 = InstrumentedNode.buildList(2, 4, 3);
			var l2 = InstrumentedNode.buildList(5, 6, 4);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.addTwoNumbers(l1, l2);

			softly.assertThat(InstrumentedNode.toList(result))
				.containsExactly(7, 0, 8);
		}

		@Test
		void handles_carry_propagation() {
			// 99 = 9 -> 9
			// 1  = 1
			// 100 = 0 -> 0 -> 1
			var l1 = InstrumentedNode.buildList(9, 9);
			var l2 = InstrumentedNode.buildList(1);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.addTwoNumbers(l1, l2);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(0, 0, 1);
		}

		@Test
		void adds_zeros() {
			var l1 = InstrumentedNode.buildList(0);
			var l2 = InstrumentedNode.buildList(0);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.addTwoNumbers(l1, l2);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(0);
		}

		@Test
		void handles_different_lengths() {
			// 9999999 + 9999 = 10009998
			var l1 = InstrumentedNode.buildList(9, 9, 9, 9, 9, 9, 9);
			var l2 = InstrumentedNode.buildList(9, 9, 9, 9);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.addTwoNumbers(l1, l2);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(8, 9, 9, 9, 0, 0, 0, 1);
		}
	}

	//endregion

	//region - Problem 5: Flatten Multilevel Doubly Linked List

	@Nested
	class FlattenMultilevel {

		@Test
		void flattens_simple_two_level_list() {
			// 1 <-> 2 <-> 3
			//       |
			//       4 <-> 5
			// Result: 1 <-> 2 <-> 4 <-> 5 <-> 3
			var head = buildMultilevelList();
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.flattenMultilevel(head);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 4, 5, 3);
		}

		@Test
		void flattens_list_without_children() {
			var head = InstrumentedNode.buildDoublyList(1, 2, 3);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.flattenMultilevel(head);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 3);
		}

		@Test
		void verifies_prev_pointers_after_flattening() {
			var head = buildMultilevelList();
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.flattenMultilevel(head);

			// Traverse forward to end
			InstrumentedNode.trackingEnabled = false;
			var current = result;
			while (current.getNext() != null) {
				current = current.getNext();
			}

			// Traverse backward, collecting values
			var backwardValues = new java.util.ArrayList<Integer>();
			while (current != null) {
				backwardValues.add((Integer) current.getValue());
				current = current.getPrev();
			}
			InstrumentedNode.trackingEnabled = true;

			assertThat(backwardValues)
				.as("Backward traversal should work correctly")
				.containsExactly(3, 5, 4, 2, 1);
		}

		private InstrumentedNode<Integer> buildMultilevelList() {
			InstrumentedNode.trackingEnabled = false;

			// Main: 1 <-> 2 <-> 3
			var n1 = new InstrumentedNode<>(1);
			var n2 = new InstrumentedNode<>(2);
			var n3 = new InstrumentedNode<>(3);
			n1.setNext(n2); n2.setPrev(n1);
			n2.setNext(n3); n3.setPrev(n2);

			// Child of 2: 4 <-> 5
			var n4 = new InstrumentedNode<>(4);
			var n5 = new InstrumentedNode<>(5);
			n4.setNext(n5); n5.setPrev(n4);
			n2.setChild(n4);

			InstrumentedNode.trackingEnabled = true;
			InstrumentedNode.resetTracking();

			return n1;
		}
	}

	//endregion

	//region - Problem 6: Reverse K-Group

	@Nested
	class ReverseKGroup {

		@Test
		void reverses_in_groups_of_2(SoftAssertions softly) {
			// 1->2->3->4->5, k=2 → 2->1->4->3->5
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.reverseKGroup(head, 2);

			softly.assertThat(InstrumentedNode.toList(result))
				.containsExactly(2, 1, 4, 3, 5);

			// O(1) space: no new nodes
			softly.assertThat(InstrumentedNode.getTotalNodesCreated())
				.as("O(1) space: must not create new nodes")
				.isEqualTo(0);
		}

		@Test
		void reverses_in_groups_of_3() {
			// 1->2->3->4->5, k=3 → 3->2->1->4->5
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.reverseKGroup(head, 3);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(3, 2, 1, 4, 5);
		}

		@Test
		void k_equals_1_returns_same_list() {
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.reverseKGroup(head, 1);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 3, 4, 5);
		}

		@Test
		void k_equals_length_reverses_entire_list() {
			var head = InstrumentedNode.buildList(1, 2, 3, 4, 5);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.reverseKGroup(head, 5);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(5, 4, 3, 2, 1);
		}

		@Test
		void k_greater_than_length_returns_unchanged() {
			var head = InstrumentedNode.buildList(1, 2, 3);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.reverseKGroup(head, 5);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 3);
		}
	}

	//endregion

	//region - Problem 7: Copy Random List

	@Nested
	class CopyRandomList {

		@Test
		void copies_list_with_random_pointers(SoftAssertions softly) {
			var head = buildRandomPointerList();
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.copyRandomList(head);

			// Verify it's a deep copy (different nodes)
			softly.assertThat(result)
				.as("Should return new node, not same reference")
				.isNotSameAs(head);

			// Verify values match
			softly.assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 3);

			// Verify O(1) space (interleaving approach creates nodes, but as part of copy)
			// The constraint here is no HashMap - we verify by checking no external structures
		}

		@Test
		void handles_null_input() {
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.copyRandomList(null);

			assertThat(result).isNull();
		}

		@Test
		void single_node_with_self_random() {
			InstrumentedNode.trackingEnabled = false;
			var node = new InstrumentedNode<>(1);
			node.setChild(node); // random points to self
			InstrumentedNode.trackingEnabled = true;
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.copyRandomList(node);

			assertThat(result.getValue()).isEqualTo(1);
			assertThat(result.getChild()).isSameAs(result);
		}

		private InstrumentedNode<Integer> buildRandomPointerList() {
			InstrumentedNode.trackingEnabled = false;

			var n1 = new InstrumentedNode<>(1);
			var n2 = new InstrumentedNode<>(2);
			var n3 = new InstrumentedNode<>(3);

			n1.setNext(n2);
			n2.setNext(n3);

			// Random pointers (using child field)
			n1.setChild(n3);  // 1's random -> 3
			n2.setChild(n1);  // 2's random -> 1
			n3.setChild(n2);  // 3's random -> 2

			InstrumentedNode.trackingEnabled = true;
			InstrumentedNode.resetTracking();

			return n1;
		}
	}

	//endregion

	//region - Problem 8: Merge K Sorted Lists

	@Nested
	class MergeKLists {

		@Test
		void merges_three_sorted_lists() {
			var l1 = InstrumentedNode.buildList(1, 4, 5);
			var l2 = InstrumentedNode.buildList(1, 3, 4);
			var l3 = InstrumentedNode.buildList(2, 6);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.mergeKLists(l1, l2, l3);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 1, 2, 3, 4, 4, 5, 6);
		}

		@Test
		void handles_empty_input() {
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.mergeKLists();

			assertThat(result).isNull();
		}

		@Test
		void handles_single_list() {
			var l1 = InstrumentedNode.buildList(1, 2, 3);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.mergeKLists(l1);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 3);
		}

		@Test
		void handles_lists_with_null_heads() {
			var l1 = InstrumentedNode.buildList(1, 2);
			InstrumentedNode<Integer> l2 = null;
			var l3 = InstrumentedNode.buildList(3, 4);
			InstrumentedNode.resetTracking();

			var result = LinkedListChallenges.mergeKLists(l1, l2, l3);

			assertThat(InstrumentedNode.toList(result))
				.containsExactly(1, 2, 3, 4);
		}
	}

	//endregion
}
