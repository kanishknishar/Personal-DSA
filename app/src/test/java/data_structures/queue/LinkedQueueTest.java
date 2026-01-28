package data_structures.queue;

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
class LinkedQueueTest {

    LinkedQueue<Integer> queue;

    @BeforeEach
    void init() {
        queue = new LinkedQueue<>();
    }

    //region - Enqueue
    @Nested
    class Enqueue {

        @Nested
        class Size_0_Empty {

            @Test
            void to_empty_queue(SoftAssertions softly) {
                queue.enqueue(42);
                softly.assertThat(queue.peek()).isEqualTo(42);
                softly.assertThat(queue.size()).isEqualTo(1);
            }

            @Test
            void handles_null_value(SoftAssertions softly) {
                queue.enqueue(null);
                softly.assertThat(queue.isEmpty()).isFalse();
                softly.assertThat(queue.size()).isEqualTo(1);
                softly.assertThat(queue.peek()).isNull();
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void to_single_element_queue(SoftAssertions softly) {
                queue.enqueue(1);
                queue.enqueue(2);
                softly.assertThat(queue.peek()).isEqualTo(1);
                softly.assertThat(queue.size()).isEqualTo(2);
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

        @Nested
        class Size_N_Multiple {

            @Test
            void maintains_FIFO_order(SoftAssertions softly) {
                queue.enqueue(1);
                queue.enqueue(2);
                queue.enqueue(3);
                softly.assertThat(queue.peek()).isEqualTo(1);
            }

            @Test
            void handles_duplicate_elements() {
                queue.enqueue(1);
                queue.enqueue(1);
                queue.enqueue(1);
                assertThat(queue.size()).isEqualTo(3);
            }

            @Test
            void stress_1000_elements(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++)
                    queue.enqueue(i);
                softly.assertThat(queue.size()).isEqualTo(1000);
                softly.assertThat(queue.peek()).isEqualTo(0);
            }
        }

        @Nested
        class Boundary_Values {

            @Test
            void handles_max_integer() {
                queue.enqueue(Integer.MAX_VALUE);
                assertThat(queue.peek()).isEqualTo(Integer.MAX_VALUE);
            }

            @Test
            void handles_min_integer() {
                queue.enqueue(Integer.MIN_VALUE);
                assertThat(queue.peek()).isEqualTo(Integer.MIN_VALUE);
            }
        }
    }
    //endregion

    //region - Dequeue
    @Nested
    class Dequeue {

        @Nested
        class Size_0_Empty {

            @Test
            void throws_on_empty_queue() {
                assertThatThrownBy(() -> queue.dequeue())
                    .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void single_element_becomes_empty() {
                queue.enqueue(42);
                queue.dequeue();
                assertThat(queue.isEmpty()).isTrue();
            }

            @Test
            void can_remove_null_element() {
                queue.enqueue(null);
                Integer dequeued = queue.dequeue();
                assertThat(dequeued).isNull();
            }
        }

        @Nested
        class Size_N_Multiple {

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
            void FIFO_order_large(SoftAssertions softly) {
                for (int i = 0; i < 100; i++)
                    queue.enqueue(i);
                for (int i = 0; i < 100; i++)
                    softly.assertThat(queue.dequeue()).isEqualTo(i);
            }
        }

        @Nested
        class Null_Values {

            @Test
            void mixed_null_and_non_null_values(SoftAssertions softly) {
                queue.enqueue(null);
                queue.enqueue(1);
                queue.enqueue(2);
                softly.assertThat(queue.dequeue()).isNull();
                softly.assertThat(queue.dequeue()).isEqualTo(1);
                softly.assertThat(queue.dequeue()).isEqualTo(2);
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
            void throws_on_empty_queue() {
                assertThatThrownBy(() -> queue.peek())
                    .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class Size_1_Single {

            @Test
            void returns_front_element() {
                queue.enqueue(42);
                assertThat(queue.peek()).isEqualTo(42);
            }

            @Test
            void returns_null_when_front_is_null() {
                queue.enqueue(null);
                assertThat(queue.peek()).isNull();
            }
        }

        @Nested
        class Size_N_Multiple {

            @Test
            void does_not_remove_element(SoftAssertions softly) {
                queue.enqueue(1);
                queue.enqueue(2);
                softly.assertThat(queue.peek()).isEqualTo(1);
                softly.assertThat(queue.peek()).isEqualTo(1);
                softly.assertThat(queue.size()).isEqualTo(2);
            }

            @Test
            void peek_between_enqueues(SoftAssertions softly) {
                queue.enqueue(1);
                softly.assertThat(queue.peek()).isEqualTo(1);
                queue.enqueue(2);
                softly.assertThat(queue.peek()).isEqualTo(1);
            }

            @Test
            void after_partial_dequeue(SoftAssertions softly) {
                for (int i = 0; i < 100; i++)
                    queue.enqueue(i);
                for (int i = 0; i < 50; i++)
                    queue.dequeue();
                softly.assertThat(queue.peek()).isEqualTo(50);
            }
        }
    }
    //endregion

    //region - Size
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
        void stress_after_operations(SoftAssertions softly) {
            for (int i = 0; i < 1000; i++)
                queue.enqueue(i);
            softly.assertThat(queue.size()).isEqualTo(1000);
            for (int i = 0; i < 500; i++)
                queue.dequeue();
            softly.assertThat(queue.size()).isEqualTo(500);
        }
    }
    //endregion

    //region - IsEmpty
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
    //endregion

    //region - Contains
    @Nested
    class Contains {

        @Test
        void returns_false_for_empty_queue() {
            assertThat(queue.contains(1)).isFalse();
        }

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
        void handles_null_in_contains() {
            queue.enqueue(1);
            queue.enqueue(null);
            queue.enqueue(2);
            assertThat(queue.contains(null)).isTrue();
        }
    }
    //endregion

    //region - Iterator
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
    }
    //endregion

    //region - ToArray
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
    }
    //endregion

    //region - Integration
    @Nested
    class Integration {

        @Nested
        class FIFO_Behavior {

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
            void maintains_insertion_order_with_duplicates(SoftAssertions softly) {
                queue.enqueue(5);
                queue.enqueue(5);
                queue.enqueue(5);
                softly.assertThat(queue.dequeue()).isEqualTo(5);
                softly.assertThat(queue.dequeue()).isEqualTo(5);
                softly.assertThat(queue.dequeue()).isEqualTo(5);
            }
        }

        @Nested
        class Stress_Tests {

            @Test
            void large_number_of_elements(SoftAssertions softly) {
                int count = 10000;
                for (int i = 0; i < count; i++)
                    queue.enqueue(i);
                softly.assertThat(queue.size()).isEqualTo(count);
                for (int i = 0; i < count; i++)
                    softly.assertThat(queue.dequeue()).isEqualTo(i);
                softly.assertThat(queue.isEmpty()).isTrue();
            }

            @Test
            void alternating_enqueue_dequeue(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    queue.enqueue(i);
                    softly.assertThat(queue.dequeue()).isEqualTo(i);
                }
                softly.assertThat(queue.isEmpty()).isTrue();
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
        }

        @Nested
        class Data_Integrity {

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

        @Nested
        class Type_Safety {

            @Test
            void handles_String_type(SoftAssertions softly) {
                LinkedQueue<String> stringQueue = new LinkedQueue<>();
                stringQueue.enqueue("first");
                stringQueue.enqueue("second");
                softly.assertThat(stringQueue.dequeue()).isEqualTo("first");
                softly.assertThat(stringQueue.dequeue()).isEqualTo("second");
            }
        }
    }
    //endregion
}
