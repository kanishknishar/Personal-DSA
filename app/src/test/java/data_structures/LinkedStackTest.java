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

import data_structures.stack.LinkedStack;

@SuppressWarnings({"DataFlowIssue", "ConstantConditions"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class LinkedStackTest {

	LinkedStack<Integer> stack;

	@BeforeEach
	void init() {
		stack = new LinkedStack<>();
	}

	//region - Add

	@Nested
	class Add {

		@Nested
		class Push {

			@Test
			void to_empty_stack(SoftAssertions softly) {
				stack.push(42);
				softly.assertThat(stack.peek()).isEqualTo(42);
				softly.assertThat(stack.size()).isEqualTo(1);
			}

			@Test
			void to_non_empty_stack(SoftAssertions softly) {
				stack.push(1);
				stack.push(2);
				softly.assertThat(stack.peek()).isEqualTo(2);
				softly.assertThat(stack.size()).isEqualTo(2);
			}

			@Test
			void maintains_LIFO_order(SoftAssertions softly) {
				stack.push(1);
				stack.push(2);
				stack.push(3);
				softly.assertThat(stack.peek()).isEqualTo(3);
			}

			@Test
			void handles_null_value(SoftAssertions softly) {
				stack.push(null);
				softly.assertThat(stack.isEmpty()).isFalse();
				softly.assertThat(stack.size()).isEqualTo(1);
				softly.assertThat(stack.peek()).isNull();
			}

			@Test
			void stress_1000_elements(SoftAssertions softly) {
				for (int i = 0; i < 1000; i++)
					stack.push(i);
				softly.assertThat(stack.size()).isEqualTo(1000);
				softly.assertThat(stack.peek()).isEqualTo(999);
			}

			@Test
			void handles_duplicate_elements() {
				stack.push(1);
				stack.push(1);
				stack.push(1);
				assertThat(stack.size()).isEqualTo(3);
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
			void returns_top_element() {
				stack.push(1);
				stack.push(2);
				assertThat(stack.peek()).isEqualTo(2);
			}

			@Test
			void does_not_remove_element(SoftAssertions softly) {
				stack.push(1);
				stack.push(2);
				softly.assertThat(stack.peek()).isEqualTo(2);
				softly.assertThat(stack.peek()).isEqualTo(2);
				softly.assertThat(stack.size()).isEqualTo(2);
			}

			@Test
			void throws_on_empty_stack() {
				assertThatThrownBy(() -> stack.peek())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void returns_null_when_top_is_null() {
				stack.push(null);
				assertThat(stack.peek()).isNull();
			}

			@Test
			void handles_max_integer_value() {
				stack.push(Integer.MAX_VALUE);
				assertThat(stack.peek()).isEqualTo(Integer.MAX_VALUE);
			}

			@Test
			void handles_min_integer_value() {
				stack.push(Integer.MIN_VALUE);
				assertThat(stack.peek()).isEqualTo(Integer.MIN_VALUE);
			}
		}

		@Nested
		class Search {

			@Test
			void find_element_at_top() {
				stack.push(1);
				stack.push(2);
				stack.push(3);
				assertThat(stack.search(3)).isEqualTo(1);
			}

			@Test
			void find_element_in_middle() {
				stack.push(1);
				stack.push(2);
				stack.push(3);
				assertThat(stack.search(2)).isEqualTo(2);
			}

			@Test
			void find_element_at_bottom() {
				stack.push(1);
				stack.push(2);
				stack.push(3);
				assertThat(stack.search(1)).isEqualTo(3);
			}

			@Test
			void returns_minus_one_for_non_existent() {
				stack.push(1);
				stack.push(2);
				assertThat(stack.search(99)).isEqualTo(-1);
			}

			@Test
			void returns_minus_one_for_empty_stack() {
				assertThat(stack.search(1)).isEqualTo(-1);
			}

			@Test
			void search_for_null_value() {
				stack.push(1);
				stack.push(null);
				stack.push(2);
				assertThat(stack.search(null)).isEqualTo(2);
			}

			@Test
			void find_first_occurrence_from_top() {
				stack.push(1);
				stack.push(2);
				stack.push(1);
				assertThat(stack.search(1)).isEqualTo(1);
			}
		}

		@Nested
		class Size {

			@Test
			void returns_zero_for_new_stack() {
				assertThat(stack.size()).isZero();
			}

			@Test
			void increments_after_push() {
				stack.push(1);
				assertThat(stack.size()).isEqualTo(1);
			}

			@Test
			void decrements_after_pop() {
				stack.push(1);
				stack.push(2);
				stack.pop();
				assertThat(stack.size()).isEqualTo(1);
			}

			@Test
			void stress_after_1000_pushes_and_500_pops(SoftAssertions softly) {
				for (int i = 0; i < 1000; i++)
					stack.push(i);
				softly.assertThat(stack.size()).isEqualTo(1000);
				for (int i = 0; i < 500; i++)
					stack.pop();
				softly.assertThat(stack.size()).isEqualTo(500);
			}
		}

		@Nested
		class IsEmpty {

			@Test
			void true_for_new_stack() {
				assertThat(stack.isEmpty()).isTrue();
			}

			@Test
			void false_after_push() {
				stack.push(1);
				assertThat(stack.isEmpty()).isFalse();
			}

			@Test
			void true_after_removal_of_all_elements() {
				stack.push(1);
				stack.pop();
				assertThat(stack.isEmpty()).isTrue();
			}
		}

		@Nested
		class ToString {

			@Test
			void generates_string_representation() {
				stack.push(1);
				stack.push(2);
				stack.push(3);
				String str = stack.toString();
				assertThat(str)
					.isNotNull()
					.containsAnyOf("1", "2", "3");
			}

			@Test
			void handles_empty_stack() {
				assertThat(stack.toString()).isNotNull();
			}
		}
	}

	//endregion

	//region - Delete

	@Nested
	class Delete {

		@Nested
		class Pop {

			@Test
			void removes_and_returns_top_element(SoftAssertions softly) {
				stack.push(1);
				stack.push(2);
				Integer popped = stack.pop();
				softly.assertThat(popped).isEqualTo(2);
				softly.assertThat(stack.peek()).isEqualTo(1);
				softly.assertThat(stack.size()).isEqualTo(1);
			}

			@Test
			void throws_on_empty_stack() {
				assertThatThrownBy(() -> stack.pop())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void consecutive_pops_clear_stack(SoftAssertions softly) {
				stack.push(1);
				stack.push(2);
				stack.pop();
				stack.pop();
				softly.assertThat(stack.isEmpty()).isTrue();
				softly.assertThatThrownBy(() -> stack.pop())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void can_remove_null_element() {
				stack.push(null);
				Integer popped = stack.pop();
				assertThat(popped).isNull();
			}

			@Test
			void single_element_becomes_empty() {
				stack.push(42);
				stack.pop();
				assertThat(stack.isEmpty()).isTrue();
			}

			@Test
			void LIFO_order(SoftAssertions softly) {
				stack.push(1);
				stack.push(2);
				stack.push(3);
				softly.assertThat(stack.pop()).isEqualTo(3);
				softly.assertThat(stack.pop()).isEqualTo(2);
				softly.assertThat(stack.pop()).isEqualTo(1);
			}

			@Test
			void push_after_pop_to_empty(SoftAssertions softly) {
				stack.push(1);
				stack.pop();
				stack.push(2);
				softly.assertThat(stack.peek()).isEqualTo(2);
				softly.assertThat(stack.size()).isEqualTo(1);
			}

			@Test
			void mixed_null_and_non_null_values(SoftAssertions softly) {
				stack.push(1);
				stack.push(null);
				stack.push(2);
				softly.assertThat(stack.pop()).isEqualTo(2);
				softly.assertThat(stack.pop()).isNull();
				softly.assertThat(stack.pop()).isEqualTo(1);
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
				stack.push(i);
			softly.assertThat(stack.size()).isEqualTo(count);
			softly.assertThat(stack.peek()).isEqualTo(count - 1);

			for (int i = count - 1; i >= 0; i--)
				softly.assertThat(stack.pop()).isEqualTo(i);
			softly.assertThat(stack.isEmpty()).isTrue();
		}

		@Test
		void alternating_push_pop(SoftAssertions softly) {
			stack.push(1);
			softly.assertThat(stack.pop()).isEqualTo(1);
			stack.push(2);
			softly.assertThat(stack.pop()).isEqualTo(2);
			stack.push(3);
			softly.assertThat(stack.pop()).isEqualTo(3);
			softly.assertThat(stack.isEmpty()).isTrue();
		}

		@Test
		void size_never_goes_negative(SoftAssertions softly) {
			softly.assertThat(stack.size()).isGreaterThanOrEqualTo(0);
			stack.push(1);
			softly.assertThat(stack.size()).isGreaterThanOrEqualTo(0);
			stack.pop();
			softly.assertThat(stack.size()).isGreaterThanOrEqualTo(0);
		}

		@Test
		void consecutive_push_pop_cycles(SoftAssertions softly) {
			for (int cycle = 0; cycle < 5; cycle++) {
				stack.push(cycle);
				stack.push(cycle * 10);
				softly.assertThat(stack.size()).isEqualTo(2);
				stack.pop();
				stack.pop();
				softly.assertThat(stack.isEmpty()).isTrue();
			}
		}
	}

	//endregion

	//region - Type Safety

	@Nested
	class TypeSafety {

		@Test
		void handles_String_type(SoftAssertions softly) {
			LinkedStack<String> stringStack = new LinkedStack<>();
			stringStack.push("hello");
			stringStack.push("world");
			softly.assertThat(stringStack.pop()).isEqualTo("world");
			softly.assertThat(stringStack.pop()).isEqualTo("hello");
		}

		@Test
		void handles_custom_object(SoftAssertions softly) {
			LinkedStack<TestObject> objStack = new LinkedStack<>();
			TestObject obj1 = new TestObject(1);
			TestObject obj2 = new TestObject(2);
			objStack.push(obj1);
			objStack.push(obj2);
			softly.assertThat(objStack.pop()).isEqualTo(obj2);
			softly.assertThat(objStack.pop()).isEqualTo(obj1);
		}
	}

	//endregion

	//region - LIFO Behavior

	@Nested
	class LIFOBehavior {

		@Test
		void strict_LIFO_ordering(SoftAssertions softly) {
			for (int i = 1; i <= 5; i++)
				stack.push(i);

			for (int i = 5; i >= 1; i--)
				softly.assertThat(stack.pop()).isEqualTo(i);
		}

		@Test
		void LIFO_with_interleaved_operations(SoftAssertions softly) {
			stack.push(1);
			stack.push(2);
			softly.assertThat(stack.pop()).isEqualTo(2);
			stack.push(3);
			softly.assertThat(stack.pop()).isEqualTo(3);
			softly.assertThat(stack.pop()).isEqualTo(1);
		}

		@Test
		void peek_always_returns_most_recent(SoftAssertions softly) {
			stack.push(1);
			softly.assertThat(stack.peek()).isEqualTo(1);
			stack.push(2);
			softly.assertThat(stack.peek()).isEqualTo(2);
			stack.push(3);
			softly.assertThat(stack.peek()).isEqualTo(3);
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
