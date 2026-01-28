package data_structures.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
class ArrayStackTest {

	ArrayStack<Integer> stack;

	@BeforeEach
	void setUp() {
		stack = new ArrayStack<>();
	}

	//region Constructor
	@Nested
	class Constructor {

		@Nested
		class Default_Constructor {

			@Test
			void creates_empty_stack(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayStack<Integer> s = new ArrayStack<>();
					softly.assertThat(s.size()).isZero();
					softly.assertThat(s.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

		}

		@Nested
		class Capacity_Constructor {

			@Test
			void creates_stack_with_specified_capacity(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayStack<Integer> s = new ArrayStack<>(20);
					softly.assertThat(s.size()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_on_negative_capacity() {
				assertThatThrownBy(() -> new ArrayStack<Integer>(-1))
					.isInstanceOf(IllegalArgumentException.class);
			}

			@Test
			void throws_on_zero_capacity() {
				assertThatThrownBy(() -> new ArrayStack<Integer>(0))
					.isInstanceOf(IllegalArgumentException.class);
			}
		}
	}
	//endregion

	//region Push
	@Nested
	class Push {

		@Nested
		class Size_0_Empty {

			@Test
			void push_to_empty_stack(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					softly.assertThat(stack.size()).isEqualTo(1);
					softly.assertThat(stack.peek()).isEqualTo(42);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void push_to_single_element_stack(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					softly.assertThat(stack.size()).isEqualTo(2);
					softly.assertThat(stack.peek()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void push_maintains_LIFO_order(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					stack.push(3);
					softly.assertThat(stack.pop()).isEqualTo(3);
					softly.assertThat(stack.pop()).isEqualTo(2);
					softly.assertThat(stack.pop()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void push_stress_1000_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 1000; i++) stack.push(i);
					softly.assertThat(stack.size()).isEqualTo(1000);
					softly.assertThat(stack.peek()).isEqualTo(999);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Resize_Behavior {

			@Test
			void triggers_resize_when_full(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					ArrayStack<Integer> s = new ArrayStack<>(2);
					s.push(1);
					s.push(2);
					s.push(3);
					softly.assertThat(s.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Null_And_Duplicates {

			@Test
			void pushes_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(null);
					softly.assertThat(stack.size()).isEqualTo(1);
					softly.assertThat(stack.peek()).isNull();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void pushes_duplicate_values(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					stack.push(42);
					stack.push(42);
					softly.assertThat(stack.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class PushAll {

			@Test
			void pushes_collection_to_empty_stack(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.pushAll(Arrays.asList(1, 2, 3));
					softly.assertThat(stack.size()).isEqualTo(3);
					softly.assertThat(stack.peek()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void pushes_empty_collection_no_change(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.pushAll(Collections.emptyList());
					softly.assertThat(stack.size()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_on_null_collection() {
				assertThatThrownBy(() -> stack.pushAll(null))
					.isInstanceOf(NullPointerException.class);
			}
		}
	}
	//endregion

	//region Pop
	@Nested
	class Pop {

		@Nested
		class Size_0_Empty {

			@Test
			void throws_on_empty_stack() {
				assertThatThrownBy(() -> stack.pop())
					.isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void removes_only_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					Integer popped = stack.pop();
					softly.assertThat(popped).isEqualTo(42);
					softly.assertThat(stack.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void removes_top_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					Integer popped = stack.pop();
					softly.assertThat(popped).isEqualTo(2);
					softly.assertThat(stack.peek()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void consecutive_pops_until_empty(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 100; i++) stack.push(i);
					for (int i = 0; i < 100; i++) stack.pop();
					softly.assertThat(stack.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class PopN {

			@Test
			void pops_n_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 10; i++) stack.push(i);
					List<Integer> popped = stack.popN(5);
					softly.assertThat(popped).containsExactly(9, 8, 7, 6, 5);
					softly.assertThat(stack.size()).isEqualTo(5);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_when_n_exceeds_size() {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.popN(5);
				}).isInstanceOf(IllegalArgumentException.class);
			}

			@Test
			void pop_zero_returns_empty_list(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					List<Integer> popped = stack.popN(0);
					softly.assertThat(popped).isEmpty();
					softly.assertThat(stack.size()).isEqualTo(1);
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
			void throws_on_empty_stack() {
				assertThatThrownBy(() -> stack.peek())
					.isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void returns_only_element_without_removing(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					softly.assertThat(stack.peek()).isEqualTo(42);
					softly.assertThat(stack.size()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void returns_top_element_without_removing(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					stack.push(3);
					softly.assertThat(stack.peek()).isEqualTo(3);
					softly.assertThat(stack.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void multiple_peeks_same_result(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					softly.assertThat(stack.peek()).isEqualTo(stack.peek());
					softly.assertThat(stack.size()).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class PeekAt {

			@Test
			void peeks_element_at_distance_from_top(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 10; i++) stack.push(i);
					softly.assertThat(stack.peekAt(0)).isEqualTo(9);
					softly.assertThat(stack.peekAt(1)).isEqualTo(8);
					softly.assertThat(stack.peekAt(2)).isEqualTo(7);
					softly.assertThat(stack.size()).isEqualTo(10);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_when_distance_exceeds_size() {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.peekAt(5);
				}).isInstanceOf(IllegalArgumentException.class);
			}
		}
	}
	//endregion

	//region Search
	@Nested
	class Search {

		@Nested
		class Found {

			@Test
			void returns_position_from_top(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					stack.push(3);
					softly.assertThat(stack.search(3)).isEqualTo(1);
					softly.assertThat(stack.search(2)).isEqualTo(2);
					softly.assertThat(stack.search(1)).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void finds_first_occurrence_of_duplicate(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					stack.push(1);
					softly.assertThat(stack.search(1)).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void finds_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(null);
					stack.push(2);
					softly.assertThat(stack.search(null)).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Not_Found {

			@Test
			void returns_negative_one_when_not_found(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					softly.assertThat(stack.search(99)).isEqualTo(-1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void returns_negative_one_on_empty_stack(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					softly.assertThat(stack.search(1)).isEqualTo(-1);
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
					softly.assertThat(stack.size()).isZero();
					stack.push(1);
					softly.assertThat(stack.size()).isEqualTo(1);
					stack.push(2);
					softly.assertThat(stack.size()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void isEmpty_returns_true_when_empty(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					softly.assertThat(stack.isEmpty()).isTrue();
					stack.push(1);
					softly.assertThat(stack.isEmpty()).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Contains {

			@Test
			void contains_returns_true_when_present(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					softly.assertThat(stack.contains(42)).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void contains_returns_false_when_absent(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(42);
					softly.assertThat(stack.contains(99)).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Clear {

			@Test
			void clear_removes_all_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 50; i++) stack.push(i);
					stack.clear();
					softly.assertThat(stack.isEmpty()).isTrue();
					softly.assertThat(stack.size()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void clear_on_empty_stack(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.clear();
					softly.assertThat(stack.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class ToArray {

			@Test
			void toArray_returns_elements_bottom_to_top(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					stack.push(1);
					stack.push(2);
					stack.push(3);
					Object[] result = stack.toArray();
					softly.assertThat(result).containsExactly(1, 2, 3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void toArray_empty_returns_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					Object[] result = stack.toArray();
					softly.assertThat(result).isEmpty();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Integration
	@Nested
	class Integration {

		@Test
		void push_pop_interleaved(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 50; i++) {
					stack.push(i);
					stack.push(i + 100);
					stack.pop();
				}
				softly.assertThat(stack.size()).isEqualTo(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void stress_mixed_operations(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 100; i++) stack.push(i);
				for (int i = 0; i < 50; i++) stack.pop();
				for (int i = 0; i < 25; i++) stack.push(i + 1000);
				softly.assertThat(stack.size()).isEqualTo(75);
				softly.assertThat(stack.peek()).isEqualTo(1024);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void rebuild_after_clear(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 100; i++) stack.push(i);
				stack.clear();
				for (int i = 0; i < 50; i++) stack.push(i + 1000);
				softly.assertThat(stack.peek()).isEqualTo(1049);
				softly.assertThat(stack.size()).isEqualTo(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void LIFO_verification(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
				for (Integer i : input) stack.push(i);
				List<Integer> output = new ArrayList<>();
				while (!stack.isEmpty()) output.add(stack.pop());
				softly.assertThat(output).containsExactly(5, 4, 3, 2, 1);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void reverse_collection_pattern(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				int[] original = {1, 2, 3, 4, 5};
				for (int val : original) stack.push(val);
				int[] reversed = new int[original.length];
				for (int i = 0; i < reversed.length; i++) reversed[i] = stack.pop();
				softly.assertThat(reversed).containsExactly(5, 4, 3, 2, 1);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void undo_operation_simulation(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				stack.push(10);
				stack.push(20);
				stack.push(30);
				int lastAction = stack.pop();
				softly.assertThat(lastAction).as("Undo last action").isEqualTo(30);
				softly.assertThat(stack.peek()).as("Current state").isEqualTo(20);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}
	//endregion
}
