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

import data_structures.linked_list.SinglyLinkedList;

@SuppressWarnings({"DataFlowIssue", "ConstantConditions"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class SinglyLinkedListTest {

    SinglyLinkedList<Integer> list;

    @BeforeEach
    void init() {
        list = new SinglyLinkedList<>();
    }

    //region - Add

    @Nested
    class Add {

        @Nested
        class First {

            @Test
            void to_empty_list(SoftAssertions softly) {
                list.addFirst(42);
                softly.assertThat(list.getFirst()).isEqualTo(42);
                softly.assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void to_non_empty_list(SoftAssertions softly) {
                list.addFirst(1);
                list.addFirst(2);
                softly.assertThat(list.getFirst()).isEqualTo(2);
                softly.assertThat(list.size()).isEqualTo(2);
            }

            @Test
            void maintains_LIFO_order() {
                list.addFirst(1);
                list.addFirst(2);
                list.addFirst(3);
                assertThat(list.getFirst()).isEqualTo(3);
            }

            @Test
            void handles_null_value() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addFirst(null);
                assertThat(s.getFirst()).isNull();
            }

            @Test
            void stress_1000_elements(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addFirst(i);
                }
                softly.assertThat(list.size()).isEqualTo(1000);
                softly.assertThat(list.getFirst()).isEqualTo(999);
                softly.assertThat(list.get(999)).isEqualTo(0);
            }
        }

        @Nested
        class Last {

            @Test
            void to_empty_list(SoftAssertions softly) {
                list.addLast(42);
                softly.assertThat(list.getFirst()).isEqualTo(42);
                softly.assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void to_non_empty_list(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                softly.assertThat(list.getFirst()).isEqualTo(1);
                softly.assertThat(list.get(1)).isEqualTo(2);
                softly.assertThat(list.size()).isEqualTo(2);
            }

            @Test
            void maintains_order(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                softly.assertThat(list.getFirst()).isEqualTo(1);
                softly.assertThat(list.get(2)).isEqualTo(3);
            }

            @Test
            void handles_null_value() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addLast(null);
                assertThat(s.getFirst()).isNull();
            }

            @Test
            void stress_1000_elements(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addLast(i);
                }
                softly.assertThat(list.size()).isEqualTo(1000);
                softly.assertThat(list.getFirst()).isEqualTo(0);
                softly.assertThat(list.get(999)).isEqualTo(999);
            }

            @Test
            void handles_duplicate_elements() {
                list.addLast(1);
                list.addLast(1);
                list.addLast(1);
                assertThat(list.size()).isEqualTo(3);
            }
        }

        @Nested
        class At {

            @Test
            void at_index_zero_equivalent_to_addFirst(SoftAssertions softly) {
                list.addAt(0, 42);
                softly.assertThat(list.get(0)).isEqualTo(42);
                softly.assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void at_index_zero_on_existing_list(SoftAssertions softly) {
                list.addAt(0, 42);
                list.addAt(0, 99);
                softly.assertThat(list.get(0)).isEqualTo(99);
                softly.assertThat(list.get(1)).isEqualTo(42);
            }

            @Test
            void at_middle_index(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(3);
                list.addAt(1, 2);
                softly.assertThat(list.get(1)).isEqualTo(2);
                softly.assertThat(list.get(2)).isEqualTo(3);
                softly.assertThat(list.size()).isEqualTo(3);
            }

            @Test
            void at_end_index_equivalent_to_addLast(SoftAssertions softly) {
                list.addLast(1);
                list.addAt(1, 2);
                softly.assertThat(list.get(1)).isEqualTo(2);
                softly.assertThat(list.size()).isEqualTo(2);
            }

            @Test
            void handles_null_value() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addLast("a");
                s.addAt(1, null);
                assertThat(s.get(1)).isNull();
            }

            @Test
            void throws_on_negative_index() {
                list.addLast(1);
                assertThatThrownBy(() -> list.addAt(-1, 42))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_greater_than_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.addAt(5, 42))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }
    }

    //endregion

    //region - Read

    @Nested
    class Read {

        @Nested
        class Get {

            @Test
            void returns_element_at_index_zero() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.get(0)).isEqualTo(1);
            }

            @Test
            void returns_element_at_middle_index() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.get(1)).isEqualTo(2);
            }

            @Test
            void returns_element_at_last_index() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.get(2)).isEqualTo(3);
            }

            @Test
            void throws_on_negative_index() {
                list.addLast(1);
                assertThatThrownBy(() -> list.get(-1))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_equal_to_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.get(1))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_greater_than_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.get(100))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.get(0))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class GetFirst {

            @Test
            void returns_first_element() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.getFirst()).isEqualTo(1);
            }

            @Test
            void handles_max_integer_value() {
                list.addFirst(Integer.MAX_VALUE);
                assertThat(list.getFirst()).isEqualTo(Integer.MAX_VALUE);
            }

            @Test
            void handles_min_integer_value() {
                list.addFirst(Integer.MIN_VALUE);
                assertThat(list.getFirst()).isEqualTo(Integer.MIN_VALUE);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.getFirst())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void returns_null_when_first_is_null() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addFirst(null);
                assertThat(s.getFirst()).isNull();
            }

            @Test
            void equals_getLast_for_single_element() {
                list.addLast(42);
                assertThat(list.getFirst()).isEqualTo(list.getLast());
            }
        }

        @Nested
        class GetLast {

            @Test
            void returns_last_element() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.getLast()).isEqualTo(2);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.getLast())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void returns_null_when_last_is_null() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addLast(null);
                assertThat(s.getLast()).isNull();
            }
        }

        @Nested
        class IndexOf {

            @Test
            void returns_index_of_existing_element() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.indexOf(2)).isEqualTo(1);
            }

            @Test
            void returns_zero_for_first_element() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.indexOf(1)).isZero();
            }

            @Test
            void returns_last_index_for_last_element() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.indexOf(3)).isEqualTo(2);
            }

            @Test
            void returns_minus_one_for_non_existing() {
                list.addLast(1);
                assertThat(list.indexOf(99)).isEqualTo(-1);
            }

            @Test
            void returns_minus_one_on_empty_list() {
                assertThat(list.indexOf(42)).isEqualTo(-1);
            }

            @Test
            void returns_first_occurrence_for_duplicates() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(1);
                assertThat(list.indexOf(1)).isZero();
            }

            @Test
            void finds_null_element() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addLast("a");
                s.addLast(null);
                s.addLast("b");
                assertThat(s.indexOf(null)).isEqualTo(1);
            }
        }

        @Nested
        class Size {

            @Test
            void returns_zero_for_new_list() {
                assertThat(list.size()).isZero();
            }

            @Test
            void increments_after_addFirst() {
                list.addFirst(1);
                assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void increments_after_addLast() {
                list.addFirst(1);
                list.addLast(2);
                assertThat(list.size()).isEqualTo(2);
            }

            @Test
            void decrements_after_deleteFirst() {
                list.addFirst(1);
                list.addLast(2);
                list.deleteFirst();
                assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void stress_after_1000_adds_and_500_deletes(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addLast(i);
                }
                softly.assertThat(list.size()).isEqualTo(1000);
                for (int i = 0; i < 500; i++) {
                    list.deleteFirst();
                }
                softly.assertThat(list.size()).isEqualTo(500);
            }
        }

        @Nested
        class IsEmpty {

            @Test
            void true_for_new_list() {
                assertThat(list.isEmpty()).isTrue();
            }

            @Test
            void false_after_add() {
                list.addFirst(1);
                assertThat(list.isEmpty()).isFalse();
            }

            @Test
            void true_after_removal_of_all_elements() {
                list.addFirst(1);
                list.deleteFirst();
                assertThat(list.isEmpty()).isTrue();
            }
        }

        @Nested
        class ToString {

            @Test
            void formats_empty_list() {
                assertThat(list.toString()).isEqualTo("[]");
            }

            @Test
            void formats_list_with_elements() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.toString()).isEqualTo("[1, 2, 3]");
            }

            @Test
            void formats_list_with_null() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addLast("a");
                s.addLast(null);
                s.addLast("b");
                assertThat(s.toString()).isEqualTo("[a, null, b]");
            }
        }
    }

    //endregion

    //region - Update

    @Nested
    class Update {

        @Nested
        class Set {

            @Test
            void sets_element_at_head() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                list.set(0, 99);
                assertThat(list.get(0)).isEqualTo(99);
            }

            @Test
            void sets_element_at_middle() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                list.set(1, 88);
                assertThat(list.get(1)).isEqualTo(88);
            }

            @Test
            void sets_element_at_tail() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                list.set(2, 77);
                assertThat(list.get(2)).isEqualTo(77);
            }

            @Test
            void does_not_change_size() {
                list.addLast(1);
                list.addLast(2);
                int sz = list.size();
                list.set(0, 99);
                assertThat(list.size()).isEqualTo(sz);
            }

            @Test
            void can_set_to_null() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addLast("a");
                s.set(0, null);
                assertThat(s.get(0)).isNull();
            }

            @Test
            void throws_on_negative_index() {
                list.addLast(1);
                assertThatThrownBy(() -> list.set(-1, 99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_equal_to_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.set(1, 99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.set(0, 99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }
    }

    //endregion

    //region - Delete

    @Nested
    class Delete {

        @Nested
        class First {

            @Test
            void removes_and_returns_first_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                int removed = list.deleteFirst();
                softly.assertThat(removed).isEqualTo(1);
                softly.assertThat(list.getFirst()).isEqualTo(2);
                softly.assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.deleteFirst())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void consecutive_deletes_clear_list(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.deleteFirst();
                list.deleteFirst();
                softly.assertThat(list.isEmpty()).isTrue();
                softly.assertThatThrownBy(() -> list.deleteFirst())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void can_remove_null_element() {
                SinglyLinkedList<String> s = new SinglyLinkedList<>();
                s.addFirst(null);
                String removed = s.deleteFirst();
                assertThat(removed).isNull();
            }

            @Test
            void single_element_becomes_empty() {
                list.addFirst(42);
                list.deleteFirst();
                assertThat(list.isEmpty()).isTrue();
            }
        }

        @Nested
        class Last {

            @Test
            void removes_and_returns_last_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                int removed = list.deleteLast();
                softly.assertThat(removed).isEqualTo(2);
                softly.assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.deleteLast())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void single_element_becomes_empty(SoftAssertions softly) {
                list.addFirst(42);
                int removed = list.deleteLast();
                softly.assertThat(removed).isEqualTo(42);
                softly.assertThat(list.isEmpty()).isTrue();
            }

            @Test
            void consecutive_deletes_from_back() {
                list.addLast(1);
                list.addLast(2);
                list.deleteLast();
                list.deleteLast();
                assertThat(list.isEmpty()).isTrue();
            }
        }

        @Nested
        class At {

            @Test
            void removes_element_at_middle_index(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                int removed = list.deleteAt(1);
                softly.assertThat(removed).isEqualTo(2);
                softly.assertThat(list.get(1)).isEqualTo(3);
                softly.assertThat(list.size()).isEqualTo(2);
            }

            @Test
            void removes_head_at_index_zero() {
                list.addLast(1);
                list.addLast(2);
                list.deleteAt(0);
                assertThat(list.getFirst()).isEqualTo(2);
            }

            @Test
            void removes_tail_at_index_size_minus_one() {
                list.addLast(1);
                list.addLast(2);
                list.deleteAt(1);
                assertThat(list.size()).isEqualTo(1);
            }

            @Test
            void throws_on_negative_index() {
                list.addLast(1);
                assertThatThrownBy(() -> list.deleteAt(-1))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_equal_to_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.deleteAt(1))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_greater_than_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.deleteAt(5))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_empty_list() {
                assertThatThrownBy(() -> list.deleteAt(0))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }
    }

    //endregion

    //region - Stress

    @Nested
    class Stress {

        @Test
        void large_sequential_add_and_delete(SoftAssertions softly) {
            for (int i = 0; i < 1000; i++) {
                list.addLast(i);
            }
            softly.assertThat(list.size()).isEqualTo(1000);

            for (int i = 0; i < 500; i++) {
                list.deleteFirst();
            }
            softly.assertThat(list.getFirst()).isEqualTo(500);
            softly.assertThat(list.size()).isEqualTo(500);

            for (int i = 0; i < 500; i++) {
                list.deleteLast();
            }
            softly.assertThat(list.isEmpty()).isTrue();
        }

        @Test
        void interleaved_add_first_and_last(SoftAssertions softly) {
            for (int i = 0; i < 10; i++) {
                list.addFirst(i);
                list.addLast(i + 100);
            }
            softly.assertThat(list.size()).isEqualTo(20);

            for (int i = 0; i < 5; i++) {
                list.deleteFirst();
                list.deleteLast();
            }
            softly.assertThat(list.size()).isEqualTo(10);
        }

        @Test
        void size_never_goes_negative(SoftAssertions softly) {
            softly.assertThat(list.size()).isGreaterThanOrEqualTo(0);
            list.addLast(1);
            softly.assertThat(list.size()).isGreaterThanOrEqualTo(0);
            list.deleteFirst();
            softly.assertThat(list.size()).isGreaterThanOrEqualTo(0);
        }
    }

    //endregion

    //region - Unidirectional Navigation

    @Nested
    class UnidirectionalNavigation {

        @Test
        void forward_only_traversal() {
            for (int i = 0; i < 10; i++)
                list.addLast(i);

            for (int i = 0; i < list.size(); i++) {
                assertThat(list.get(i)).isEqualTo(i);
            }
        }

        @Test
        void pointer_integrity_after_operations(SoftAssertions softly) {
            for (int i = 0; i < 5; i++)
                list.addLast(i);

            list.deleteAt(2);

            softly.assertThat(list.get(2)).isEqualTo(3);
            softly.assertThat(list.size()).isEqualTo(4);
        }
    }

    //endregion
}
