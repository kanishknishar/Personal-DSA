package data_structures.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings({"ConstantConditions", "unused"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class ArrayQueueTest {

	ArrayQueue<Integer> queue;

	@BeforeEach
	void setUp() {
		queue = new ArrayQueue<>();
	}

	//region Constructor
	@Nested
	class Constructor {

		@Nested
		class Default_Constructor {

			@Test
			void creates_empty_queue(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>();
					softly.assertThat(q.size()).isZero();
					softly.assertThat(q.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

		}

		@Nested
		class Capacity_Constructor {

			@Test
			void creates_queue_with_specified_capacity(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>(20);
					softly.assertThat(q.size()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_on_negative_capacity() {
				assertThatThrownBy(() -> new ArrayQueue<Integer>(-1))
					.isInstanceOf(IllegalArgumentException.class);
			}

			@Test
			void throws_on_zero_capacity() {
				assertThatThrownBy(() -> new ArrayQueue<Integer>(0))
					.isInstanceOf(IllegalArgumentException.class);
			}
		}
	}
	//endregion

	//region Enqueue
	@Nested
	class Enqueue {

		@Nested
		class Size_0_Empty {

			@Test
			void enqueue_to_empty_queue(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					softly.assertThat(queue.size()).isEqualTo(1);
					softly.assertThat(queue.peek()).isEqualTo(42);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void enqueue_to_single_element_queue(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(1);
					queue.enqueue(2);
					softly.assertThat(queue.size()).isEqualTo(2);
					softly.assertThat(queue.peek()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void enqueue_maintains_FIFO_order(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(1);
					queue.enqueue(2);
					queue.enqueue(3);
					softly.assertThat(queue.dequeue()).isEqualTo(1);
					softly.assertThat(queue.dequeue()).isEqualTo(2);
					softly.assertThat(queue.dequeue()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void enqueue_stress_1000_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 1000; i++) queue.enqueue(i);
					softly.assertThat(queue.size()).isEqualTo(1000);
					softly.assertThat(queue.peek()).isEqualTo(0);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Resize_Behavior {

			@Test
			void triggers_resize_when_full(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>(2);
					q.enqueue(1);
					q.enqueue(2);
					q.enqueue(3);
					softly.assertThat(q.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Circular_Buffer {

			@Test
			void handles_circular_wraparound(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>(4);
					q.enqueue(1);
					q.enqueue(2);
					q.dequeue();
					q.dequeue();
					q.enqueue(3);
					q.enqueue(4);
					q.enqueue(5);
					softly.assertThat(q.dequeue()).isEqualTo(3);
					softly.assertThat(q.dequeue()).isEqualTo(4);
					softly.assertThat(q.dequeue()).isEqualTo(5);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void rear_wraps_to_front(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>(3);
					q.enqueue(1);
					q.enqueue(2);
					q.dequeue();
					q.enqueue(3);
					q.enqueue(4);
					softly.assertThat(q.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Null_And_Duplicates {

			@Test
			void enqueues_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(null);
					softly.assertThat(queue.size()).isEqualTo(1);
					softly.assertThat(queue.peek()).isNull();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void enqueues_duplicate_values(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					queue.enqueue(42);
					queue.enqueue(42);
					softly.assertThat(queue.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Dequeue
	@Nested
	class Dequeue {

		@Nested
		class Size_0_Empty {

			@Test
			void throws_on_empty_queue() {
				assertThatThrownBy(() -> queue.dequeue())
					.isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void removes_only_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					Integer dequeued = queue.dequeue();
					softly.assertThat(dequeued).isEqualTo(42);
					softly.assertThat(queue.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void removes_front_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(1);
					queue.enqueue(2);
					Integer dequeued = queue.dequeue();
					softly.assertThat(dequeued).isEqualTo(1);
					softly.assertThat(queue.peek()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void consecutive_dequeues_until_empty(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 100; i++) queue.enqueue(i);
					for (int i = 0; i < 100; i++) queue.dequeue();
					softly.assertThat(queue.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Circular_Buffer {

			@Test
			void handles_circular_wraparound(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>(4);
					q.enqueue(1);
					q.enqueue(2);
					q.enqueue(3);
					q.dequeue();
					q.enqueue(4);
					q.enqueue(5);
					softly.assertThat(q.dequeue()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

	}
	//endregion

	//region Peek
	@Nested
	class Peek {

		@Nested
		class Size_0_Empty {

			@Test
			void throws_on_empty_queue() {
				assertThatThrownBy(() -> queue.peek())
					.isInstanceOf(UnsupportedOperationException.class);
			}



			@Test
			void peekRear_throws_on_empty() {
				assertThatThrownBy(() -> queue.peekRear())
					.isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void peek_equals_peekRear(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					softly.assertThat(queue.peek()).isEqualTo(42);
					softly.assertThat(queue.peekRear()).isEqualTo(42);
					softly.assertThat(queue.peek()).isEqualTo(queue.peekRear());
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void peek_returns_first_enqueued(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(1);
					queue.enqueue(2);
					queue.enqueue(3);
					softly.assertThat(queue.peek()).isEqualTo(1);
					softly.assertThat(queue.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void peekRear_returns_last_enqueued(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(1);
					queue.enqueue(2);
					queue.enqueue(3);
					softly.assertThat(queue.peekRear()).isEqualTo(3);
					softly.assertThat(queue.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void multiple_peeks_same_result(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					softly.assertThat(queue.peek()).isEqualTo(queue.peek());
					softly.assertThat(queue.size()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Utilities
	@Nested
	class Utilities {

		@Nested
		class Size_And_Capacity {

			@Test
			void size_returns_element_count(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					softly.assertThat(queue.size()).isZero();
					queue.enqueue(1);
					softly.assertThat(queue.size()).isEqualTo(1);
					queue.enqueue(2);
					softly.assertThat(queue.size()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void isEmpty_returns_true_when_empty(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					softly.assertThat(queue.isEmpty()).isTrue();
					queue.enqueue(1);
					softly.assertThat(queue.isEmpty()).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Contains {

			@Test
			void contains_returns_true_when_present(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					softly.assertThat(queue.contains(42)).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void contains_returns_false_when_absent(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(42);
					softly.assertThat(queue.contains(99)).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Clear {

			@Test
			void clear_removes_all_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 50; i++) queue.enqueue(i);
					queue.clear();
					softly.assertThat(queue.isEmpty()).isTrue();
					softly.assertThat(queue.size()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void clear_on_empty_queue(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.clear();
					softly.assertThat(queue.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class ToArray {

			@Test
			void toArray_returns_elements_front_to_rear(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					queue.enqueue(1);
					queue.enqueue(2);
					queue.enqueue(3);
					Object[] result = queue.toArray();
					softly.assertThat(result).containsExactly(1, 2, 3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void toArray_empty_returns_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					Object[] result = queue.toArray();
					softly.assertThat(result).isEmpty();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void toArray_handles_wrapped_queue(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayQueue<Integer> q = new ArrayQueue<>(4);
					q.enqueue(1);
					q.enqueue(2);
					q.dequeue();
					q.enqueue(3);
					q.enqueue(4);
					Object[] result = q.toArray();
					softly.assertThat(result).containsExactly(2, 3, 4);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Integration
	@Nested
	class Integration {

		@Test
		void enqueue_dequeue_interleaved(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 50; i++) {
					queue.enqueue(i);
					queue.enqueue(i + 100);
					queue.dequeue();
				}
				softly.assertThat(queue.size()).isEqualTo(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void stress_mixed_operations(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 100; i++) queue.enqueue(i);
				for (int i = 0; i < 50; i++) queue.dequeue();
				for (int i = 0; i < 25; i++) queue.enqueue(i + 1000);
				softly.assertThat(queue.size()).isEqualTo(75);
				softly.assertThat(queue.peek()).isEqualTo(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void rebuild_after_clear(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 100; i++) queue.enqueue(i);
				queue.clear();
				for (int i = 0; i < 50; i++) queue.enqueue(i + 1000);
				softly.assertThat(queue.peek()).isEqualTo(1000);
				softly.assertThat(queue.peekRear()).isEqualTo(1049);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void FIFO_verification(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
				for (Integer i : input) queue.enqueue(i);
				List<Integer> output = new ArrayList<>();
				while (!queue.isEmpty()) output.add(queue.dequeue());
				softly.assertThat(output).containsExactlyElementsOf(input);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void circular_stress_test(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				ArrayQueue<Integer> q = new ArrayQueue<>(8);
				for (int round = 0; round < 100; round++) {
					for (int i = 0; i < 5; i++) q.enqueue(round * 10 + i);
					for (int i = 0; i < 3; i++) q.dequeue();
				}
				softly.assertThat(q.size()).isEqualTo(200);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}
	//endregion
}
