package data_structures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import data_structures.queue.LinkedQueue;

@SuppressWarnings({"DataFlowIssue", "ConstantConditions"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class LinkedQueueTest {

	LinkedQueue<Integer> queue;

	@BeforeEach
	void init() {
		queue = new LinkedQueue<>();
	}

	//region - Add

	@Nested
	class Add {

		@Nested
		class Enqueue {

			@Test
			void to_empty_queue(SoftAssertions softly) {
				queue.enqueue(42);
				softly.assertThat(queue.peek()).isEqualTo(42);
				softly.assertThat(queue.size()).isEqualTo(1);
			}

			@Test
			void to_non_empty_queue(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				softly.assertThat(queue.peek()).isEqualTo(1);
				softly.assertThat(queue.size()).isEqualTo(2);
			}

			@Test
			void maintains_FIFO_order(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				softly.assertThat(queue.peek()).isEqualTo(1);
			}

			@Test
			void handles_null_value(SoftAssertions softly) {
				queue.enqueue(null);
				softly.assertThat(queue.isEmpty()).isFalse();
				softly.assertThat(queue.size()).isEqualTo(1);
				softly.assertThat(queue.peek()).isNull();
			}

			@Test
			void stress_1000_elements(SoftAssertions softly) {
				for (int i = 0; i < 1000; i++)
					queue.enqueue(i);
				softly.assertThat(queue.size()).isEqualTo(1000);
				softly.assertThat(queue.peek()).isEqualTo(0);
			}

			@Test
			void handles_duplicate_elements() {
				queue.enqueue(1);
				queue.enqueue(1);
				queue.enqueue(1);
				assertThat(queue.size()).isEqualTo(3);
			}

			@Test
			void after_dequeue_to_empty(SoftAssertions softly) {
				queue.enqueue(1);
				queue.dequeue();
				queue.enqueue(2);
				softly.assertThat(queue.peek()).isEqualTo(2);
				softly.assertThat(queue.size()).isEqualTo(1);
			}
		}
	}

	//endregion

	//region - Read

	@Nested
	class Read {

		@Nested
		class Peek {

			@Test
			void returns_front_element() {
				queue.enqueue(1);
				queue.enqueue(2);
				assertThat(queue.peek()).isEqualTo(1);
			}

			@Test
			void does_not_remove_element(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				softly.assertThat(queue.peek()).isEqualTo(1);
				softly.assertThat(queue.peek()).isEqualTo(1);
				softly.assertThat(queue.size()).isEqualTo(2);
			}

			@Test
			void throws_on_empty_queue() {
				assertThatThrownBy(() -> queue.peek())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void returns_null_when_front_is_null() {
				queue.enqueue(null);
				assertThat(queue.peek()).isNull();
			}

			@Test
			void handles_max_integer_value() {
				queue.enqueue(Integer.MAX_VALUE);
				assertThat(queue.peek()).isEqualTo(Integer.MAX_VALUE);
			}

			@Test
			void handles_min_integer_value() {
				queue.enqueue(Integer.MIN_VALUE);
				assertThat(queue.peek()).isEqualTo(Integer.MIN_VALUE);
			}

			@Test
			void peek_between_enqueues(SoftAssertions softly) {
				queue.enqueue(1);
				softly.assertThat(queue.peek()).isEqualTo(1);
				queue.enqueue(2);
				softly.assertThat(queue.peek()).isEqualTo(1);
				queue.enqueue(3);
				softly.assertThat(queue.peek()).isEqualTo(1);
			}

			@Test
			void after_partial_dequeue(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				queue.dequeue();
				softly.assertThat(queue.peek()).isEqualTo(2);
				queue.dequeue();
				softly.assertThat(queue.peek()).isEqualTo(3);
			}
		}

		@Nested
		class Contains {

			@Test
			void returns_true_for_existing_element(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				softly.assertThat(queue.contains(1)).isTrue();
				softly.assertThat(queue.contains(2)).isTrue();
				softly.assertThat(queue.contains(3)).isTrue();
			}

			@Test
			void returns_false_for_non_existing_element() {
				queue.enqueue(1);
				queue.enqueue(2);
				assertThat(queue.contains(3)).isFalse();
			}

			@Test
			void returns_false_for_empty_queue() {
				assertThat(queue.contains(1)).isFalse();
			}

			@Test
			void handles_null_in_contains() {
				queue.enqueue(1);
				queue.enqueue(null);
				queue.enqueue(2);
				assertThat(queue.contains(null)).isTrue();
			}

			@Test
			void finds_duplicates(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(1);
				softly.assertThat(queue.contains(1)).isTrue();
				softly.assertThat(queue.size()).isEqualTo(3);
			}
		}

		@Nested
		class Size {

			@Test
			void returns_zero_for_new_queue() {
				assertThat(queue.size()).isZero();
			}

			@Test
			void increments_after_enqueue() {
				queue.enqueue(1);
				assertThat(queue.size()).isEqualTo(1);
			}

			@Test
			void decrements_after_dequeue() {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.dequeue();
				assertThat(queue.size()).isEqualTo(1);
			}

			@Test
			void stress_after_1000_enqueues_and_500_dequeues(SoftAssertions softly) {
				for (int i = 0; i < 1000; i++)
					queue.enqueue(i);
				softly.assertThat(queue.size()).isEqualTo(1000);
				for (int i = 0; i < 500; i++)
					queue.dequeue();
				softly.assertThat(queue.size()).isEqualTo(500);
			}
		}

		@Nested
		class IsEmpty {

			@Test
			void true_for_new_queue() {
				assertThat(queue.isEmpty()).isTrue();
			}

			@Test
			void false_after_enqueue() {
				queue.enqueue(1);
				assertThat(queue.isEmpty()).isFalse();
			}

			@Test
			void true_after_removal_of_all_elements() {
				queue.enqueue(1);
				queue.dequeue();
				assertThat(queue.isEmpty()).isTrue();
			}
		}

		@Nested
		class Iterator {

			@Test
			void iterates_from_front_to_rear(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				java.util.Iterator<Integer> iter = queue.iterator();
				softly.assertThat(iter.hasNext()).isTrue();
				softly.assertThat(iter.next()).isEqualTo(1);
				softly.assertThat(iter.next()).isEqualTo(2);
				softly.assertThat(iter.next()).isEqualTo(3);
				softly.assertThat(iter.hasNext()).isFalse();
			}

			@Test
			void handles_empty_queue_iteration() {
				java.util.Iterator<Integer> iter = queue.iterator();
				assertThat(iter.hasNext()).isFalse();
			}

			@Test
			void throws_when_iterating_past_end() {
				queue.enqueue(1);
				java.util.Iterator<Integer> iter = queue.iterator();
				iter.next();
				assertThatThrownBy(() -> iter.next())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void iterates_over_single_element(SoftAssertions softly) {
				queue.enqueue(42);
				java.util.Iterator<Integer> iter = queue.iterator();
				softly.assertThat(iter.hasNext()).isTrue();
				softly.assertThat(iter.next()).isEqualTo(42);
				softly.assertThat(iter.hasNext()).isFalse();
			}
		}

		@Nested
		class ToArray {

			@Test
			void converts_to_array_front_to_rear(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				Object[] array = queue.toArray();
				softly.assertThat(array).hasSize(3);
				softly.assertThat(array[0]).isEqualTo(1);
				softly.assertThat(array[1]).isEqualTo(2);
				softly.assertThat(array[2]).isEqualTo(3);
			}

			@Test
			void empty_queue_returns_empty_array() {
				Object[] array = queue.toArray();
				assertThat(array).isEmpty();
			}

			@Test
			void reflects_current_state_after_dequeue(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				queue.dequeue();
				Object[] array = queue.toArray();
				softly.assertThat(array).hasSize(2);
				softly.assertThat(array[0]).isEqualTo(2);
				softly.assertThat(array[1]).isEqualTo(3);
			}
		}

		@Nested
		class ToString {

			@Test
			void generates_string_representation() {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				String str = queue.toString();
				assertThat(str)
					.isNotNull()
					.containsAnyOf("1", "2", "3");
			}

			@Test
			void handles_empty_queue() {
				assertThat(queue.toString()).isNotNull();
			}
		}
	}

	//endregion

	//region - Delete

	@Nested
	class Delete {

		@Nested
		class Dequeue {

			@Test
			void removes_and_returns_front_element(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				Integer dequeued = queue.dequeue();
				softly.assertThat(dequeued).isEqualTo(1);
				softly.assertThat(queue.peek()).isEqualTo(2);
				softly.assertThat(queue.size()).isEqualTo(1);
			}

			@Test
			void throws_on_empty_queue() {
				assertThatThrownBy(() -> queue.dequeue())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void consecutive_dequeues_clear_queue(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.dequeue();
				queue.dequeue();
				softly.assertThat(queue.isEmpty()).isTrue();
				softly.assertThatThrownBy(() -> queue.dequeue())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void can_remove_null_element() {
				queue.enqueue(null);
				Integer dequeued = queue.dequeue();
				assertThat(dequeued).isNull();
			}

			@Test
			void single_element_becomes_empty() {
				queue.enqueue(42);
				queue.dequeue();
				assertThat(queue.isEmpty()).isTrue();
			}

			@Test
			void FIFO_order(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				queue.enqueue(3);
				softly.assertThat(queue.dequeue()).isEqualTo(1);
				softly.assertThat(queue.dequeue()).isEqualTo(2);
				softly.assertThat(queue.dequeue()).isEqualTo(3);
				softly.assertThat(queue.isEmpty()).isTrue();
			}

			@Test
			void mixed_null_and_non_null_values(SoftAssertions softly) {
				queue.enqueue(null);
				queue.enqueue(1);
				queue.enqueue(2);
				softly.assertThat(queue.dequeue()).isNull();
				softly.assertThat(queue.dequeue()).isEqualTo(1);
				softly.assertThat(queue.dequeue()).isEqualTo(2);
			}

			@Test
			void maintains_FIFO_with_multiple_operations(SoftAssertions softly) {
				queue.enqueue(1);
				queue.enqueue(2);
				softly.assertThat(queue.dequeue()).isEqualTo(1);
				queue.enqueue(3);
				queue.enqueue(4);
				softly.assertThat(queue.dequeue()).isEqualTo(2);
				softly.assertThat(queue.dequeue()).isEqualTo(3);
				softly.assertThat(queue.dequeue()).isEqualTo(4);
			}
		}
	}

	//endregion

	//region - Stress

	@Nested
	class Stress {

		@Test
		void large_number_of_elements(SoftAssertions softly) {
			int count = 10000;
			for (int i = 0; i < count; i++)
				queue.enqueue(i);
			softly.assertThat(queue.size()).isEqualTo(count);
			softly.assertThat(queue.peek()).isEqualTo(0);

			for (int i = 0; i < count; i++)
				softly.assertThat(queue.dequeue()).isEqualTo(i);
			softly.assertThat(queue.isEmpty()).isTrue();
		}

		@Test
		void alternating_enqueue_dequeue(SoftAssertions softly) {
			queue.enqueue(1);
			softly.assertThat(queue.dequeue()).isEqualTo(1);
			queue.enqueue(2);
			softly.assertThat(queue.dequeue()).isEqualTo(2);
			queue.enqueue(3);
			softly.assertThat(queue.dequeue()).isEqualTo(3);
			softly.assertThat(queue.isEmpty()).isTrue();
		}

		@Test
		void circular_behavior_enqueue_after_many_dequeues(SoftAssertions softly) {
			for (int i = 0; i < 100; i++)
				queue.enqueue(i);
			for (int i = 0; i < 50; i++)
				queue.dequeue();
			for (int i = 100; i < 150; i++)
				queue.enqueue(i);

			softly.assertThat(queue.size()).isEqualTo(100);
			softly.assertThat(queue.peek()).isEqualTo(50);
		}

		@Test
		void rapid_cycles(SoftAssertions softly) {
			for (int cycle = 0; cycle < 100; cycle++) {
				queue.enqueue(cycle);
				queue.enqueue(cycle + 1);
				softly.assertThat(queue.dequeue()).isEqualTo(cycle);
				softly.assertThat(queue.dequeue()).isEqualTo(cycle + 1);
			}
			softly.assertThat(queue.isEmpty()).isTrue();
		}

		@Test
		void size_never_goes_negative(SoftAssertions softly) {
			softly.assertThat(queue.size()).isGreaterThanOrEqualTo(0);
			queue.enqueue(1);
			softly.assertThat(queue.size()).isGreaterThanOrEqualTo(0);
			queue.dequeue();
			softly.assertThat(queue.size()).isGreaterThanOrEqualTo(0);
		}

		@Test
		void consecutive_enqueue_dequeue_cycles(SoftAssertions softly) {
			for (int cycle = 0; cycle < 5; cycle++) {
				queue.enqueue(cycle);
				queue.enqueue(cycle * 10);
				softly.assertThat(queue.size()).isEqualTo(2);
				queue.dequeue();
				queue.dequeue();
				softly.assertThat(queue.isEmpty()).isTrue();
			}
		}
	}

	//endregion

	//region - Type Safety

	@Nested
	class TypeSafety {

		@Test
		void handles_String_type(SoftAssertions softly) {
			LinkedQueue<String> stringQueue = new LinkedQueue<>();
			stringQueue.enqueue("first");
			stringQueue.enqueue("second");
			stringQueue.enqueue("third");
			softly.assertThat(stringQueue.dequeue()).isEqualTo("first");
			softly.assertThat(stringQueue.dequeue()).isEqualTo("second");
			softly.assertThat(stringQueue.dequeue()).isEqualTo("third");
		}

		@Test
		void handles_custom_object(SoftAssertions softly) {
			LinkedQueue<TestObject> objQueue = new LinkedQueue<>();
			TestObject obj1 = new TestObject(1);
			TestObject obj2 = new TestObject(2);
			TestObject obj3 = new TestObject(3);
			objQueue.enqueue(obj1);
			objQueue.enqueue(obj2);
			objQueue.enqueue(obj3);
			softly.assertThat(objQueue.dequeue()).isEqualTo(obj1);
			softly.assertThat(objQueue.dequeue()).isEqualTo(obj2);
			softly.assertThat(objQueue.dequeue()).isEqualTo(obj3);
		}
	}

	//endregion

	//region - Complex Scenarios

	@Nested
	class ComplexScenarios {

		@Test
		void interleaved_operations(SoftAssertions softly) {
			queue.enqueue(1);
			queue.enqueue(2);
			softly.assertThat(queue.dequeue()).isEqualTo(1);
			queue.enqueue(3);
			queue.enqueue(4);
			softly.assertThat(queue.dequeue()).isEqualTo(2);
			softly.assertThat(queue.dequeue()).isEqualTo(3);
			queue.enqueue(5);
			softly.assertThat(queue.dequeue()).isEqualTo(4);
			softly.assertThat(queue.dequeue()).isEqualTo(5);
		}

		@Test
		void integrity_after_exception(SoftAssertions softly) {
			queue.enqueue(1);
			queue.enqueue(2);
			queue.dequeue();
			queue.dequeue();
			assertThatThrownBy(() -> queue.dequeue())
				.isInstanceOf(IndexOutOfBoundsException.class);

			queue.enqueue(3);
			softly.assertThat(queue.peek()).isEqualTo(3);
		}
	}

	//endregion

	//region - FIFO Behavior

	@Nested
	class FIFOBehavior {

		@Test
		void strict_FIFO_ordering(SoftAssertions softly) {
			for (int i = 1; i <= 5; i++)
				queue.enqueue(i);

			for (int i = 1; i <= 5; i++)
				softly.assertThat(queue.dequeue()).isEqualTo(i);
		}

		@Test
		void FIFO_with_interleaved_operations(SoftAssertions softly) {
			queue.enqueue(1);
			queue.enqueue(2);
			softly.assertThat(queue.dequeue()).isEqualTo(1);
			queue.enqueue(3);
			softly.assertThat(queue.dequeue()).isEqualTo(2);
			softly.assertThat(queue.dequeue()).isEqualTo(3);
		}

		@Test
		void peek_always_returns_oldest(SoftAssertions softly) {
			queue.enqueue(1);
			softly.assertThat(queue.peek()).isEqualTo(1);
			queue.enqueue(2);
			softly.assertThat(queue.peek()).isEqualTo(1);
			queue.enqueue(3);
			softly.assertThat(queue.peek()).isEqualTo(1);
		}

		@Test
		void maintains_insertion_order_with_duplicates(SoftAssertions softly) {
			queue.enqueue(5);
			queue.enqueue(5);
			queue.enqueue(5);

			softly.assertThat(queue.dequeue()).isEqualTo(5);
			softly.assertThat(queue.dequeue()).isEqualTo(5);
			softly.assertThat(queue.dequeue()).isEqualTo(5);
		}
	}

	//endregion

	private static class TestObject {
		private final int value;

		TestObject(int value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			TestObject that = (TestObject) o;
			return value == that.value;
		}

		@Override
		public int hashCode() {
			return value;
		}
	}
}
