package data_structures.linked_list;

import java.util.List;

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

import data_structures.linked_list.DoublyLinkedList.Node;

@SuppressWarnings({"ConstantConditions", "unused"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class DoublyLinkedListTest {

    DoublyLinkedList<Integer> list;

    @BeforeEach
    void init() {
        list = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    //region - Node Helpers (direct field access)
    private Node<Integer> getHead() {
        return list.head;
    }

    private Node<Integer> getTail() {
        return list.tail;
    }

    private Node<Integer> getNext(Node<Integer> node) {
        return node.next;
    }

    private Node<Integer> getPrev(Node<Integer> node) {
        return node.prev;
    }

    private Integer getValue(Node<Integer> node) {
        return node.value;
    }

    private Node<Integer> walkNext(Node<Integer> node, int steps) {
        Node<Integer> curr = node;
        for (int i = 0; i < steps; i++) {
            curr = getNext(curr);
        }
        return curr;
    }

    /**
     * Walks from head sentinel to reach the node at the given 0-based index.
     */
    private Node<Integer> nodeAtIndexFromFirstElement(int index) {
        return walkNext(getHead(), index + 1);
    }

    private Integer valueAtIndexFromHead(int index) {
        return list.get(index);
    }
    //endregion

    //region - Add
    @Nested
    class Add {

        @Nested
        class Before {

            @Test
            void inserts_before_specified_node(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(3);
                int sizeBefore = list.size;

                Node<Integer> nodeHolding3 = nodeAtIndexFromFirstElement(1);
                list.addBefore(nodeHolding3, 2);

                softly.assertThat(list.size)
                        .as("size should increment after addBefore")
                        .isEqualTo(sizeBefore + 1);
                softly.assertThat(valueAtIndexFromHead(0)).isEqualTo(1);
                softly.assertThat(valueAtIndexFromHead(1)).isEqualTo(2);
                softly.assertThat(valueAtIndexFromHead(2)).isEqualTo(3);
            }

            @Test
            void inserts_before_first_element_becomes_new_first(SoftAssertions softly) {
                list.addLast(10);
                list.addLast(20);
                int sizeBefore = list.size;

                Node<Integer> firstNode = nodeAtIndexFromFirstElement(0);
                list.addBefore(firstNode, 5);

                softly.assertThat(list.size)
                        .as("size should increment after addBefore at front")
                        .isEqualTo(sizeBefore + 1);
                softly.assertThat(valueAtIndexFromHead(0)).isEqualTo(5);
                softly.assertThat(valueAtIndexFromHead(1)).isEqualTo(10);
                softly.assertThat(valueAtIndexFromHead(2)).isEqualTo(20);
                softly.assertThat(list.getFirst())
                        .as("getFirst should return newly inserted element")
                        .isEqualTo(5);
            }

            @Test
            void maintains_prev_next_pointer_integrity_after_insert(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(3);

                Node<Integer> nodeHolding3 = nodeAtIndexFromFirstElement(1);
                list.addBefore(nodeHolding3, 2);

                Node<Integer> insertedNode = nodeAtIndexFromFirstElement(1);
                softly.assertThat(getValue(insertedNode)).isEqualTo(2);
                softly.assertThat(getValue(getPrev(insertedNode)))
                        .as("inserted node's prev should point to node holding 1")
                        .isEqualTo(1);
                softly.assertThat(getValue(getNext(insertedNode)))
                        .as("inserted node's next should point to node holding 3")
                        .isEqualTo(3);
                softly.assertThat(getNext(getPrev(insertedNode)))
                        .as("prev node's next should point back to inserted node")
                        .isSameAs(insertedNode);
                softly.assertThat(getPrev(getNext(insertedNode)))
                        .as("next node's prev should point back to inserted node")
                        .isSameAs(insertedNode);
            }

            @Test
            void throws_when_node_is_null() {
                assertThatThrownBy(() -> list.addBefore(null, 1))
                        .isInstanceOf(NullPointerException.class);
            }

            @Test
            void adds_before_node_from_another_list_without_throwing() {
                DoublyLinkedList<Integer> listA = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
                DoublyLinkedList<Integer> listB = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
                listA.addLast(1);
                Node<Integer> nodeFromA = listA.head.next;

                listB.addBefore(nodeFromA, 99);

                assertThat(listB.size).isEqualTo(1);
            }
        }

        @Nested
        class After {

            @Test
            void inserts_after_specified_node(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(3);
                int sizeBefore = list.size;

                Node<Integer> nodeHolding1 = nodeAtIndexFromFirstElement(0);
                list.addAfter(nodeHolding1, 2);

                softly.assertThat(list.size)
                        .as("size should increment after addAfter")
                        .isEqualTo(sizeBefore + 1);
                softly.assertThat(valueAtIndexFromHead(0)).isEqualTo(1);
                softly.assertThat(valueAtIndexFromHead(1)).isEqualTo(2);
                softly.assertThat(valueAtIndexFromHead(2)).isEqualTo(3);
            }

            @Test
            void inserts_after_last_element_becomes_new_last(SoftAssertions softly) {
                list.addLast(10);
                list.addLast(20);
                int sizeBefore = list.size;

                Node<Integer> lastNode = nodeAtIndexFromFirstElement(1);
                list.addAfter(lastNode, 30);

                softly.assertThat(list.size)
                        .as("size should increment after addAfter at end")
                        .isEqualTo(sizeBefore + 1);
                softly.assertThat(valueAtIndexFromHead(0)).isEqualTo(10);
                softly.assertThat(valueAtIndexFromHead(1)).isEqualTo(20);
                softly.assertThat(valueAtIndexFromHead(2)).isEqualTo(30);
                softly.assertThat(list.getLast())
                        .as("getLast should return newly inserted element")
                        .isEqualTo(30);
            }

            @Test
            void maintains_prev_next_pointer_integrity_after_insert(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(3);

                Node<Integer> nodeHolding1 = nodeAtIndexFromFirstElement(0);
                list.addAfter(nodeHolding1, 2);

                Node<Integer> insertedNode = nodeAtIndexFromFirstElement(1);
                softly.assertThat(getValue(insertedNode)).isEqualTo(2);
                softly.assertThat(getValue(getPrev(insertedNode)))
                        .as("inserted node's prev should point to node holding 1")
                        .isEqualTo(1);
                softly.assertThat(getValue(getNext(insertedNode)))
                        .as("inserted node's next should point to node holding 3")
                        .isEqualTo(3);
                softly.assertThat(getNext(getPrev(insertedNode)))
                        .as("prev node's next should point back to inserted node")
                        .isSameAs(insertedNode);
                softly.assertThat(getPrev(getNext(insertedNode)))
                        .as("next node's prev should point back to inserted node")
                        .isSameAs(insertedNode);
            }

            @Test
            void throws_when_node_is_null() {
                assertThatThrownBy(() -> list.addAfter(null, 1))
                        .isInstanceOf(NullPointerException.class);
            }

            @Test
            void adds_after_node_from_another_list_without_throwing() {
                DoublyLinkedList<Integer> listA = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
                DoublyLinkedList<Integer> listB = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
                listA.addLast(1);
                Node<Integer> nodeFromA = listA.head.next;

                listB.addAfter(nodeFromA, 99);

                assertThat(listB.size).isEqualTo(1);
            }
        }

        @Nested
        class First {

            @Test
            void to_empty_list(SoftAssertions softly) {
                list.addFirst(42);
                softly.assertThat(list.getFirst()).isEqualTo(42);
                softly.assertThat(list.getLast()).isEqualTo(42);
                softly.assertThat(list.size).isEqualTo(1);
            }

            @Test
            void to_non_empty_list(SoftAssertions softly) {
                list.addFirst(1);
                list.addFirst(2);
                softly.assertThat(list.getFirst()).isEqualTo(2);
                softly.assertThat(list.getLast()).isEqualTo(1);
                softly.assertThat(list.size).isEqualTo(2);
            }

            @Test
            void maintains_LIFO_order(SoftAssertions softly) {
                list.addFirst(1);
                list.addFirst(2);
                list.addFirst(3);
                softly.assertThat(list.getFirst()).isEqualTo(3);
                softly.assertThat(list.getLast()).isEqualTo(1);
            }

            @Test
            void stress_1000_elements(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addFirst(i);
                }
                softly.assertThat(list.size).isEqualTo(1000);
                softly.assertThat(list.getFirst()).isEqualTo(999);
                softly.assertThat(list.getLast()).isEqualTo(0);
            }
        }

        @Nested
        class Last {

            @Test
            void to_empty_list(SoftAssertions softly) {
                list.addLast(42);
                softly.assertThat(list.getLast()).isEqualTo(42);
                softly.assertThat(list.getFirst()).isEqualTo(42);
                softly.assertThat(list.size).isEqualTo(1);
            }

            @Test
            void to_non_empty_list(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                softly.assertThat(list.getLast()).isEqualTo(2);
                softly.assertThat(list.getFirst()).isEqualTo(1);
                softly.assertThat(list.size).isEqualTo(2);
            }

            @Test
            void maintains_order(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                softly.assertThat(list.getFirst()).isEqualTo(1);
                softly.assertThat(list.getLast()).isEqualTo(3);
            }

            @Test
            void stress_1000_elements(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addLast(i);
                }
                softly.assertThat(list.size).isEqualTo(1000);
                softly.assertThat(list.getLast()).isEqualTo(999);
                softly.assertThat(list.getFirst()).isEqualTo(0);
            }

            @Test
            void handles_duplicate_elements() {
                list.addLast(1);
                list.addLast(1);
                list.addLast(1);
                assertThat(list.size).isEqualTo(3);
            }
        }

        @Nested
        class At {

            @Test
            void at_index_zero_equivalent_to_addFirst(SoftAssertions softly) {
                list.addAt(0, 42);
                softly.assertThat(list.get(0)).isEqualTo(42);
                softly.assertThat(list.size).isEqualTo(1);
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
                softly.assertThat(list.size).isEqualTo(3);
            }

            @Test
            void at_end_index_equivalent_to_addLast(SoftAssertions softly) {
                list.addLast(1);
                list.addAt(1, 2);
                softly.assertThat(list.get(1)).isEqualTo(2);
                softly.assertThat(list.getLast()).isEqualTo(2);
                softly.assertThat(list.size).isEqualTo(2);
            }

            @Test
            void near_end_uses_tail_traversal(SoftAssertions softly) {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }
                list.addAt(8, 99);
                softly.assertThat(list.get(8)).isEqualTo(99);
                softly.assertThat(list.size).isEqualTo(11);
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

            @Test
            void large_list_add_at_first_half_index(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.addAt(20, 999);

                softly.assertThat(list.size).isEqualTo(101);
                softly.assertThat(list.get(19)).isEqualTo(19);
                softly.assertThat(list.get(20)).isEqualTo(999);
                softly.assertThat(list.get(21)).isEqualTo(20);
            }

            @Test
            void large_list_add_at_second_half_index(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.addAt(80, 888);

                softly.assertThat(list.size).isEqualTo(101);
                softly.assertThat(list.get(79)).isEqualTo(79);
                softly.assertThat(list.get(80)).isEqualTo(888);
                softly.assertThat(list.get(81)).isEqualTo(80);
            }

            @Test
            void large_list_add_near_tail_verifies_reverse_traversal(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.addAt(95, 777);

                softly.assertThat(list.size).isEqualTo(101);
                softly.assertThat(list.get(94)).isEqualTo(94);
                softly.assertThat(list.get(95)).isEqualTo(777);
                softly.assertThat(list.get(96)).isEqualTo(95);
            }
        }
    }

    //endregion
    //region - Read
    @Nested
    class Read {

        @Nested
        class Get {

            @Nested
            class At {

                @Test
                void index_zero(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    softly.assertThat(list.get(0)).isEqualTo(1);
                }

                @Test
                void middle_index(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(3);
                    softly.assertThat(list.get(1)).isEqualTo(2);
                }

                @Test
                void last_index(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(3);
                    softly.assertThat(list.get(2)).isEqualTo(3);
                }

                @Test
                void first_half_traversal(SoftAssertions softly) {
                    for (int i = 0; i < 10; i++) {
                        list.addLast(i);
                    }
                    softly.assertThat(list.get(2)).isEqualTo(2);
                }

                @Test
                void get_from_second_half_of_list_returns_expected_value(SoftAssertions softly) {
                    for (int i = 0; i < 10; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.size)
                            .as("setup: list should contain 10 elements [0..9]")
                            .isEqualTo(10);
                    softly.assertThat(list.get(8))
                            .as("expected value at index 8 (near tail) after appending [0..9]")
                            .isEqualTo(8);
                }

                @Test
                void traversal_choice_is_not_asserted_without_instrumentation() {
                    for (int i = 0; i < 11; i++) {
                        list.addLast(i);
                    }
                    assertThat(list.get(5)).isEqualTo(5);
                    assertThat(list.get(10)).isEqualTo(10);
                }

                @Test
                void boundary_values(SoftAssertions softly) {
                    list.addFirst(Integer.MAX_VALUE);
                    list.addFirst(Integer.MIN_VALUE);
                    list.addLast(0);
                    softly.assertThat(list.get(1)).isEqualTo(Integer.MAX_VALUE);
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

                @Test
                void pathing_returns_correct_value_for_first_half_index() {
                    for (int i = 0; i < 11; i++) {
                        list.addLast(i);
                    }
                    assertThat(list.get(5))
                            .as("get(5) on list [0..10] should return 5 (first-half traversal)")
                            .isEqualTo(5);
                }

                @Test
                void pathing_returns_correct_value_for_second_half_index() {
                    for (int i = 0; i < 11; i++) {
                        list.addLast(i);
                    }
                    assertThat(list.get(10))
                            .as("get(10) on list [0..10] should return 10 (second-half traversal)")
                            .isEqualTo(10);
                }

                @Test
                void large_list_get_multiple_first_half_indices(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.get(10)).isEqualTo(10);
                    softly.assertThat(list.get(25)).isEqualTo(25);
                    softly.assertThat(list.get(40)).isEqualTo(40);
                    softly.assertThat(list.get(49)).isEqualTo(49);
                }

                @Test
                void large_list_get_multiple_second_half_indices(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.get(50)).isEqualTo(50);
                    softly.assertThat(list.get(70)).isEqualTo(70);
                    softly.assertThat(list.get(85)).isEqualTo(85);
                    softly.assertThat(list.get(99)).isEqualTo(99);
                }

                @Test
                void large_list_get_boundary_between_first_and_second_half(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.get(48)).isEqualTo(48);
                    softly.assertThat(list.get(49)).isEqualTo(49);
                    softly.assertThat(list.get(50)).isEqualTo(50);
                    softly.assertThat(list.get(51)).isEqualTo(51);
                }

                @Test
                void large_list_alternating_first_and_second_half_access(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.get(5)).isEqualTo(5);
                    softly.assertThat(list.get(95)).isEqualTo(95);
                    softly.assertThat(list.get(15)).isEqualTo(15);
                    softly.assertThat(list.get(85)).isEqualTo(85);
                    softly.assertThat(list.get(0)).isEqualTo(0);
                    softly.assertThat(list.get(99)).isEqualTo(99);
                }
            }

            @Nested
            class First {

                @Test
                void returns_first_element(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    softly.assertThat(list.size).isEqualTo(2);
                    softly.assertThat(list.getFirst()).isEqualTo(1);
                }

                @Test
                void handles_max_integer_value(SoftAssertions softly) {
                    list.addFirst(Integer.MAX_VALUE);
                    softly.assertThat(list.size).isEqualTo(1);
                    softly.assertThat(list.getFirst()).isEqualTo(Integer.MAX_VALUE);
                }

                @Test
                void handles_min_integer_value(SoftAssertions softly) {
                    list.addFirst(Integer.MIN_VALUE);
                    softly.assertThat(list.size).isEqualTo(1);
                    softly.assertThat(list.getFirst()).isEqualTo(Integer.MIN_VALUE);
                }

                @Test
                void throws_on_empty_list() {
                    assertThat(list.size).isZero();
                    assertThatThrownBy(() -> list.getFirst())
                            .isInstanceOf(IndexOutOfBoundsException.class);
                }

                @Test
                void equals_getLast_for_single_element(SoftAssertions softly) {
                    list.addLast(42);
                    softly.assertThat(list.size).isEqualTo(1);
                    softly.assertThat(list.getFirst()).isEqualTo(list.getLast());
                }
            }

            @Nested
            class Last {

                @Test
                void returns_last_element(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    softly.assertThat(list.size).isEqualTo(2);
                    softly.assertThat(list.getLast()).isEqualTo(2);
                }

                @Test
                void throws_on_empty_list() {
                    assertThat(list.size).isZero();
                    assertThatThrownBy(() -> list.getLast())
                            .isInstanceOf(IndexOutOfBoundsException.class);
                }
            }

            @Nested
            class FirstIndexOf {

                @Test
                void existing_element(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(3);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getFirstIndexOf(2)).isEqualTo(1);
                }

                @Test
                void first_element(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    softly.assertThat(list.size).isEqualTo(2);
                    softly.assertThat(list.getFirstIndexOf(1)).isZero();
                }

                @Test
                void last_element(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(3);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getFirstIndexOf(3)).isEqualTo(2);
                }

                @Test
                void non_existing_returns_minus_one(SoftAssertions softly) {
                    list.addLast(1);
                    softly.assertThat(list.size).isEqualTo(1);
                    softly.assertThat(list.getFirstIndexOf(99)).isEqualTo(-1);
                }

                @Test
                void empty_list_returns_minus_one(SoftAssertions softly) {
                    softly.assertThat(list.size).isZero();
                    softly.assertThat(list.getFirstIndexOf(42)).isEqualTo(-1);
                }

                @Test
                void duplicates_returns_first_occurrence(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(1);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getFirstIndexOf(1)).isZero();
                }

                @Test
                void finds_element_by_content_not_reference(SoftAssertions softly) {
                    DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
                    s.addLast("test");
                    softly.assertThat(s.size).isEqualTo(1);
                    softly.assertThat(s.getFirstIndexOf("test")).isEqualTo(0);
                }

                @Test
                void large_list_find_element_in_first_half(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.getFirstIndexOf(25))
                            .as("should find element at index 25 (first half)")
                            .isEqualTo(25);
                }

                @Test
                void large_list_find_element_in_second_half(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.getFirstIndexOf(75))
                            .as("should find element at index 75 (second half)")
                            .isEqualTo(75);
                }

                @Test
                void large_list_with_duplicates_finds_first_occurrence(SoftAssertions softly) {
                    for (int i = 0; i < 50; i++) {
                        list.addLast(i);
                    }
                    list.addLast(25); // duplicate at index 50
                    for (int i = 51; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.getFirstIndexOf(25))
                            .as("should find first occurrence at index 25, not the duplicate at 50")
                            .isEqualTo(25);
                }
            }

            @Nested
            class LastIndexOf {

                @Test
                void from_tail(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(3);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getLastIndexOf(3)).isEqualTo(2);
                }

                @Test
                void duplicates_returns_last_occurrence(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(1);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getLastIndexOf(1)).isEqualTo(2);
                }

                @Test
                void non_existing_returns_minus_one(SoftAssertions softly) {
                    list.addLast(1);
                    softly.assertThat(list.size).isEqualTo(1);
                    softly.assertThat(list.getLastIndexOf(99)).isEqualTo(-1);
                }

                @Test
                void empty_list_returns_minus_one(SoftAssertions softly) {
                    softly.assertThat(list.size).isZero();
                    softly.assertThat(list.getLastIndexOf(42)).isEqualTo(-1);

                }

                @Test
                void finds_element_by_content_not_reference(SoftAssertions softly) {
                    DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
                    s.addLast("test");
                    s.addLast("other");
                    s.addLast("test");
                    softly.assertThat(s.size).isEqualTo(3);
                    softly.assertThat(s.getLastIndexOf("test")).isEqualTo(2);
                }

                @Test
                void large_list_find_last_occurrence_in_second_half(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.getLastIndexOf(85))
                            .as("should find element at index 85 via reverse traversal")
                            .isEqualTo(85);
                }

                @Test
                void large_list_with_duplicates_finds_last_occurrence(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }
                    list.addLast(75); // duplicate at index 100
                    list.addLast(101);

                    softly.assertThat(list.getLastIndexOf(75))
                            .as("should find last occurrence at index 100, not first at 75")
                            .isEqualTo(100);
                }

                @Test
                void large_list_find_last_near_beginning(SoftAssertions softly) {
                    for (int i = 0; i < 100; i++) {
                        list.addLast(i);
                    }

                    softly.assertThat(list.getLastIndexOf(10))
                            .as("reverse search should still find element near beginning")
                            .isEqualTo(10);
                }
            }

            @Nested
            class AllIndices {

                @Test
                void empty_list_returns_empty_list(SoftAssertions softly) {
                    softly.assertThat(list.size).isZero();
                    List<Integer> indices = list.getAllIndices(42);
                    softly.assertThat(indices).isEmpty();
                }

                @Test
                void non_existing_returns_empty_list(SoftAssertions softly) {
                    list.addLast(1);
                    softly.assertThat(list.size).isEqualTo(1);
                    softly.assertThat(list.getAllIndices(42)).isEmpty();
                }

                @Test
                void single_occurrence_returns_single_index(SoftAssertions softly) {
                    list.addLast(1);
                    list.addLast(2);
                    list.addLast(3);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getAllIndices(2)).containsExactly(1);
                }

                @Test
                void multiple_occurrences_returns_all_indices_in_order(SoftAssertions softly) {
                    list.addLast(7);
                    list.addLast(8);
                    list.addLast(7);
                    list.addLast(9);
                    list.addLast(7);
                    softly.assertThat(list.size).isEqualTo(5);
                    softly.assertThat(list.getAllIndices(7)).containsExactly(0, 2, 4);
                }

                @Test
                void all_elements_same_returns_all_indices(SoftAssertions softly) {
                    list.addLast(5);
                    list.addLast(5);
                    list.addLast(5);
                    softly.assertThat(list.size).isEqualTo(3);
                    softly.assertThat(list.getAllIndices(5)).containsExactly(0, 1, 2);
                }
            }
        }

        @Nested
        class Size {

            @Test
            void returns_zero_for_new_list() {
                assertThat(list.size).isZero();
            }

            @Test
            void increments_after_addFirst() {
                list.addFirst(1);
                assertThat(list.size).isEqualTo(1);
            }

            @Test
            void increments_after_addLast() {
                list.addFirst(1);
                list.addLast(2);
                assertThat(list.size).isEqualTo(2);
            }

            @Test
            void increments_after_addAt() {
                list.addLast(1);
                list.addLast(3);
                list.addAt(1, 2);
                assertThat(list.size).isEqualTo(3);
            }

            @Test
            void decrements_after_deleteFirst() {
                list.addFirst(1);
                list.addLast(2);
                list.deleteFirst();
                assertThat(list.size).isEqualTo(1);
            }

            @Test
            void decrements_after_deleteAt() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                list.deleteAt(1);
                assertThat(list.size).isEqualTo(2);
            }

            @Test
            void stress_after_1000_adds_and_500_deletes(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addLast(i);
                }
                softly.assertThat(list.size).isEqualTo(1000);
                for (int i = 0; i < 500; i++) {
                    list.deleteFirst();
                }
                softly.assertThat(list.size).isEqualTo(500);
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
            void formats_newly_created_empty_list() {
                DoublyLinkedList<Integer> freshList = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
                assertThat(freshList.size).isZero();
                assertThat(freshList.toString()).isEqualTo("[]");
            }

            @Test
            void formats_empty_list_after_initialization() {
                assertThat(list.size).isZero();
                assertThat(list.toString()).isEqualTo("[]");
            }

            @Test
            void formats_empty_list_after_all_elements_removed(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.deleteFirst();
                list.deleteFirst();

                softly.assertThat(list.size).isZero();
                softly.assertThat(list.toString()).isEqualTo("[]");
            }

            @Test
            void formats_single_element_via_addLast() {
                list.addLast(42);
                assertThat(list.size).isEqualTo(1);
                assertThat(list.toString()).isEqualTo("[42]");
            }

            @Test
            void formats_single_element_via_addFirst() {
                list.addFirst(99);
                assertThat(list.size).isEqualTo(1);
                assertThat(list.toString()).isEqualTo("[99]");
            }

            @Test
            void formats_single_element_via_addAt() {
                list.addAt(0, 77);
                assertThat(list.size).isEqualTo(1);
                assertThat(list.toString()).isEqualTo("[77]");
            }

            @Test
            void formats_two_elements() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.size).isEqualTo(2);
                assertThat(list.toString()).isEqualTo("[1] <-> [2]");
            }

            @Test
            void formats_three_elements_matches_link_arrows() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.size).isEqualTo(3);
                assertThat(list.toString()).isEqualTo("[1] <-> [2] <-> [3]");
            }

            @Test
            void formats_five_elements() {
                for (int i = 1; i <= 5; i++) {
                    list.addLast(i);
                }
                assertThat(list.size).isEqualTo(5);
                assertThat(list.toString()).isEqualTo("[1] <-> [2] <-> [3] <-> [4] <-> [5]");
            }

            @Test
            void formats_large_list_with_10_elements(SoftAssertions softly) {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }

                String result = list.toString();
                softly.assertThat(list.size).isEqualTo(10);
                softly.assertThat(result).startsWith("[0] <-> [1]");
                softly.assertThat(result).endsWith("[8] <-> [9]");
                softly.assertThat(result).contains("[4] <-> [5]");
            }

            @Test
            void formats_large_list_with_20_elements_verifies_complete_chain(SoftAssertions softly) {
                for (int i = 0; i < 20; i++) {
                    list.addLast(i);
                }

                String result = list.toString();
                softly.assertThat(list.size).isEqualTo(20);

                // Verify first few elements
                softly.assertThat(result).startsWith("[0] <-> [1] <-> [2]");

                // Verify middle elements (both halves for implicit pathing)
                softly.assertThat(result).contains("[9] <-> [10] <-> [11]");

                // Verify last few elements
                softly.assertThat(result).endsWith("[18] <-> [19]");
            }

            @Test
            void formats_large_list_with_50_elements_counts_separators(SoftAssertions softly) {
                for (int i = 0; i < 50; i++) {
                    list.addLast(i);
                }

                String result = list.toString();
                softly.assertThat(list.size).isEqualTo(50);

                // Should have exactly 49 separators " <-> " for 50 elements
                int separatorCount = (result.length() - result.replace(" <-> ", "").length()) / 5;
                softly.assertThat(separatorCount)
                        .as("should have 49 separators for 50 elements")
                        .isEqualTo(49);

                // Verify boundary elements
                softly.assertThat(result).startsWith("[0]");
                softly.assertThat(result).endsWith("[49]");
            }

            @Test
            void formats_very_large_list_with_100_elements_verifies_structure(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i * 10);  // multiples of 10
                }
                String result = list.toString();
                softly.assertThat(list.size).isEqualTo(100);
                softly.assertThat(result).startsWith("[0] <-> [10] <-> [20]");
                softly.assertThat(result).contains("[490] <-> [500] <-> [510]");
                softly.assertThat(result).endsWith("[980] <-> [990]");
            }

            @Test
            void formats_list_with_negative_numbers() {
                list.addLast(-5);
                list.addLast(-3);
                list.addLast(-1);
                assertThat(list.toString()).isEqualTo("[-5] <-> [-3] <-> [-1]");
            }

            @Test
            void formats_list_with_mixed_positive_and_negative() {
                list.addLast(-10);
                list.addLast(0);
                list.addLast(10);
                assertThat(list.toString()).isEqualTo("[-10] <-> [0] <-> [10]");
            }

            @Test
            void formats_list_with_zero_values() {
                list.addLast(0);
                list.addLast(0);
                list.addLast(0);
                assertThat(list.toString()).isEqualTo("[0] <-> [0] <-> [0]");
            }

            @Test
            void formats_list_with_max_integer_value() {
                list.addLast(Integer.MAX_VALUE);
                assertThat(list.toString()).isEqualTo("[" + Integer.MAX_VALUE + "]");
            }

            @Test
            void formats_list_with_min_integer_value() {
                list.addLast(Integer.MIN_VALUE);
                assertThat(list.toString()).isEqualTo("[" + Integer.MIN_VALUE + "]");
            }

            @Test
            void formats_list_with_boundary_values() {
                list.addLast(Integer.MIN_VALUE);
                list.addLast(0);
                list.addLast(Integer.MAX_VALUE);
                assertThat(list.toString())
                        .isEqualTo("[" + Integer.MIN_VALUE + "] <-> [0] <-> [" + Integer.MAX_VALUE + "]");
            }

            @Test
            void formats_list_built_with_addFirst_reverse_order() {
                list.addFirst(3);
                list.addFirst(2);
                list.addFirst(1);
                assertThat(list.toString()).isEqualTo("[1] <-> [2] <-> [3]");
            }

            @Test
            void formats_list_built_with_addAt_insertions(SoftAssertions softly) {
                list.addAt(0, 1);
                list.addAt(1, 3);
                list.addAt(1, 2);

                softly.assertThat(list.size).isEqualTo(3);
                softly.assertThat(list.toString()).isEqualTo("[1] <-> [2] <-> [3]");
            }

            @Test
            void formats_list_built_with_mixed_add_operations(SoftAssertions softly) {
                list.addLast(2);
                list.addFirst(1);
                list.addLast(3);

                softly.assertThat(list.size).isEqualTo(3);
                softly.assertThat(list.toString()).isEqualTo("[1] <-> [2] <-> [3]");
            }

            @Test
            void formats_correctly_after_deletion(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                list.addLast(4);

                list.deleteAt(1);

                softly.assertThat(list.size).isEqualTo(3);
                softly.assertThat(list.toString()).isEqualTo("[1] <-> [3] <-> [4]");
            }

            @Test
            void formats_correctly_after_update(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);

                list.setAt(1, 99);

                softly.assertThat(list.size).isEqualTo(3);
                softly.assertThat(list.toString()).isEqualTo("[1] <-> [99] <-> [3]");
            }

            @Test
            void formats_correctly_after_multiple_modifications(SoftAssertions softly) {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }

                list.deleteFirst();
                list.deleteLast();
                list.setAt(0, 99);

                softly.assertThat(list.size).isEqualTo(8);
                String result = list.toString();
                softly.assertThat(result).startsWith("[99]");
                softly.assertThat(result).endsWith("[8]");
            }

            @Test
            void formats_string_list_single_element() {
                DoublyLinkedList<String> strList = new DoublyLinkedList<>("HEAD", "TAIL");
                strList.addLast("hello");
                assertThat(strList.toString()).isEqualTo("[hello]");
            }

            @Test
            void formats_string_list_multiple_elements() {
                DoublyLinkedList<String> strList = new DoublyLinkedList<>("HEAD", "TAIL");
                strList.addLast("alpha");
                strList.addLast("beta");
                strList.addLast("gamma");
                assertThat(strList.toString()).isEqualTo("[alpha] <-> [beta] <-> [gamma]");
            }

            @Test
            void formats_string_list_with_empty_strings() {
                DoublyLinkedList<String> strList = new DoublyLinkedList<>("HEAD", "TAIL");
                strList.addLast("");
                strList.addLast("content");
                strList.addLast("");
                assertThat(strList.toString()).isEqualTo("[] <-> [content] <-> []");
            }

            @Test
            void formats_list_with_all_duplicate_values() {
                list.addLast(5);
                list.addLast(5);
                list.addLast(5);
                list.addLast(5);
                assertThat(list.toString()).isEqualTo("[5] <-> [5] <-> [5] <-> [5]");
            }

            @Test
            void formats_list_with_some_duplicates() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.toString()).isEqualTo("[1] <-> [2] <-> [2] <-> [3]");
            }

            @Test
            void formats_stress_test_1000_elements_verifies_boundaries(SoftAssertions softly) {
                for (int i = 0; i < 1000; i++) {
                    list.addLast(i);
                }

                String result = list.toString();
                softly.assertThat(list.size).isEqualTo(1000);
                softly.assertThat(result).startsWith("[0] <-> [1] <-> [2]");
                softly.assertThat(result).contains("[499] <-> [500] <-> [501]");
                softly.assertThat(result).endsWith("[998] <-> [999]");

                // Verify it's a valid string (doesn't throw, isn't null)
                softly.assertThat(result).isNotNull();
                softly.assertThat(result.length()).isGreaterThan(1000);
            }
        }

        @Nested
        class Contains {

            @Test
            void returns_true_when_value_exists() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.contains(2)).isTrue();
            }

            @Test
            void returns_true_for_first_element() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.contains(1)).isTrue();
            }

            @Test
            void returns_true_for_last_element() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.contains(2)).isTrue();
            }

            @Test
            void returns_false_when_value_not_found() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.contains(99)).isFalse();
            }

            @Test
            void returns_false_on_empty_list() {
                assertThat(list.size).isZero();
                assertThat(list.contains(1)).isFalse();
            }

            @Test
            void returns_true_for_duplicate_values() {
                list.addLast(5);
                list.addLast(5);
                list.addLast(5);
                assertThat(list.contains(5)).isTrue();
            }

            @Test
            void large_list_returns_true_for_existing_value() {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }
                assertThat(list.contains(50)).isTrue();
            }

            @Test
            void large_list_returns_false_for_non_existing_value() {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }
                assertThat(list.contains(999)).isFalse();
            }
        }

        @Nested
        class ToArray {

            @Test
            void returns_empty_array_for_empty_list() {
                assertThat(list.size).isZero();
                Object[] arr = list.toArray();
                assertThat(arr).isEmpty();
            }

            @Test
            void returns_single_element_array() {
                list.addLast(42);
                assertThat(list.size).isEqualTo(1);
                Object[] arr = list.toArray();
                assertThat(arr).containsExactly(42);
            }

            @Test
            void returns_array_with_elements_in_order(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                softly.assertThat(list.size).isEqualTo(3);

                Object[] arr = list.toArray();
                softly.assertThat(arr).containsExactly(1, 2, 3);
                softly.assertThat(arr.length).isEqualTo(list.size);
            }

            @Test
            void returned_array_is_independent_of_list(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);

                Object[] arr = list.toArray();
                softly.assertThat(arr[0]).as("array should initially contain value").isEqualTo(1);
                arr[0] = 99;

                softly.assertThat(list.get(0))
                        .as("modifying array should not affect list")
                        .isEqualTo(1);
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
                list.addLast(3);
                int sizeBefore = list.size;

                Integer deleted = list.deleteFirst();

                softly.assertThat(deleted).isEqualTo(1);
                softly.assertThat(list.size)
                        .as("size should decrement after deleteFirst")
                        .isEqualTo(sizeBefore - 1);
                softly.assertThat(list.getFirst()).isEqualTo(2);
            }

            @Test
            void on_single_element_list_makes_list_empty(SoftAssertions softly) {
                list.addLast(42);

                Integer deleted = list.deleteFirst();

                softly.assertThat(deleted).isEqualTo(42);
                softly.assertThat(list.size).isZero();
                softly.assertThat(list.isEmpty()).isTrue();
            }

            @Test
            void maintains_pointer_integrity_after_delete(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);

                list.deleteFirst();

                Node<Integer> newFirst = nodeAtIndexFromFirstElement(0);
                Node<Integer> head = getHead();
                softly.assertThat(getValue(newFirst)).isEqualTo(2);
                softly.assertThat(getNext(head))
                        .as("head.next should point to new first element")
                        .isSameAs(newFirst);
                softly.assertThat(getPrev(newFirst))
                        .as("new first element's prev should point to head")
                        .isSameAs(head);
            }

            @Test
            void throws_on_empty_list() {
                assertThat(list.size).isZero();
                assertThatThrownBy(() -> list.deleteFirst())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class Last {

            @Test
            void removes_and_returns_last_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                int sizeBefore = list.size;

                Integer deleted = list.deleteLast();

                softly.assertThat(deleted).isEqualTo(3);
                softly.assertThat(list.size)
                        .as("size should decrement after deleteLast")
                        .isEqualTo(sizeBefore - 1);
                softly.assertThat(list.getLast()).isEqualTo(2);
            }

            @Test
            void on_single_element_list_makes_list_empty(SoftAssertions softly) {
                list.addLast(42);

                Integer deleted = list.deleteLast();

                softly.assertThat(deleted).isEqualTo(42);
                softly.assertThat(list.size).isZero();
                softly.assertThat(list.isEmpty()).isTrue();
            }

            @Test
            void maintains_pointer_integrity_after_delete(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);

                list.deleteLast();

                Node<Integer> newLast = nodeAtIndexFromFirstElement(1);
                Node<Integer> tail = getTail();
                softly.assertThat(getValue(newLast)).isEqualTo(2);
                softly.assertThat(getPrev(tail))
                        .as("tail.prev should point to new last element")
                        .isSameAs(newLast);
                softly.assertThat(getNext(newLast))
                        .as("new last element's next should point to tail")
                        .isSameAs(tail);
            }

            @Test
            void throws_on_empty_list() {
                assertThat(list.size).isZero();
                assertThatThrownBy(() -> list.deleteLast())
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class At {

            @Test
            void removes_and_returns_element_at_middle_index(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                int sizeBefore = list.size;

                Integer deleted = list.deleteAt(1);

                softly.assertThat(deleted).isEqualTo(2);
                softly.assertThat(list.size)
                        .as("size should decrement after deleteAt")
                        .isEqualTo(sizeBefore - 1);
                softly.assertThat(list.get(0)).isEqualTo(1);
                softly.assertThat(list.get(1)).isEqualTo(3);
            }

            @Test
            void at_index_zero_equivalent_to_deleteFirst(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);

                Integer deleted = list.deleteAt(0);

                softly.assertThat(deleted).isEqualTo(1);
                softly.assertThat(list.size).isEqualTo(1);
                softly.assertThat(list.getFirst()).isEqualTo(2);
            }

            @Test
            void at_last_index_equivalent_to_deleteLast(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);

                Integer deleted = list.deleteAt(1);

                softly.assertThat(deleted).isEqualTo(2);
                softly.assertThat(list.size).isEqualTo(1);
                softly.assertThat(list.getLast()).isEqualTo(1);
            }

            @Test
            void maintains_pointer_integrity_after_delete(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);

                list.deleteAt(1);

                Node<Integer> node1 = nodeAtIndexFromFirstElement(0);
                Node<Integer> node3 = nodeAtIndexFromFirstElement(1);
                softly.assertThat(getNext(node1))
                        .as("node holding 1 should now point directly to node holding 3")
                        .isSameAs(node3);
                softly.assertThat(getPrev(node3))
                        .as("node holding 3 should now point directly back to node holding 1")
                        .isSameAs(node1);
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
            void throws_on_empty_list() {
                assertThat(list.size).isZero();
                assertThatThrownBy(() -> list.deleteAt(0))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void large_list_delete_from_first_half(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                Integer deleted = list.deleteAt(20);

                softly.assertThat(deleted).isEqualTo(20);
                softly.assertThat(list.size).isEqualTo(99);
                softly.assertThat(list.get(19)).isEqualTo(19);
                softly.assertThat(list.get(20)).isEqualTo(21);
            }

            @Test
            void large_list_delete_from_second_half(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                Integer deleted = list.deleteAt(75);

                softly.assertThat(deleted).isEqualTo(75);
                softly.assertThat(list.size).isEqualTo(99);
                softly.assertThat(list.get(74)).isEqualTo(74);
                softly.assertThat(list.get(75)).isEqualTo(76);
            }

            @Test
            void large_list_delete_near_tail_verifies_reverse_traversal(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                Integer deleted = list.deleteAt(95);

                softly.assertThat(deleted).isEqualTo(95);
                softly.assertThat(list.size).isEqualTo(99);
                softly.assertThat(list.get(94)).isEqualTo(94);
                softly.assertThat(list.get(95)).isEqualTo(96);
            }

            @Test
            void large_list_multiple_deletes_from_both_ends(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.deleteAt(10);  // first half
                list.deleteAt(80);  // second half (now 81 because of prev delete)

                softly.assertThat(list.size).isEqualTo(98);
                softly.assertThat(list.get(9)).isEqualTo(9);
                softly.assertThat(list.get(10)).isEqualTo(11);
            }
        }

        @Nested
        class ByValue {

            @Test
            void removes_existing_value_and_returns_true(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                int sizeBefore = list.size;

                boolean removed = list.deleteValue(2);

                softly.assertThat(removed).isTrue();
                softly.assertThat(list.size)
                        .as("size should decrement after deleteValue")
                        .isEqualTo(sizeBefore - 1);
                softly.assertThat(list.get(0)).isEqualTo(1);
                softly.assertThat(list.get(1)).isEqualTo(3);
            }

            @Test
            void removes_first_occurrence_only(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(1);
                int sizeBefore = list.size;

                boolean removed = list.deleteValue(1);

                softly.assertThat(removed).isTrue();
                softly.assertThat(list.size).isEqualTo(sizeBefore - 1);
                softly.assertThat(list.get(0)).isEqualTo(2);
                softly.assertThat(list.get(1)).isEqualTo(1);
            }

            @Test
            void returns_false_when_value_not_found(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                int sizeBefore = list.size;

                boolean removed = list.deleteValue(99);

                softly.assertThat(removed).isFalse();
                softly.assertThat(list.size)
                        .as("size should not change when value not found")
                        .isEqualTo(sizeBefore);
            }

            @Test
            void returns_false_on_empty_list() {
                assertThat(list.size).isZero();

                boolean removed = list.deleteValue(1);

                assertThat(removed).isFalse();
            }
        }
    }

    //endregion
    //region - Update
    @Nested
    class Update {

        @Nested
        class SetAt {

            @Test
            void updates_value_at_index(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                int sizeBefore = list.size;

                list.setAt(1, 99);

                softly.assertThat(list.size)
                        .as("size should not change after setAt")
                        .isEqualTo(sizeBefore);
                softly.assertThat(list.get(0)).isEqualTo(1);
                softly.assertThat(list.get(1)).isEqualTo(99);
                softly.assertThat(list.get(2)).isEqualTo(3);
            }

            @Test
            void updates_first_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);

                list.setAt(0, 99);

                softly.assertThat(list.getFirst()).isEqualTo(99);
                softly.assertThat(list.get(1)).isEqualTo(2);
            }

            @Test
            void updates_last_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);

                list.setAt(1, 99);

                softly.assertThat(list.get(0)).isEqualTo(1);
                softly.assertThat(list.getLast()).isEqualTo(99);
            }

            @Test
            void throws_on_negative_index() {
                list.addLast(1);
                assertThatThrownBy(() -> list.setAt(-1, 99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_index_equal_to_size() {
                list.addLast(1);
                assertThatThrownBy(() -> list.setAt(1, 99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void throws_on_empty_list() {
                assertThat(list.size).isZero();
                assertThatThrownBy(() -> list.setAt(0, 99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            void large_list_update_first_half_index(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.setAt(25, 999);

                softly.assertThat(list.size).isEqualTo(100);
                softly.assertThat(list.get(24)).isEqualTo(24);
                softly.assertThat(list.get(25)).isEqualTo(999);
                softly.assertThat(list.get(26)).isEqualTo(26);
            }

            @Test
            void large_list_update_second_half_index(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.setAt(80, 888);

                softly.assertThat(list.size).isEqualTo(100);
                softly.assertThat(list.get(79)).isEqualTo(79);
                softly.assertThat(list.get(80)).isEqualTo(888);
                softly.assertThat(list.get(81)).isEqualTo(81);
            }

            @Test
            void large_list_update_near_tail_verifies_reverse_traversal(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.setAt(95, 777);

                softly.assertThat(list.size).isEqualTo(100);
                softly.assertThat(list.get(94)).isEqualTo(94);
                softly.assertThat(list.get(95)).isEqualTo(777);
                softly.assertThat(list.get(96)).isEqualTo(96);
            }

            @Test
            void large_list_multiple_updates_from_both_ends(SoftAssertions softly) {
                for (int i = 0; i < 100; i++) {
                    list.addLast(i);
                }

                list.setAt(10, 1010);  // first half
                list.setAt(85, 8585);  // second half
                list.setAt(50, 5050);  // middle

                softly.assertThat(list.size).isEqualTo(100);
                softly.assertThat(list.get(10)).isEqualTo(1010);
                softly.assertThat(list.get(85)).isEqualTo(8585);
                softly.assertThat(list.get(50)).isEqualTo(5050);
            }
        }

        @Nested
        class SetFirst {

            @Test
            void updates_first_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                int sizeBefore = list.size;

                list.setFirst(99);

                softly.assertThat(list.size)
                        .as("size should not change after setFirst")
                        .isEqualTo(sizeBefore);
                softly.assertThat(list.getFirst()).isEqualTo(99);
                softly.assertThat(list.get(1)).isEqualTo(2);
            }

            @Test
            void throws_on_empty_list() {
                assertThat(list.size).isZero();
                assertThatThrownBy(() -> list.setFirst(99))
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        class SetLast {

            @Test
            void updates_last_element(SoftAssertions softly) {
                list.addLast(1);
                list.addLast(2);
                int sizeBefore = list.size;

                list.setLast(99);

                softly.assertThat(list.size)
                        .as("size should not change after setLast")
                        .isEqualTo(sizeBefore);
                softly.assertThat(list.get(0)).isEqualTo(1);
                softly.assertThat(list.getLast()).isEqualTo(99);
            }

            @Test
            void throws_on_empty_list() {
                assertThat(list.size).isZero();
                assertThatThrownBy(() -> list.setLast(99))
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
            for (int i = 0; i < 100; i++) {
                list.addLast(i);
            }
            softly.assertThat(list.size).isEqualTo(100);
            softly.assertThat(list.getFirst()).isEqualTo(0);
            softly.assertThat(list.getLast()).isEqualTo(99);

            for (int i = 0; i < 50; i++) {
                list.deleteFirst();
            }
            softly.assertThat(list.size).isEqualTo(50);
            softly.assertThat(list.getFirst()).isEqualTo(50);
            softly.assertThat(list.getLast()).isEqualTo(99);
        }

        @Test
        void interleaved_add_first_and_add_last(SoftAssertions softly) {
            for (int i = 0; i < 50; i++) {
                list.addFirst(i);
                list.addLast(i + 100);
            }

            softly.assertThat(list.size).isEqualTo(100);
            softly.assertThat(list.getFirst()).isEqualTo(49);
            softly.assertThat(list.getLast()).isEqualTo(149);
        }

        @Test
        void repeated_add_and_remove_to_single_element(SoftAssertions softly) {
            for (int i = 0; i < 100; i++) {
                list.addLast(i);
                softly.assertThat(list.size).isEqualTo(1);
                Integer deleted = list.deleteFirst();
                softly.assertThat(deleted).isEqualTo(i);
                softly.assertThat(list.size).isZero();
            }
        }
    }
    //endregion

    //region - Sentinel Integrity
    @Nested
    class SentinelIntegrity {

        @Test
        void head_and_tail_remain_connected_after_all_elements_removed(SoftAssertions softly) {
            list.addLast(1);
            list.addLast(2);
            list.deleteFirst();
            list.deleteFirst();

            Node<Integer> head = getHead();
            Node<Integer> tail = getTail();

            softly.assertThat(list.size).isZero();
            softly.assertThat(getNext(head))
                    .as("head.next should point to tail when list is empty")
                    .isSameAs(tail);
            softly.assertThat(getPrev(tail))
                    .as("tail.prev should point to head when list is empty")
                    .isSameAs(head);
        }

        @Test
        void head_sentinel_value_is_preserved(SoftAssertions softly) {
            Node<Integer> head = getHead();
            softly.assertThat(getValue(head)).isEqualTo(Integer.MIN_VALUE);

            list.addFirst(1);
            list.addFirst(2);
            list.deleteFirst();

            head = getHead();
            softly.assertThat(getValue(head))
                    .as("head sentinel value should not change")
                    .isEqualTo(Integer.MIN_VALUE);
        }

        @Test
        void tail_sentinel_value_is_preserved(SoftAssertions softly) {
            Node<Integer> tail = getTail();
            softly.assertThat(getValue(tail)).isEqualTo(Integer.MAX_VALUE);

            list.addLast(1);
            list.addLast(2);
            list.deleteLast();

            tail = getTail();
            softly.assertThat(getValue(tail))
                    .as("tail sentinel value should not change")
                    .isEqualTo(Integer.MAX_VALUE);
        }

        @Test
        void first_element_prev_points_to_head(SoftAssertions softly) {
            list.addLast(1);
            list.addLast(2);

            Node<Integer> head = getHead();
            Node<Integer> firstNode = nodeAtIndexFromFirstElement(0);

            softly.assertThat(getPrev(firstNode))
                    .as("first element's prev should point to head sentinel")
                    .isSameAs(head);
            softly.assertThat(getNext(head))
                    .as("head's next should point to first element")
                    .isSameAs(firstNode);
        }

        @Test
        void last_element_next_points_to_tail(SoftAssertions softly) {
            list.addLast(1);
            list.addLast(2);

            Node<Integer> tail = getTail();
            Node<Integer> lastNode = nodeAtIndexFromFirstElement(1);

            softly.assertThat(getNext(lastNode))
                    .as("last element's next should point to tail sentinel")
                    .isSameAs(tail);
            softly.assertThat(getPrev(tail))
                    .as("tail's prev should point to last element")
                    .isSameAs(lastNode);
        }
    }
    //endregion

    //region - Bidirectional Navigation
    @Nested
    class BidirectionalNavigation {

        @Test
        void forward_traversal_visits_all_elements_in_order(SoftAssertions softly) {
            for (int i = 0; i < 5; i++) {
                list.addLast(i);
            }

            Node<Integer> current = getNext(getHead());
            for (int i = 0; i < 5; i++) {
                softly.assertThat(getValue(current))
                        .as("forward traversal at index %d", i)
                        .isEqualTo(i);
                current = getNext(current);
            }
            softly.assertThat(current)
                    .as("should reach tail after traversing all elements")
                    .isSameAs(getTail());
        }

        @Test
        void backward_traversal_visits_all_elements_in_reverse_order(SoftAssertions softly) {
            for (int i = 0; i < 5; i++) {
                list.addLast(i);
            }

            Node<Integer> current = getPrev(getTail());
            for (int i = 4; i >= 0; i--) {
                softly.assertThat(getValue(current))
                        .as("backward traversal expecting value %d", i)
                        .isEqualTo(i);
                current = getPrev(current);
            }
            softly.assertThat(current)
                    .as("should reach head after traversing all elements backwards")
                    .isSameAs(getHead());
        }

        @Test
        void accessing_from_both_ends_uses_get_within_bounds(SoftAssertions softly) {
            for (int i = 0; i < 20; i++) {
                list.addLast(i);
            }

            softly.assertThat(list.size).isEqualTo(20);
            softly.assertThat(list.get(0)).isEqualTo(0);
            softly.assertThat(list.get(10)).isEqualTo(10);
            softly.assertThat(list.get(19)).isEqualTo(19);
        }
    }

    //endregion

	//region - Edge Cases
	@Nested
	class EdgeCases {

		@Nested
		class BoundaryValues {
			@Test
			void handles_max_integer_throughout_operations(SoftAssertions softly) {
				list.addLast(Integer.MAX_VALUE);
				list.addLast(Integer.MAX_VALUE - 1);
				softly.assertThat(list.getFirst()).isEqualTo(Integer.MAX_VALUE);
				softly.assertThat(list.get(1)).isEqualTo(Integer.MAX_VALUE - 1);
				list.setAt(0, Integer.MAX_VALUE - 2);
				softly.assertThat(list.getFirst()).isEqualTo(Integer.MAX_VALUE - 2);
			}

			@Test
			void handles_min_integer_throughout_operations(SoftAssertions softly) {
				list.addFirst(Integer.MIN_VALUE);
				list.addFirst(Integer.MIN_VALUE + 1);
				softly.assertThat(list.getFirst()).isEqualTo(Integer.MIN_VALUE + 1);
				softly.assertThat(list.get(1)).isEqualTo(Integer.MIN_VALUE);
			}

			@Test
			void handles_zero_mixed_with_boundaries(SoftAssertions softly) {
				list.addLast(Integer.MIN_VALUE);
				list.addLast(0);
				list.addLast(Integer.MAX_VALUE);
				softly.assertThat(list.size).isEqualTo(3);
				softly.assertThat(list.get(1)).isZero();
			}
		}

		@Nested
		class SingleElementOperations {
			@Test
			void delete_and_readd_repeatedly(SoftAssertions softly) {
				for (int i = 0; i < 10; i++) {
					list.addLast(i);
					softly.assertThat(list.size).isEqualTo(1);
					list.deleteFirst();
					softly.assertThat(list.size).isZero();
				}
			}

			@Test
			void operations_on_single_element_list(SoftAssertions softly) {
				list.addLast(42);
				softly.assertThat(list.getFirst()).isEqualTo(list.getLast());
				softly.assertThat(list.get(0)).isEqualTo(42);
				softly.assertThat(list.getFirstIndexOf(42)).isZero();
				softly.assertThat(list.getLastIndexOf(42)).isZero();
				list.setAt(0, 99);
				softly.assertThat(list.getFirst()).isEqualTo(99);
			}
		}

		@Nested
		class AllDuplicates {
			@Test
			void list_with_all_same_values(SoftAssertions softly) {
				for (int i = 0; i < 10; i++)
					list.addLast(5);
				softly.assertThat(list.size).isEqualTo(10);
				softly.assertThat(list.getFirstIndexOf(5)).isZero();
				softly.assertThat(list.getLastIndexOf(5)).isEqualTo(9);
				softly.assertThat(list.getAllIndices(5)).hasSize(10);
			}

			@Test
			void delete_from_all_duplicates(SoftAssertions softly) {
				for (int i = 0; i < 5; i++)
					list.addLast(7);
				boolean removed = list.deleteValue(7);
				softly.assertThat(removed).isTrue();
				softly.assertThat(list.size).isEqualTo(4);
				softly.assertThat(list.getFirstIndexOf(7)).isZero();
			}
		}

		@Nested
		class AlternatingValues {
			@Test
			void handles_alternating_pattern(SoftAssertions softly) {
				for (int i = 0; i < 10; i++)
					list.addLast(i % 2);
				softly.assertThat(list.size).isEqualTo(10);
				softly.assertThat(list.getFirstIndexOf(0)).isZero();
				softly.assertThat(list.getFirstIndexOf(1)).isEqualTo(1);
				softly.assertThat(list.getLastIndexOf(0)).isEqualTo(8);
				softly.assertThat(list.getLastIndexOf(1)).isEqualTo(9);
			}
		}

		@Nested
		class RapidOperations {
			@Test
			void rapid_add_delete_cycles(SoftAssertions softly) {
				for (int i = 0; i < 100; i++) {
					list.addLast(i);
					list.addFirst(i * 2);
					if (list.size > 5) {
						list.deleteFirst();
						list.deleteLast();
					}
				}
				softly.assertThat(list.size).isGreaterThan(0);
			}

			@Test
			void mixed_read_write_operations(SoftAssertions softly) {
				for (int i = 0; i < 50; i++) {
					list.addLast(i);
					if (i % 5 == 0 && i > 0) {
						Integer val = list.get(i / 2);
						list.setAt(i / 3, val + 100);
					}
				}
				softly.assertThat(list.size).isEqualTo(50);
			}
		}
	}
	//endregion

	//region - Integration
	@Nested
	class Integration {

		//region - Bidirectional Traversal
		@Nested
		class Bidirectional_Traversal {

			@Test
			void forward_and_backward_traversal_match(SoftAssertions softly) {
				for (int i = 0; i < 100; i++) {
					list.addLast(i);
				}

				java.util.List<Integer> forward = new java.util.ArrayList<>();
				for (int i = 0; i < list.size; i++) {
					forward.add(list.get(i));
				}

				java.util.List<Integer> backward = new java.util.ArrayList<>();
				for (int i = list.size - 1; i >= 0; i--) {
					backward.add(list.get(i));
				}
				java.util.Collections.reverse(backward);

				softly.assertThat(forward).isEqualTo(backward);
			}

			@Test
			void prev_next_pointer_consistency(SoftAssertions softly) {
				for (int i = 0; i < 50; i++) {
					list.addLast(i);
				}

				Node<Integer> current = list.head.next;
				while (current != list.tail) {
					if (current.prev != list.head) {
						softly.assertThat(current.prev.next).isSameAs(current);
					}
					if (current.next != list.tail) {
						softly.assertThat(current.next.prev).isSameAs(current);
					}
					current = current.next;
				}
			}
		}
		//endregion

		//region - Deque Operations
		@Nested
		class Deque_Operations {

			@Test
			void front_back_alternating_operations(SoftAssertions softly) {
				for (int i = 0; i < 50; i++) {
					list.addFirst(i);
					list.addLast(100 + i);
				}

				softly.assertThat(list.size).isEqualTo(100);
				softly.assertThat(list.getFirst()).isEqualTo(49);
				softly.assertThat(list.getLast()).isEqualTo(149);
			}

			@Test
			void interleaved_add_remove_both_ends(SoftAssertions softly) {
				for (int i = 0; i < 25; i++) {
					list.addFirst(i);
					list.addLast(100 + i);
					if (i > 0) {
						list.deleteFirst();
						list.deleteLast();
					}
				}

				softly.assertThat(list.size).isGreaterThan(0);
			}
		}
		//endregion

		//region - Sentinel Node Integrity
		@Nested
		class Sentinel_Node_Integrity {

			@Test
			void head_sentinel_never_changes(SoftAssertions softly) {
				Node<Integer> originalHead = list.head;

				for (int i = 0; i < 100; i++) {
					list.addFirst(i);
				}
				for (int i = 0; i < 50; i++) {
					list.deleteFirst();
				}

				softly.assertThat(list.head).isSameAs(originalHead);
			}

			@Test
			void tail_sentinel_never_changes(SoftAssertions softly) {
				Node<Integer> originalTail = list.tail;

				for (int i = 0; i < 100; i++) {
					list.addLast(i);
				}
				for (int i = 0; i < 50; i++) {
					list.deleteLast();
				}

				softly.assertThat(list.tail).isSameAs(originalTail);
			}

			@Test
			void empty_list_sentinel_connection(SoftAssertions softly) {
				for (int i = 0; i < 50; i++) {
					list.addLast(i);
				}
				for (int i = 0; i < 50; i++) {
					list.deleteFirst();
				}

				softly.assertThat(list.head.next).isSameAs(list.tail);
				softly.assertThat(list.tail.prev).isSameAs(list.head);
				softly.assertThat(list.isEmpty()).isTrue();
			}
		}
		//endregion

		//region - Node Operations
		@Nested
		class Node_Operations {

			@Test
			void addBefore_maintains_chain_integrity(SoftAssertions softly) {
				list.addLast(1);
				list.addLast(3);
				list.addLast(5);

				Node<Integer> node3 = list.getNode(1);
				list.addBefore(node3, 2);

				softly.assertThat(list.get(0)).isEqualTo(1);
				softly.assertThat(list.get(1)).isEqualTo(2);
				softly.assertThat(list.get(2)).isEqualTo(3);
				softly.assertThat(list.get(3)).isEqualTo(5);
			}

			@Test
			void addAfter_maintains_chain_integrity(SoftAssertions softly) {
				list.addLast(1);
				list.addLast(3);
				list.addLast(5);

				Node<Integer> node1 = list.getNode(0);
				list.addAfter(node1, 2);

				softly.assertThat(list.get(0)).isEqualTo(1);
				softly.assertThat(list.get(1)).isEqualTo(2);
				softly.assertThat(list.get(2)).isEqualTo(3);
				softly.assertThat(list.get(3)).isEqualTo(5);
			}
		}
		//endregion

		//region - Search Operations
		@Nested
		class Search_Operations {

			@Test
			void getAllIndices_with_duplicates(SoftAssertions softly) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 5; j++) {
						list.addLast(j);
					}
				}

				List<Integer> indicesOf2 = list.getAllIndices(2);
				softly.assertThat(indicesOf2).hasSize(10);
				softly.assertThat(indicesOf2).containsExactly(2, 7, 12, 17, 22, 27, 32, 37, 42, 47);
			}

			@Test
			void first_last_index_consistency(SoftAssertions softly) {
				for (int i = 0; i < 100; i++) {
					list.addLast(i % 10);
				}

				for (int val = 0; val < 10; val++) {
					int first = list.getFirstIndexOf(val);
					int last = list.getLastIndexOf(val);
					List<Integer> all = list.getAllIndices(val);

					softly.assertThat(first).isEqualTo(all.getFirst());
					softly.assertThat(last).isEqualTo(all.getLast());
				}
			}
		}
		//endregion

		//region - Bulk Operations
		@Nested
		class Bulk_Operations {

			@Test
			void rebuild_after_complete_deletion(SoftAssertions softly) {
				for (int i = 0; i < 100; i++) {
					list.addLast(i);
				}

				while (!list.isEmpty()) {
					list.deleteFirst();
				}

				softly.assertThat(list.isEmpty()).isTrue();
				softly.assertThat(list.head.next).isSameAs(list.tail);

				for (int i = 1000; i < 1050; i++) {
					list.addLast(i);
				}

				softly.assertThat(list.getFirst()).isEqualTo(1000);
				softly.assertThat(list.getLast()).isEqualTo(1049);
			}

			@Test
			void toArray_reflects_modifications(SoftAssertions softly) {
				for (int i = 0; i < 20; i++) {
					list.addLast(i);
				}

				Object[] before = list.toArray();
				softly.assertThat(before).hasSize(20);

				for (int i = 0; i < 5; i++) {
					list.deleteFirst();
				}

				Object[] after = list.toArray();
				softly.assertThat(after).hasSize(15);
				softly.assertThat(after[0]).isEqualTo(5);
			}
		}
		//endregion

		//region - Boundary_Values
		@Nested
		class Boundary_Values {

			@Test
			void single_element_boundary(SoftAssertions softly) {
				list.addFirst(42);

				softly.assertThat(list.getFirst()).isEqualTo(42);
				softly.assertThat(list.getLast()).isEqualTo(42);
				softly.assertThat(list.size).isEqualTo(1);

				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(42);
				softly.assertThat(list.isEmpty()).isTrue();
			}

			@Test
			void two_element_operations(SoftAssertions softly) {
				list.addFirst(1);
				list.addLast(2);

				softly.assertThat(list.getFirst()).isEqualTo(1);
				softly.assertThat(list.getLast()).isEqualTo(2);

				list.setFirst(10);
				list.setLast(20);

				softly.assertThat(list.getFirst()).isEqualTo(10);
				softly.assertThat(list.getLast()).isEqualTo(20);
			}

			@Test
			void boundary_integer_values(SoftAssertions softly) {
				list.addLast(Integer.MIN_VALUE + 1);
				list.addLast(0);
				list.addLast(Integer.MAX_VALUE - 1);

				softly.assertThat(list.getFirst()).isEqualTo(Integer.MIN_VALUE + 1);
				softly.assertThat(list.getLast()).isEqualTo(Integer.MAX_VALUE - 1);
				softly.assertThat(list.get(1)).isZero();
			}
		}
		//endregion

		//region - Performance Scenarios
		@Nested
		class Performance_Scenarios {

			@Test
			void large_list_bidirectional_access(SoftAssertions softly) {
				int size = 1000;

				for (int i = 0; i < size; i++) {
					list.addLast(i);
				}

				softly.assertThat(list.get(0)).isZero();
				softly.assertThat(list.get(10)).isEqualTo(10);

				softly.assertThat(list.get(size - 1)).isEqualTo(size - 1);
				softly.assertThat(list.get(size - 10)).isEqualTo(size - 10);

				softly.assertThat(list.get(size / 2)).isEqualTo(size / 2);
			}

			@Test
			void continuous_mixed_operations(SoftAssertions softly) {
				java.util.Random random = new java.util.Random(42);

				for (int i = 0; i < 500; i++) {
					int op = random.nextInt(6);
					switch (op) {
						case 0 -> list.addFirst(i);
						case 1 -> list.addLast(i);
						case 2 -> { if (!list.isEmpty()) list.deleteFirst(); }
						case 3 -> { if (!list.isEmpty()) list.deleteLast(); }
						case 4 -> { if (!list.isEmpty()) list.setFirst(i * 2); }
						case 5 -> { if (!list.isEmpty()) list.setLast(i * 2); }
					}

					if (!list.isEmpty()) {
						softly.assertThat(list.head.next).isNotNull();
						softly.assertThat(list.tail.prev).isNotNull();
					}
				}
			}
		}
		//endregion
	}
	//endregion

}
