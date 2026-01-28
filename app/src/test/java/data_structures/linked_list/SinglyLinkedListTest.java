package data_structures.linked_list;

import java.util.ArrayList;
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

import data_structures.linked_list.SinglyLinkedList.Node;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class SinglyLinkedListTest {
	SinglyLinkedList<Integer> list;

	@BeforeEach
	void setUp() {
		list = new SinglyLinkedList<>();
	}

	@Nested
	class Add {
		@Nested
		class AddFirst {

			@Test
			void adds_to_empty_list(SoftAssertions softly) {
				list.addFirst(42);
				softly.assertThat(list.head.value).isEqualTo(42);
				softly.assertThat(list.tail).isSameAs(list.head);
				softly.assertThat(list.head.next).isNull();
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void prepends_to_single_element_list(SoftAssertions softly) {
				buildList(10);
				list.addFirst(5);
				softly.assertThat(list.head.value).isEqualTo(5);
				softly.assertThat(list.head.next.value).isEqualTo(10);
				softly.assertThat(list.tail.value).isEqualTo(10);
				softly.assertThat(list.size).isEqualTo(2);
			}

			@Test
			void prepends_to_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				list.addFirst(5);
				softly.assertThat(collectValues()).containsExactly(5, 10, 20);
				softly.assertThat(list.size).isEqualTo(3);
			}

			@Test
			void prepends_to_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.addFirst(-1);
				softly.assertThat(list.head.value).isEqualTo(-1);
				softly.assertThat(list.head.next.value).isEqualTo(0);
				softly.assertThat(list.tail.value).isEqualTo(99);
				softly.assertThat(list.size).isEqualTo(101);
			}

			@Test
			void adds_null_value() {
				list.addFirst(null);
				assertThat(list.head.value).isNull();
			}

			@Test
			void adds_max_integer() {
				list.addFirst(Integer.MAX_VALUE);
				assertThat(list.head.value).isEqualTo(Integer.MAX_VALUE);
			}

			@Test
			void adds_min_integer() {
				list.addFirst(Integer.MIN_VALUE);
				assertThat(list.head.value).isEqualTo(Integer.MIN_VALUE);
			}

			@Test
			void consecutive_adds_maintain_LIFO_order(SoftAssertions softly) {
				list.addFirst(3);
				list.addFirst(2);
				list.addFirst(1);
				softly.assertThat(collectValues()).containsExactly(1, 2, 3);
			}

			@Test
			void adds_duplicate_values(SoftAssertions softly) {
				list.addFirst(42);
				list.addFirst(42);
				list.addFirst(42);
				softly.assertThat(collectValues()).containsExactly(42, 42, 42);
				softly.assertThat(list.size).isEqualTo(3);
			}
		}

		@Nested
		class AddLast {

			@Test
			void adds_to_empty_list(SoftAssertions softly) {
				list.addLast(42);
				softly.assertThat(list.head.value).isEqualTo(42);
				softly.assertThat(list.tail).isSameAs(list.head);
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void appends_to_single_element_list(SoftAssertions softly) {
				buildList(10);
				list.addLast(20);
				softly.assertThat(list.head.value).isEqualTo(10);
				softly.assertThat(list.tail.value).isEqualTo(20);
				softly.assertThat(list.head.next).isSameAs(list.tail);
				softly.assertThat(list.size).isEqualTo(2);
			}

			@Test
			void appends_to_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				list.addLast(30);
				softly.assertThat(collectValues()).containsExactly(10, 20, 30);
				softly.assertThat(list.tail.value).isEqualTo(30);
				softly.assertThat(list.tail.next).isNull();
				softly.assertThat(list.size).isEqualTo(3);
			}

			@Test
			void appends_to_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.addLast(999);
				softly.assertThat(list.head.value).isEqualTo(0);
				softly.assertThat(list.tail.value).isEqualTo(999);
				softly.assertThat(list.size).isEqualTo(101);
			}

			@Test
			void adds_null_value() {
				list.addLast(null);
				assertThat(list.tail.value).isNull();
			}

			@Test
			void consecutive_adds_maintain_FIFO_order(SoftAssertions softly) {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				softly.assertThat(collectValues()).containsExactly(1, 2, 3);
			}

			@Test
			void adds_duplicate_values(SoftAssertions softly) {
				list.addLast(7);
				list.addLast(7);
				list.addLast(7);
				softly.assertThat(collectValues()).containsExactly(7, 7, 7);
			}

			@Test
			void alternating_negative_positive_values(SoftAssertions softly) {
				list.addLast(-1);
				list.addLast(1);
				list.addLast(-2);
				list.addLast(2);
				softly.assertThat(collectValues()).containsExactly(-1, 1, -2, 2);
			}
		}

		@Nested
		class AddAt {

			@Test
			void adds_at_index_zero_to_empty_list(SoftAssertions softly) {
				list.addAt(0, 42);
				softly.assertThat(list.head.value).isEqualTo(42);
				softly.assertThat(list.tail).isSameAs(list.head);
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void throws_on_index_one_for_empty_list() {
				assertThatThrownBy(() -> list.addAt(1, 42))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_negative_index() {
				assertThatThrownBy(() -> list.addAt(-1, 42))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void adds_at_index_zero_shifts_single_element(SoftAssertions softly) {
				buildList(10);
				list.addAt(0, 5);
				softly.assertThat(collectValues()).containsExactly(5, 10);
				softly.assertThat(list.size).isEqualTo(2);
			}

			@Test
			void adds_at_index_one_appends_to_single_element(SoftAssertions softly) {
				buildList(10);
				list.addAt(1, 20);
				softly.assertThat(collectValues()).containsExactly(10, 20);
				softly.assertThat(list.tail.value).isEqualTo(20);
			}

			@Test
			void throws_on_index_two_for_single_element() {
				buildList(10);
				assertThatThrownBy(() -> list.addAt(2, 30))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void adds_at_beginning_of_two_element_list(SoftAssertions softly) {
				buildList(10, 30);
				list.addAt(0, 5);
				softly.assertThat(collectValues()).containsExactly(5, 10, 30);
			}

			@Test
			void adds_at_middle_of_two_element_list(SoftAssertions softly) {
				buildList(10, 30);
				list.addAt(1, 20);
				softly.assertThat(collectValues()).containsExactly(10, 20, 30);
			}

			@Test
			void adds_at_end_of_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				list.addAt(2, 30);
				softly.assertThat(collectValues()).containsExactly(10, 20, 30);
				softly.assertThat(list.tail.value).isEqualTo(30);
			}

			@Test
			void adds_at_beginning_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.addAt(0, -1);
				softly.assertThat(list.head.value).isEqualTo(-1);
				softly.assertThat(list.size).isEqualTo(101);
			}

			@Test
			void adds_at_middle_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.addAt(50, 999);
				List<Integer> values = collectValues();
				softly.assertThat(values.get(49)).isEqualTo(49);
				softly.assertThat(values.get(50)).isEqualTo(999);
				softly.assertThat(values.get(51)).isEqualTo(50);
			}

			@Test
			void adds_at_end_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.addAt(100, 999);
				softly.assertThat(list.tail.value).isEqualTo(999);
				softly.assertThat(list.size).isEqualTo(101);
			}

			@Test
			void throws_on_index_greater_than_size() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.addAt(10, 99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void adds_null_value_at_index(SoftAssertions softly) {
				buildList(1, 3);
				list.addAt(1, null);
				softly.assertThat(collectValues()).containsExactly(1, null, 3);
			}
		}

		@Nested
		class AddBefore {

			@Test
			void throws_on_empty_list() {
				Node<Integer> dummyNode = new Node<>(42);
				assertThatThrownBy(() -> list.addBefore(dummyNode, 99))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void inserts_before_only_node(SoftAssertions softly) {
				buildList(10);
				Node<Integer> targetNode = list.head;
				list.addBefore(targetNode, 5);
				softly.assertThat(list.head.value).isEqualTo(5);
				softly.assertThat(list.head.next.value).isEqualTo(10);
				softly.assertThat(list.size).isEqualTo(2);
			}

			@Test
			void inserts_before_head_of_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				list.addBefore(list.head, 5);
				softly.assertThat(collectValues()).containsExactly(5, 10, 20);
			}

			@Test
			void inserts_before_tail_of_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				list.addBefore(list.tail, 15);
				softly.assertThat(collectValues()).containsExactly(10, 15, 20);
			}

			@Test
			void inserts_before_middle_node_in_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Node<Integer> current = list.head;
				for (int i = 0; i < 50; i++) current = current.next;
				list.addBefore(current, 999);
				List<Integer> values = collectValues();
				softly.assertThat(values.get(50)).isEqualTo(999);
				softly.assertThat(values.get(51)).isEqualTo(50);
			}
		}

		@Nested
		class AddAfter {

			@Test
			void throws_on_empty_list() {
				Node<Integer> dummyNode = new Node<>(42);
				assertThatThrownBy(() -> list.addAfter(dummyNode, 99))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void inserts_after_only_node(SoftAssertions softly) {
				buildList(10);
				list.addAfter(list.head, 20);
				softly.assertThat(collectValues()).containsExactly(10, 20);
				softly.assertThat(list.size).isEqualTo(2);
			}

			@Test
			void inserts_after_head_of_two_element_list(SoftAssertions softly) {
				buildList(10, 30);
				list.addAfter(list.head, 20);
				softly.assertThat(collectValues()).containsExactly(10, 20, 30);
			}

			@Test
			void inserts_after_tail_of_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				list.addAfter(list.tail, 30);
				softly.assertThat(collectValues()).containsExactly(10, 20, 30);
				softly.assertThat(list.tail.value).isEqualTo(30);
			}

			@Test
			void inserts_after_middle_node_in_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Node<Integer> current = list.head;
				for (int i = 0; i < 50; i++) current = current.next;
				list.addAfter(current, 999);
				List<Integer> values = collectValues();
				softly.assertThat(values.get(50)).isEqualTo(50);
				softly.assertThat(values.get(51)).isEqualTo(999);
			}
		}
	}

	@Nested
	class Delete {
		@Nested
		class DeleteFirst {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.deleteFirst())
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void removes_only_element(SoftAssertions softly) {
				buildList(42);
				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(42);
				softly.assertThat(list.head).isNull();
				softly.assertThat(list.tail).isNull();
				softly.assertThat(list.size).isZero();
			}

			@Test
			void removes_first_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(10);
				softly.assertThat(list.head.value).isEqualTo(20);
				softly.assertThat(list.head).isSameAs(list.tail);
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void removes_first_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(0);
				softly.assertThat(list.head.value).isEqualTo(1);
				softly.assertThat(list.tail.value).isEqualTo(99);
				softly.assertThat(list.size).isEqualTo(99);
			}

			@Test
			void removes_null_value() {
				list.head = new Node<>(null);
				list.tail = list.head;
				list.size = 1;
				Integer removed = list.deleteFirst();
				assertThat(removed).isNull();
			}

			@Test
			void consecutive_deletes_empty_list(SoftAssertions softly) {
				buildList(1, 2, 3);
				list.deleteFirst();
				list.deleteFirst();
				list.deleteFirst();
				softly.assertThat(list.isEmpty()).isTrue();
				softly.assertThatThrownBy(() -> list.deleteFirst())
					.isInstanceOf(IllegalStateException.class);
			}
		}

		@Nested
		class DeleteLast {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.deleteLast())
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void removes_only_element(SoftAssertions softly) {
				buildList(42);
				Integer removed = list.deleteLast();
				softly.assertThat(removed).isEqualTo(42);
				softly.assertThat(list.head).isNull();
				softly.assertThat(list.tail).isNull();
				softly.assertThat(list.size).isZero();
			}

			@Test
			void removes_last_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				Integer removed = list.deleteLast();
				softly.assertThat(removed).isEqualTo(20);
				softly.assertThat(list.tail.value).isEqualTo(10);
				softly.assertThat(list.tail.next).isNull();
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void removes_last_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Integer removed = list.deleteLast();
				softly.assertThat(removed).isEqualTo(99);
				softly.assertThat(list.head.value).isEqualTo(0);
				softly.assertThat(list.tail.value).isEqualTo(98);
				softly.assertThat(list.size).isEqualTo(99);
			}

			@Test
			void consecutive_deletes_from_back(SoftAssertions softly) {
				buildList(1, 2, 3);
				softly.assertThat(list.deleteLast()).isEqualTo(3);
				softly.assertThat(list.deleteLast()).isEqualTo(2);
				softly.assertThat(list.deleteLast()).isEqualTo(1);
				softly.assertThat(list.isEmpty()).isTrue();
			}
		}

		@Nested
		class DeleteAt {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.deleteAt(0))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void deletes_only_element_at_index_zero(SoftAssertions softly) {
				buildList(42);
				Integer removed = list.deleteAt(0);
				softly.assertThat(removed).isEqualTo(42);
				softly.assertThat(list.isEmpty()).isTrue();
			}

			@Test
			void throws_on_index_one_for_single_element() {
				buildList(42);
				assertThatThrownBy(() -> list.deleteAt(1))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void deletes_head_of_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				Integer removed = list.deleteAt(0);
				softly.assertThat(removed).isEqualTo(10);
				softly.assertThat(list.head.value).isEqualTo(20);
			}

			@Test
			void deletes_tail_of_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				Integer removed = list.deleteAt(1);
				softly.assertThat(removed).isEqualTo(20);
				softly.assertThat(list.tail.value).isEqualTo(10);
			}

			@Test
			void deletes_from_middle_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Integer removed = list.deleteAt(50);
				softly.assertThat(removed).isEqualTo(50);
				softly.assertThat(list.size).isEqualTo(99);
			}

			@Test
			void deletes_near_end_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Integer removed = list.deleteAt(95);
				softly.assertThat(removed).isEqualTo(95);
			}

			@Test
			void throws_on_negative_index() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.deleteAt(-1))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_index_equal_to_size() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.deleteAt(3))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_index_greater_than_size() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.deleteAt(100))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void deletes_null_value(SoftAssertions softly) {
				list.head = new Node<>(1);
				list.head.next = new Node<>(null);
				list.head.next.next = new Node<>(3);
				list.tail = list.head.next.next;
				list.size = 3;
				Integer removed = list.deleteAt(1);
				softly.assertThat(removed).isNull();
				softly.assertThat(list.size).isEqualTo(2);
			}
		}

		@Nested
		class DeleteValue {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.deleteValue(42))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void deletes_matching_value_from_single_element(SoftAssertions softly) {
				buildList(42);
				boolean result = list.deleteValue(42);
				softly.assertThat(result).isTrue();
				softly.assertThat(list.isEmpty()).isTrue();
			}

			@Test
			void returns_false_for_non_matching_single_element(SoftAssertions softly) {
				buildList(42);
				boolean result = list.deleteValue(99);
				softly.assertThat(result).isFalse();
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void deletes_head_value_from_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				boolean result = list.deleteValue(10);
				softly.assertThat(result).isTrue();
				softly.assertThat(list.head.value).isEqualTo(20);
			}

			@Test
			void deletes_tail_value_from_two_element_list(SoftAssertions softly) {
				buildList(10, 20);
				boolean result = list.deleteValue(20);
				softly.assertThat(result).isTrue();
				softly.assertThat(list.tail.value).isEqualTo(10);
			}

			@Test
			void deletes_value_from_middle_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				boolean result = list.deleteValue(50);
				softly.assertThat(result).isTrue();
				softly.assertThat(list.size).isEqualTo(99);
			}

			@Test
			void deletes_only_first_occurrence_of_duplicate(SoftAssertions softly) {
				buildList(1, 2, 1, 3, 1);
				boolean result = list.deleteValue(1);
				softly.assertThat(result).isTrue();
				softly.assertThat(list.head.value).isEqualTo(2);
				softly.assertThat(list.size).isEqualTo(4);
			}

			@Test
			void returns_false_for_non_existing_value(SoftAssertions softly) {
				buildList(1, 2, 3);
				boolean result = list.deleteValue(99);
				softly.assertThat(result).isFalse();
				softly.assertThat(list.size).isEqualTo(3);
			}
		}
	}

	@Nested
	class Get {
		@Nested
		class GetFirst {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getFirst())
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_only_element() {
				buildList(42);
				assertThat(list.getFirst()).isEqualTo(42);
			}

			@Test
			void returns_first_of_two_elements() {
				buildList(10, 20);
				assertThat(list.getFirst()).isEqualTo(10);
			}

			@Test
			void returns_first_of_large_list() {
				buildLargeList(100);
				assertThat(list.getFirst()).isEqualTo(0);
			}

			@Test
			void returns_null_if_first_is_null() {
				list.head = new Node<>(null);
				list.tail = list.head;
				list.size = 1;
				assertThat(list.getFirst()).isNull();
			}

			@Test
			void returns_max_integer() {
				buildList(Integer.MAX_VALUE);
				assertThat(list.getFirst()).isEqualTo(Integer.MAX_VALUE);
			}

			@Test
			void returns_min_integer() {
				buildList(Integer.MIN_VALUE);
				assertThat(list.getFirst()).isEqualTo(Integer.MIN_VALUE);
			}
		}

		@Nested
		class GetLast {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getLast())
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_only_element() {
				buildList(42);
				assertThat(list.getLast()).isEqualTo(42);
			}

			@Test
			void equals_getFirst_for_single_element() {
				buildList(42);
				assertThat(list.getLast()).isEqualTo(list.getFirst());
			}

			@Test
			void returns_last_of_two_elements() {
				buildList(10, 20);
				assertThat(list.getLast()).isEqualTo(20);
			}

			@Test
			void returns_last_of_large_list() {
				buildLargeList(100);
				assertThat(list.getLast()).isEqualTo(99);
			}

			@Test
			void returns_null_if_last_is_null() {
				list.head = new Node<>(1);
				list.head.next = new Node<>(null);
				list.tail = list.head.next;
				list.size = 2;
				assertThat(list.getLast()).isNull();
			}
		}

		@Nested
		class GetAt {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getAt(0))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_only_element_at_zero() {
				buildList(42);
				assertThat(list.getAt(0)).isEqualTo(42);
			}

			@Test
			void throws_on_index_one_for_single_element() {
				buildList(42);
				assertThatThrownBy(() -> list.getAt(1))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void returns_first_of_two_elements() {
				buildList(10, 20);
				assertThat(list.getAt(0)).isEqualTo(10);
			}

			@Test
			void returns_second_of_two_elements() {
				buildList(10, 20);
				assertThat(list.getAt(1)).isEqualTo(20);
			}

			@Test
			void returns_elements_at_various_indices_in_large_list(SoftAssertions softly) {
				buildLargeList(100);
				softly.assertThat(list.getAt(0)).isEqualTo(0);
				softly.assertThat(list.getAt(50)).isEqualTo(50);
				softly.assertThat(list.getAt(99)).isEqualTo(99);
			}

			@Test
			void throws_on_negative_index() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.getAt(-1))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_index_equal_to_size() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.getAt(3))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void returns_null_at_index() {
				list.head = new Node<>(1);
				list.head.next = new Node<>(null);
				list.head.next.next = new Node<>(3);
				list.tail = list.head.next.next;
				list.size = 3;
				assertThat(list.getAt(1)).isNull();
			}
		}

		@Nested
		class GetNode {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getNode(0))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_only_node(SoftAssertions softly) {
				buildList(42);
				Node<Integer> node = list.getNode(0);
				softly.assertThat(node).isSameAs(list.head);
				softly.assertThat(node.value).isEqualTo(42);
				softly.assertThat(node.next).isNull();
			}

			@Test
			void returns_first_node_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				Node<Integer> node = list.getNode(0);
				softly.assertThat(node.value).isEqualTo(10);
				softly.assertThat(node.next.value).isEqualTo(20);
			}

			@Test
			void returns_second_node_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				Node<Integer> node = list.getNode(1);
				softly.assertThat(node.value).isEqualTo(20);
				softly.assertThat(node.next).isNull();
			}

			@Test
			void returns_middle_node_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Node<Integer> node = list.getNode(50);
				softly.assertThat(node.value).isEqualTo(50);
				softly.assertThat(node.next.value).isEqualTo(51);
			}

			@Test
			void returns_last_node_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				Node<Integer> node = list.getNode(99);
				softly.assertThat(node).isSameAs(list.tail);
				softly.assertThat(node.next).isNull();
			}

			@Test
			void throws_on_negative_index() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.getNode(-1))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void node_chain_traversal_matches_list(SoftAssertions softly) {
				buildList(1, 2, 3, 4, 5);
				Node<Integer> node = list.getNode(0);
				int count = 0;
				while (node != null) {
					softly.assertThat(node.value).isEqualTo(count + 1);
					node = node.next;
					count++;
				}
				softly.assertThat(count).isEqualTo(5);
			}
		}

		@Nested
		class GetFirstIndexOf {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getFirstIndexOf(42))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_zero_for_matching_single_element() {
				buildList(42);
				assertThat(list.getFirstIndexOf(42)).isZero();
			}

			@Test
			void returns_minus_one_for_non_matching_single_element() {
				buildList(42);
				assertThat(list.getFirstIndexOf(99)).isEqualTo(-1);
			}

			@Test
			void returns_index_of_first_element_in_two_element_list() {
				buildList(10, 20);
				assertThat(list.getFirstIndexOf(10)).isZero();
			}

			@Test
			void returns_index_of_second_element_in_two_element_list() {
				buildList(10, 20);
				assertThat(list.getFirstIndexOf(20)).isEqualTo(1);
			}

			@Test
			void finds_elements_in_large_list(SoftAssertions softly) {
				buildLargeList(100);
				softly.assertThat(list.getFirstIndexOf(0)).isZero();
				softly.assertThat(list.getFirstIndexOf(50)).isEqualTo(50);
				softly.assertThat(list.getFirstIndexOf(99)).isEqualTo(99);
			}

			@Test
			void returns_first_occurrence_with_duplicates() {
				buildList(1, 2, 1, 3, 1);
				assertThat(list.getFirstIndexOf(1)).isZero();
			}
		}

		@Nested
		class GetLastIndexOf {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getLastIndexOf(42))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_zero_for_matching_single_element() {
				buildList(42);
				assertThat(list.getLastIndexOf(42)).isZero();
			}

			@Test
			void returns_minus_one_for_non_matching_single_element() {
				buildList(42);
				assertThat(list.getLastIndexOf(99)).isEqualTo(-1);
			}

			@Test
			void returns_correct_index_for_unique_elements(SoftAssertions softly) {
				buildList(10, 20);
				softly.assertThat(list.getLastIndexOf(10)).isZero();
				softly.assertThat(list.getLastIndexOf(20)).isEqualTo(1);
			}

			@Test
			void finds_last_occurrence_in_large_list_with_duplicate() {
				buildLargeList(50);
				list.tail.next = new Node<>(25);
				list.tail = list.tail.next;
				list.size = 51;
				assertThat(list.getLastIndexOf(25)).isEqualTo(50);
			}

			@Test
			void returns_last_occurrence_with_duplicates() {
				buildList(1, 2, 1, 3, 1);
				assertThat(list.getLastIndexOf(1)).isEqualTo(4);
			}
		}

		@Nested
		class GetAllIndices {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.getAllIndices(42))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void returns_single_index_for_matching_single_element() {
				buildList(42);
				assertThat(list.getAllIndices(42)).containsExactly(0);
			}

			@Test
			void returns_empty_list_for_non_matching_single_element() {
				buildList(42);
				assertThat(list.getAllIndices(99)).isEmpty();
			}

			@Test
			void returns_both_indices_for_duplicates_in_two_element_list() {
				buildList(42, 42);
				assertThat(list.getAllIndices(42)).containsExactly(0, 1);
			}

			@Test
			void returns_single_index_for_unique_in_two_element_list() {
				buildList(10, 20);
				assertThat(list.getAllIndices(10)).containsExactly(0);
			}

			@Test
			void returns_all_indices_in_large_list_with_pattern(SoftAssertions softly) {
				Integer[] values = new Integer[100];
				for (int i = 0; i < 100; i++) values[i] = i % 10;
				buildList(values);
				softly.assertThat(list.getAllIndices(5))
					.containsExactly(5, 15, 25, 35, 45, 55, 65, 75, 85, 95);
			}

			@Test
			void returns_all_indices_when_all_elements_same(SoftAssertions softly) {
				buildList(7, 7, 7, 7, 7);
				softly.assertThat(list.getAllIndices(7)).containsExactly(0, 1, 2, 3, 4);
			}

			@Test
			void returns_empty_for_non_existing_value() {
				buildList(1, 2, 3, 4, 5);
				assertThat(list.getAllIndices(99)).isEmpty();
			}
		}

	}

	@Nested
	class Set {
		@Nested
		class SetFirst {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.setFirst(99))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void updates_only_element(SoftAssertions softly) {
				buildList(42);
				list.setFirst(99);
				softly.assertThat(list.head.value).isEqualTo(99);
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void updates_first_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				list.setFirst(99);
				softly.assertThat(list.head.value).isEqualTo(99);
				softly.assertThat(list.tail.value).isEqualTo(20);
			}

			@Test
			void updates_first_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.setFirst(999);
				softly.assertThat(list.head.value).isEqualTo(999);
				softly.assertThat(list.head.next.value).isEqualTo(1);
			}

			@Test
			void sets_null_value(SoftAssertions softly) {
				buildList(42);
				list.setFirst(null);
				softly.assertThat(list.head.value).isNull();
			}

			@Test
			void does_not_change_size(SoftAssertions softly) {
				buildList(1, 2, 3);
				int originalSize = list.size;
				list.setFirst(99);
				softly.assertThat(list.size).isEqualTo(originalSize);
			}
		}

		@Nested
		class SetLast {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.setLast(99))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void updates_only_element(SoftAssertions softly) {
				buildList(42);
				list.setLast(99);
				softly.assertThat(list.tail.value).isEqualTo(99);
			}

			@Test
			void updates_last_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				list.setLast(99);
				softly.assertThat(list.head.value).isEqualTo(10);
				softly.assertThat(list.tail.value).isEqualTo(99);
			}

			@Test
			void updates_last_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.setLast(999);
				softly.assertThat(list.tail.value).isEqualTo(999);
			}

			@Test
			void sets_null_value() {
				buildList(1, 2, 3);
				list.setLast(null);
				assertThat(list.tail.value).isNull();
			}
		}

		@Nested
		class SetAt {

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.setAt(0, 99))
					.isInstanceOf(IllegalStateException.class);
			}

			@Test
			void updates_only_element_at_index_zero(SoftAssertions softly) {
				buildList(42);
				list.setAt(0, 99);
				softly.assertThat(list.head.value).isEqualTo(99);
			}

			@Test
			void throws_on_index_one_for_single_element() {
				buildList(42);
				assertThatThrownBy(() -> list.setAt(1, 99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void updates_first_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				list.setAt(0, 99);
				softly.assertThat(list.head.value).isEqualTo(99);
				softly.assertThat(list.tail.value).isEqualTo(20);
			}

			@Test
			void updates_second_of_two_elements(SoftAssertions softly) {
				buildList(10, 20);
				list.setAt(1, 99);
				softly.assertThat(list.head.value).isEqualTo(10);
				softly.assertThat(list.tail.value).isEqualTo(99);
			}

			@Test
			void updates_middle_of_large_list(SoftAssertions softly) {
				buildLargeList(100);
				list.setAt(50, 999);
				Node<Integer> current = list.head;
				for (int i = 0; i < 50; i++) current = current.next;
				softly.assertThat(current.value).isEqualTo(999);
			}

			@Test
			void throws_on_negative_index() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.setAt(-1, 99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_index_equal_to_size() {
				buildList(1, 2, 3);
				assertThatThrownBy(() -> list.setAt(3, 99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void does_not_change_size(SoftAssertions softly) {
				buildList(1, 2, 3);
				list.setAt(1, 99);
				softly.assertThat(list.size).isEqualTo(3);
			}

			@Test
			void sets_null_value() {
				buildList(1, 2, 3);
				list.setAt(1, null);
				assertThat(list.head.next.value).isNull();
			}
		}

	}

	@Nested
	class Size {
		@Test
		void returns_zero_for_empty_list() {
			assertThat(list.size()).isZero();
		}

		@Test
		void returns_one_for_single_element() {
			buildList(42);
			assertThat(list.size()).isEqualTo(1);
		}

		@Test
		void returns_two_for_two_elements() {
			buildList(10, 20);
			assertThat(list.size()).isEqualTo(2);
		}

		@Test
		void returns_correct_count_for_large_list() {
			buildLargeList(100);
			assertThat(list.size()).isEqualTo(100);
		}
	}

	@Nested
	class IsEmpty {

		@Test
		void returns_true_for_empty_list() {
			assertThat(list.isEmpty()).isTrue();
		}

		@Test
		void returns_false_for_single_element() {
			buildList(42);
			assertThat(list.isEmpty()).isFalse();
		}

		@Test
		void returns_false_for_two_elements() {
			buildList(10, 20);
			assertThat(list.isEmpty()).isFalse();
		}

		@Test
		void returns_false_for_large_list() {
			buildLargeList(100);
			assertThat(list.isEmpty()).isFalse();
		}
	}

	@Nested
	class ToArray {

		@Test
		void returns_empty_array_for_empty_list() {
			Object[] arr = list.toArray();
			assertThat(arr).isEmpty();
		}

		@Test
		void returns_single_element_array() {
			buildList(42);
			Object[] arr = list.toArray();
			assertThat(arr).containsExactly(42);
		}

		@Test
		void returns_two_element_array_in_order() {
			buildList(10, 20);
			Object[] arr = list.toArray();
			assertThat(arr).containsExactly(10, 20);
		}

		@Test
		void returns_all_elements_in_order_for_large_list(SoftAssertions softly) {
			buildLargeList(100);
			Object[] arr = list.toArray();
			softly.assertThat(arr).hasSize(100);
			softly.assertThat(arr[0]).isEqualTo(0);
			softly.assertThat(arr[50]).isEqualTo(50);
			softly.assertThat(arr[99]).isEqualTo(99);
		}

		@Test
		void preserves_null_values() {
			list.head = new Node<>(1);
			list.head.next = new Node<>(null);
			list.head.next.next = new Node<>(3);
			list.tail = list.head.next.next;
			list.size = 3;
			Object[] arr = list.toArray();
			assertThat(arr).containsExactly(1, null, 3);
		}

		@Test
		void array_is_independent_of_list(SoftAssertions softly) {
			buildList(1, 2, 3);
			Object[] arr = list.toArray();
			list.head.value = 99;
			softly.assertThat(arr[0]).isEqualTo(1);
		}
	}

	@Nested
	class ToString {

		@Test
		void formats_empty_list() {
			assertThat(list.toString()).isEqualTo("[]");
		}

		@Test
		void formats_single_element() {
			buildList(42);
			assertThat(list.toString()).isEqualTo("[42]");
		}

		@Test
		void formats_two_elements() {
			buildList(10, 20);
			String result = list.toString();
			assertThat(result).startsWith("[10");
			assertThat(result).contains("20");
			assertThat(result).endsWith("]");
		}

		@Test
		void formats_large_list_with_arrow_notation(SoftAssertions softly) {
			buildLargeList(5);
			String result = list.toString();
			softly.assertThat(result).startsWith("[0");
			softly.assertThat(result).contains("->");
			softly.assertThat(result).endsWith("]");
		}

		@Test
		void formats_negative_numbers() {
			buildList(-5, 0, 5);
			String result = list.toString();
			assertThat(result).contains("-5");
			assertThat(result).contains("0");
			assertThat(result).contains("5");
		}
	}

	@Nested
	class WithStringType {

		SinglyLinkedList<String> stringList;

		@BeforeEach
		void setUp() {
			stringList = new SinglyLinkedList<>();
		}

		@Test
		void addFirst_with_strings(SoftAssertions softly) {
			stringList.addFirst("hello");
			stringList.addFirst("world");
			softly.assertThat(stringList.head.value).isEqualTo("world");
			softly.assertThat(stringList.tail.value).isEqualTo("hello");
		}

		@Test
		void addLast_with_strings(SoftAssertions softly) {
			stringList.addLast("alpha");
			stringList.addLast("beta");
			stringList.addLast("gamma");
			softly.assertThat(stringList.head.value).isEqualTo("alpha");
			softly.assertThat(stringList.tail.value).isEqualTo("gamma");
			softly.assertThat(stringList.size).isEqualTo(3);
		}

		@Test
		void handles_empty_string() {
			stringList.addFirst("");
			assertThat(stringList.head.value).isEmpty();
		}

		@Test
		void handles_whitespace_string() {
			stringList.addFirst("   ");
			assertThat(stringList.head.value).isEqualTo("   ");
		}

		@Test
		void handles_special_characters() {
			stringList.addFirst("!@#$%^&*()");
			assertThat(stringList.head.value).isEqualTo("!@#$%^&*()");
		}

		@Test
		void handles_unicode_characters() {
			stringList.addFirst("日本語");
			stringList.addLast("中文");
			assertThat(stringList.head.value).isEqualTo("日本語");
			assertThat(stringList.tail.value).isEqualTo("中文");
		}
	}

	@Nested
	class WithCustomObjectType {

		record Point(int x, int y) {
		}

		SinglyLinkedList<Point> pointList;

		@BeforeEach
		void setUp() {
			pointList = new SinglyLinkedList<>();
		}

		@Test
		void addFirst_with_custom_objects(SoftAssertions softly) {
			pointList.addFirst(new Point(0, 0));
			pointList.addFirst(new Point(1, 1));
			softly.assertThat(pointList.head.value).isEqualTo(new Point(1, 1));
			softly.assertThat(pointList.tail.value).isEqualTo(new Point(0, 0));
		}

		@Test
		void addLast_with_custom_objects(SoftAssertions softly) {
			pointList.addLast(new Point(0, 0));
			pointList.addLast(new Point(5, 5));
			pointList.addLast(new Point(10, 10));
			softly.assertThat(pointList.size).isEqualTo(3);
			softly.assertThat(pointList.tail.value).isEqualTo(new Point(10, 10));
		}

		@Test
		void handles_objects_with_same_values() {
			pointList.addLast(new Point(1, 1));
			pointList.addLast(new Point(1, 1));
			pointList.addLast(new Point(1, 1));
			assertThat(pointList.size).isEqualTo(3);
		}
	}

	@Nested
	class Integration {

		@Test
		void build_with_addLast_then_retrieve_all_with_getAt(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			for (int i = 0; i < 100; i++) {
				softly.assertThat(list.getAt(i)).isEqualTo(i);
			}
		}

		@Test
		void build_with_addFirst_then_verify_reverse_order(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addFirst(i);
			}
			softly.assertThat(list.getFirst()).isEqualTo(99);
			softly.assertThat(list.getLast()).isEqualTo(0);
		}

		@Test
		void alternate_addFirst_and_addLast_creates_symmetric_structure(SoftAssertions softly) {
			for (int i = 1; i <= 50; i++) {
				list.addFirst(i);
				list.addLast(i + 100);
			}
			softly.assertThat(list.size()).isEqualTo(100);
			softly.assertThat(list.getFirst()).isEqualTo(50);
			softly.assertThat(list.getLast()).isEqualTo(150);
		}

		@Test
		void build_then_traverse_via_getNode_chain(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			Node<Integer> current = list.getNode(0);
			int count = 0;
			while (current != null) {
				softly.assertThat(current.value).isEqualTo(count);
				current = current.next;
				count++;
			}
			softly.assertThat(count).isEqualTo(100);
		}

		@Test
		void deleteFirst_until_empty(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			for (int i = 0; i < 100; i++) {
				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(i);
			}
			softly.assertThat(list.isEmpty()).isTrue();
		}

		@Test
		void deleteLast_until_empty(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			for (int i = 99; i >= 0; i--) {
				Integer removed = list.deleteLast();
				softly.assertThat(removed).isEqualTo(i);
			}
			softly.assertThat(list.isEmpty()).isTrue();
		}

		@Test
		void alternate_deleteFirst_and_deleteLast_converges_to_empty(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			for (int i = 0; i < 50; i++) {
				list.deleteFirst();
				list.deleteLast();
			}
			softly.assertThat(list.isEmpty()).isTrue();
		}

		@Test
		void delete_from_middle_maintains_chain_integrity(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			list.deleteAt(50);
			list.deleteAt(25);
			list.deleteAt(75);
			softly.assertThat(list.size).isEqualTo(97);
			Node<Integer> current = list.head;
			int count = 0;
			while (current != null) {
				current = current.next;
				count++;
			}
			softly.assertThat(count).isEqualTo(97);
		}

		@Test
		void add_delete_add_cycle_maintains_consistency(SoftAssertions softly) {
			for (int cycle = 0; cycle < 20; cycle++) {
				list.addFirst(cycle);
				softly.assertThat(list.size).isEqualTo(1);
				list.deleteFirst();
				softly.assertThat(list.isEmpty()).isTrue();
			}
		}

		@Test
		void grow_shrink_grow_pattern_preserves_structure(SoftAssertions softly) {
			for (int i = 0; i < 50; i++) {
				list.addLast(i);
			}
			softly.assertThat(list.size).isEqualTo(50);
			for (int i = 0; i < 25; i++) {
				list.deleteFirst();
			}
			softly.assertThat(list.size).isEqualTo(25);
			softly.assertThat(list.getFirst()).isEqualTo(25);
			for (int i = 50; i < 75; i++) {
				list.addLast(i);
			}
			softly.assertThat(list.size).isEqualTo(50);
			softly.assertThat(list.getLast()).isEqualTo(74);
		}

		@Test
		void find_element_then_update_it(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			int targetIndex = list.getFirstIndexOf(50);
			list.setAt(targetIndex, 999);
			softly.assertThat(list.getAt(50)).isEqualTo(999);
			softly.assertThat(list.getFirstIndexOf(999)).isEqualTo(50);
		}

		@Test
		void find_all_occurrences_of_duplicate(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i % 10);
			}
			List<Integer> indices = list.getAllIndices(5);
			softly.assertThat(indices).hasSize(10);
			softly.assertThat(indices).containsExactly(5, 15, 25, 35, 45, 55, 65, 75, 85, 95);
		}

		@Test
		void update_all_elements_via_setAt(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			for (int i = 0; i < 100; i++) {
				list.setAt(i, i * 10);
			}
			for (int i = 0; i < 100; i++) {
				softly.assertThat(list.getAt(i)).isEqualTo(i * 10);
			}
		}

		@Test
		void get_node_modify_value_directly_reflects_in_list(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			Node<Integer> node = list.getNode(50);
			node.value = 999;
			softly.assertThat(list.getAt(50)).isEqualTo(999);
		}

		@Test
		void addAfter_node_reference_inserts_correctly(SoftAssertions softly) {
			for (int i = 0; i < 10; i++) {
				list.addLast(i * 10);
			}
			Node<Integer> fifthNode = list.getNode(4);
			list.addAfter(fifthNode, 45);
			softly.assertThat(list.getAt(4)).isEqualTo(40);
			softly.assertThat(list.getAt(5)).isEqualTo(45);
			softly.assertThat(list.getAt(6)).isEqualTo(50);
		}

		@Test
		void addBefore_node_reference_inserts_correctly(SoftAssertions softly) {
			for (int i = 0; i < 10; i++) {
				list.addLast(i * 10);
			}
			Node<Integer> fifthNode = list.getNode(4);
			list.addBefore(fifthNode, 35);
			softly.assertThat(list.getAt(3)).isEqualTo(30);
			softly.assertThat(list.getAt(4)).isEqualTo(35);
			softly.assertThat(list.getAt(5)).isEqualTo(40);
		}

		@Test
		void head_and_tail_correct_after_many_addFirst(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addFirst(i);
			}
			softly.assertThat(list.head.value).isEqualTo(99);
			softly.assertThat(list.tail.value).isEqualTo(0);
			softly.assertThat(list.tail.next).isNull();
		}

		@Test
		void head_and_tail_correct_after_many_addLast(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			softly.assertThat(list.head.value).isEqualTo(0);
			softly.assertThat(list.tail.value).isEqualTo(99);
			softly.assertThat(list.tail.next).isNull();
		}

		@Test
		void head_and_tail_same_for_single_element_throughout_cycle(SoftAssertions softly) {
			list.addFirst(42);
			softly.assertThat(list.head).isSameAs(list.tail);
			list.deleteFirst();
			softly.assertThat(list.head).isNull();
			softly.assertThat(list.tail).isNull();
			list.addLast(99);
			softly.assertThat(list.head).isSameAs(list.tail);
		}

		@Test
		void tail_updates_correctly_after_deleteLast_sequence(SoftAssertions softly) {
			for (int i = 0; i < 5; i++) {
				list.addLast(i);
			}
			for (int i = 4; i >= 1; i--) {
				softly.assertThat(list.tail.value).isEqualTo(i);
				list.deleteLast();
			}
			softly.assertThat(list.tail.value).isEqualTo(0);
			softly.assertThat(list.head).isSameAs(list.tail);
		}

		@Test
		void size_never_negative_throughout_operations(SoftAssertions softly) {
			softly.assertThat(list.size()).isGreaterThanOrEqualTo(0);
			for (int i = 0; i < 50; i++) {
				list.addLast(i);
				softly.assertThat(list.size()).isGreaterThanOrEqualTo(0);
			}
			for (int i = 0; i < 50; i++) {
				list.deleteFirst();
				softly.assertThat(list.size()).isGreaterThanOrEqualTo(0);
			}
		}

		@Test
		void size_matches_actual_node_count_after_mixed_operations(SoftAssertions softly) {
			for (int i = 0; i < 100; i++) {
				list.addLast(i);
			}
			for (int i = 0; i < 30; i++) {
				list.deleteFirst();
			}
			for (int i = 0; i < 20; i++) {
				list.deleteLast();
			}
			for (int i = 0; i < 10; i++) {
				list.addFirst(i);
			}
			int actualCount = 0;
			Node<Integer> current = list.head;
			while (current != null) {
				actualCount++;
				current = current.next;
			}
			softly.assertThat(list.size()).isEqualTo(actualCount);
			softly.assertThat(list.size()).isEqualTo(60);
		}

		@Test
		void toArray_reflects_current_state_after_modifications(SoftAssertions softly) {
			for (int i = 0; i < 10; i++) {
				list.addLast(i);
			}
			Object[] before = list.toArray();
			softly.assertThat(before).hasSize(10);
			list.deleteFirst();
			list.deleteFirst();
			Object[] after = list.toArray();
			softly.assertThat(after).hasSize(8);
			softly.assertThat(after[0]).isEqualTo(2);
		}

		@Test
		void delete_all_then_rebuild_from_scratch(SoftAssertions softly) {
			for (int i = 0; i < 50; i++) {
				list.addLast(i);
			}
			while (!list.isEmpty()) {
				list.deleteFirst();
			}
			softly.assertThat(list.head).isNull();
			softly.assertThat(list.tail).isNull();
			softly.assertThat(list.size()).isZero();
			for (int i = 100; i < 150; i++) {
				list.addLast(i);
			}
			softly.assertThat(list.getFirst()).isEqualTo(100);
			softly.assertThat(list.getLast()).isEqualTo(149);
			softly.assertThat(list.size()).isEqualTo(50);
		}

		@Test
		void single_element_cycle_repeated(SoftAssertions softly) {
			for (int cycle = 0; cycle < 100; cycle++) {
				list.addLast(cycle);
				softly.assertThat(list.head).isSameAs(list.tail);
				softly.assertThat(list.size).isEqualTo(1);
				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(cycle);
				softly.assertThat(list.isEmpty()).isTrue();
			}
		}

		@Test
		void two_element_swap_simulation(SoftAssertions softly) {
			list.addLast(1);
			list.addLast(2);
			Integer first = list.getFirst();
			Integer last = list.getLast();
			list.setFirst(last);
			list.setLast(first);
			softly.assertThat(list.getFirst()).isEqualTo(2);
			softly.assertThat(list.getLast()).isEqualTo(1);
		}

		@Test
		void insert_at_every_position_in_existing_list(SoftAssertions softly) {
			for (int i = 0; i < 50; i++) {
				list.addLast(i * 2);
			}
			for (int i = 0; i < 50; i++) {
				list.addAt(i * 2 + 1, i * 2 + 1);
			}
			softly.assertThat(list.size).isEqualTo(100);
			for (int i = 0; i < 100; i++) {
				softly.assertThat(list.getAt(i)).isEqualTo(i);
			}
		}

		@Test
		void handles_max_and_min_integers(SoftAssertions softly) {
			list.addLast(Integer.MIN_VALUE);
			list.addLast(0);
			list.addLast(Integer.MAX_VALUE);
			softly.assertThat(list.getFirst()).isEqualTo(Integer.MIN_VALUE);
			softly.assertThat(list.getLast()).isEqualTo(Integer.MAX_VALUE);
			softly.assertThat(list.getAt(1)).isZero();
		}

		@Test
		void search_for_boundary_values(SoftAssertions softly) {
			list.addLast(Integer.MIN_VALUE);
			list.addLast(Integer.MAX_VALUE);
			list.addLast(Integer.MIN_VALUE);
			softly.assertThat(list.getFirstIndexOf(Integer.MIN_VALUE)).isZero();
			softly.assertThat(list.getLastIndexOf(Integer.MIN_VALUE)).isEqualTo(2);
			softly.assertThat(list.getAllIndices(Integer.MIN_VALUE)).containsExactly(0, 2);
		}
	}

	//region - Helper Methods
	private void buildList(Integer... values) {
		if (values.length == 0) return;
		list.head = new Node<>(values[0]);
		Node<Integer> current = list.head;
		for (int i = 1; i < values.length; i++) {
			current.next = new Node<>(values[i]);
			current = current.next;
		}
		list.tail = current;
		list.size = values.length;
	}

	private List<Integer> collectValues() {
		List<Integer> result = new ArrayList<>();
		Node<Integer> current = list.head;
		while (current != null) {
			result.add(current.value);
			current = current.next;
		}
		return result;
	}

	private void buildLargeList(int count) {
		Integer[] values = new Integer[count];
		for (int i = 0; i < count; i++) values[i] = i;
		buildList(values);
	}
	//endregion

}
