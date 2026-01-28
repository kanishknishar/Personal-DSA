package data_structures.stack;

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

@SuppressWarnings({"DataFlowIssue", "ConstantConditions", "unused"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class LinkedStackTest {

    LinkedStack<Integer> stack;

    @BeforeEach
    void init() {
        stack = new LinkedStack<>();
    }

    //region - Push
    @Nested
    class Push {

        @Nested
        class Size_0_Empty {

            @Test
            void to_empty_stack(SoftAssertions softly) {
                stack.push(42);
                softly.assertThat(stack.peek()).isEqualTo(42);
                softly.assertThat(stack.size()).isEqualTo(1);
            }

            @Test
            void handles_null_value(SoftAssertions softly) {
                stack.push(null);
                softly.assertThat(stack.isEmpty()).isFalse();
                softly.assertThat(stack.size()).isEqualTo(1);
                softly.assertThat(stack.peek()).isNull();
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void to_single_element_stack(SoftAssertions softly) {
                stack.push(1);
                stack.push(2);
                softly.assertThat(stack.peek()).isEqualTo(2);
                softly.assertThat(stack.size()).isEqualTo(2);
            }
        }

        @Nested
        class Size_N_Multiple {

            @Test
            void maintains_LIFO_order(SoftAssertions softly) {
                stack.push(1);
                stack.push(2);
                stack.push(3);
                softly.assertThat(stack.peek()).isEqualTo(3);
            }

            @Test
            void handles_duplicate_elements() {
                stack.push(1);
                stack.push(1);
                stack.push(1);
                assertThat(stack.size()).isEqualTo(3);
            }

            @Test
            void stress_1000_elements(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++)
                    stack.push(i);
                softly.assertThat(stack.size()).isEqualTo(1000);
                softly.assertThat(stack.peek()).isEqualTo(999);
            }
        }

        @Nested
        class Boundary_Values {

            @Test
            void handles_max_integer() {
                stack.push(Integer.MAX_VALUE);
                assertThat(stack.peek()).isEqualTo(Integer.MAX_VALUE);
            }

            @Test
            void handles_min_integer() {
                stack.push(Integer.MIN_VALUE);
                assertThat(stack.peek()).isEqualTo(Integer.MIN_VALUE);
            }
        }
    }
    //endregion

    //region - Pop
    @Nested
    class Pop {

        @Nested
        class Size_0_Empty {

            @Test
            void throws_on_empty_stack() {
                assertThatThrownBy(() -> stack.pop())
                    .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void single_element_becomes_empty() {
                stack.push(42);
                stack.pop();
                assertThat(stack.isEmpty()).isTrue();
            }

            @Test
            void can_remove_null_element() {
                stack.push(null);
                Integer popped = stack.pop();
                assertThat(popped).isNull();
            }
        }

        @Nested
        class Size_N_Multiple {

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
            void LIFO_order_large(SoftAssertions softly) {
                for (int i = 0; i < 100; i++)
                    stack.push(i);
                for (int i = 99; i >= 0; i--)
                    softly.assertThat(stack.pop()).isEqualTo(i);
            }
        }

        @Nested
        class Null_And_Recovery {

            @Test
            void mixed_null_and_non_null_values(SoftAssertions softly) {
                stack.push(1);
                stack.push(null);
                stack.push(2);
                softly.assertThat(stack.pop()).isEqualTo(2);
                softly.assertThat(stack.pop()).isNull();
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
        }
    }
    //endregion

    //region - Peek
    @Nested
    class Peek {

        @Nested
        class Size_0_Empty {

            @Test
            void throws_on_empty_stack() {
                assertThatThrownBy(() -> stack.peek())
                    .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void returns_top_element() {
                stack.push(42);
                assertThat(stack.peek()).isEqualTo(42);
            }

            @Test
            void returns_null_when_top_is_null() {
                stack.push(null);
                assertThat(stack.peek()).isNull();
            }
        }

        @Nested
        class Size_N_Multiple {

            @Test
            void does_not_remove_element(SoftAssertions softly) {
                stack.push(1);
                stack.push(2);
                softly.assertThat(stack.peek()).isEqualTo(2);
                softly.assertThat(stack.peek()).isEqualTo(2);
                softly.assertThat(stack.size()).isEqualTo(2);
            }

            @Test
            void peek_always_returns_most_recent(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    stack.push(i);
                    softly.assertThat(stack.peek()).isEqualTo(i);
                }
            }
        }
    }
    //endregion

    //region - Search
    @Nested
    class Search {

        @Nested
        class Size_0_Empty {

            @Test
            void returns_minus_one_for_empty_stack() {
                assertThat(stack.search(1)).isEqualTo(-1);
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void find_only_element() {
                stack.push(42);
                assertThat(stack.search(42)).isEqualTo(1);
            }

            @Test
            void returns_minus_one_for_non_existent() {
                stack.push(42);
                assertThat(stack.search(99)).isEqualTo(-1);
            }
        }

        @Nested
        class Size_N_Multiple {

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
            void find_first_occurrence_from_top() {
                stack.push(1);
                stack.push(2);
                stack.push(1);
                assertThat(stack.search(1)).isEqualTo(1);
            }
        }

        @Nested
        class Null_Search {

            @Test
            void search_for_null_value() {
                stack.push(1);
                stack.push(null);
                stack.push(2);
                assertThat(stack.search(null)).isEqualTo(2);
            }
        }
    }
    //endregion

    //region - Size
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
        void stress_after_operations(SoftAssertions softly) {
            for (int i = 0; i < 1000; i++)
                stack.push(i);
            softly.assertThat(stack.size()).isEqualTo(1000);
            for (int i = 0; i < 500; i++)
                stack.pop();
            softly.assertThat(stack.size()).isEqualTo(500);
        }
    }
    //endregion

    //region - IsEmpty
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
    //endregion

    //region - Integration
    @Nested
    class Integration {

        @Nested
        class LIFO_Behavior {

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
        }

        @Nested
        class Stress_Tests {

            @Test
            void large_number_of_elements(SoftAssertions softly) {
                int count = 10000;
                for (int i = 0; i < count; i++)
                    stack.push(i);
                softly.assertThat(stack.size()).isEqualTo(count);
                for (int i = count - 1; i >= 0; i--)
                    softly.assertThat(stack.pop()).isEqualTo(i);
                softly.assertThat(stack.isEmpty()).isTrue();
            }

            @Test
            void alternating_push_pop(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    stack.push(i);
                    softly.assertThat(stack.pop()).isEqualTo(i);
                }
                softly.assertThat(stack.isEmpty()).isTrue();
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

        @Nested
        class Type_Safety {

            @Test
            void handles_String_type(SoftAssertions softly) {
                LinkedStack<String> stringStack = new LinkedStack<>();
                stringStack.push("hello");
                stringStack.push("world");
                softly.assertThat(stringStack.pop()).isEqualTo("world");
                softly.assertThat(stringStack.pop()).isEqualTo("hello");
            }
        }
    }
    //endregion
}
