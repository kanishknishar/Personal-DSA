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

import data_structures.linked_list.DoublyLinkedList;

@SuppressWarnings({"ConstantConditions"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SoftAssertionsExtension.class)
class DoublyLinkedListTest {

	DoublyLinkedList<Integer> list;

	@BeforeEach
	void init() {
		list = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
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
			void handles_null_value() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addFirst(null);
				assertThat(s.getFirst()).isNull();
			}

			@Test
			void stress_1000_elements(SoftAssertions softly) {
				for (int i = 0; i < 1000; i++)
					list.addFirst(i);
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
			void handles_null_value() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast(null);
				assertThat(s.getLast()).isNull();
			}

			@Test
			void stress_1000_elements(SoftAssertions softly) {
				for (int i = 0; i < 1000; i++)
					list.addLast(i);
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
				for (int i = 0; i < 10; i++)
					list.addLast(i);
				list.addAt(8, 99);
				softly.assertThat(list.get(8)).isEqualTo(99);
				softly.assertThat(list.size).isEqualTo(11);
			}

			@Test
			void handles_null_value() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
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
			void first_half_uses_head_traversal() {
				for (int i = 0; i < 10; i++)
					list.addLast(i);
				assertThat(list.get(2)).isEqualTo(2);
			}

			@Test
			void second_half_uses_tail_traversal() {
				for (int i = 0; i < 10; i++)
					list.addLast(i);
				assertThat(list.get(8)).isEqualTo(8);
			}

			@Test
			void handles_boundary_values() {
				list.addFirst(Integer.MAX_VALUE);
				list.addFirst(Integer.MIN_VALUE);
				list.addLast(0);
				assertThat(list.get(1)).isEqualTo(Integer.MAX_VALUE);
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
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
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
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
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
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast(null);
				s.addLast("b");
				assertThat(s.indexOf(null)).isEqualTo(1);
			}
		}

		@Nested
		class LastIndexOf {

			@Test
			void returns_last_index_from_tail() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				assertThat(list.lastIndexOf(3)).isEqualTo(2);
			}

			@Test
			void returns_last_occurrence_for_duplicates() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(1);
				assertThat(list.lastIndexOf(1)).isEqualTo(2);
			}

			@Test
			void returns_minus_one_for_non_existing() {
				list.addLast(1);
				assertThat(list.lastIndexOf(99)).isEqualTo(-1);
			}

			@Test
			void finds_null_element_from_tail() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast(null);
				s.addLast("b");
				s.addLast(null);
				assertThat(s.lastIndexOf(null)).isEqualTo(3);
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
				for (int i = 0; i < 1000; i++)
					list.addLast(i);
				softly.assertThat(list.size).isEqualTo(1000);
				for (int i = 0; i < 500; i++)
					list.deleteFirst();
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
			void formats_empty_list() {
				assertThat(list.toString()).isEqualTo("[]");
			}

			@Test
			void formats_single_element() {
				list.addLast(42);
				assertThat(list.toString()).isEqualTo("[42]");
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
				list.addLast(1);
				list.addLast(null);
				list.addLast(3);
				assertThat(list.toString()).isEqualTo("[1, null, 3]");
			}

			@Test
			void large_list_has_valid_format(SoftAssertions softly) {
				for (int i = 0; i < 5; i++)
					list.addLast(i);
				String str = list.toString();
				softly.assertThat(str).startsWith("[");
				softly.assertThat(str).endsWith("]");
				softly.assertThat(str).contains(", ");
			}
		}

		@Nested
		class Contains {

			@Test
			void returns_true_for_existing_element() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				assertThat(list.contains(2)).isTrue();
			}

			@Test
			void returns_false_for_non_existing() {
				list.addLast(1);
				assertThat(list.contains(99)).isFalse();
			}

			@Test
			void returns_false_on_empty_list() {
				assertThat(list.contains(42)).isFalse();
			}

			@Test
			void finds_null_element() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast(null);
				assertThat(s.contains(null)).isTrue();
			}

			@Test
			void finds_first_element() {
				list.addLast(1);
				list.addLast(2);
				assertThat(list.contains(1)).isTrue();
			}

			@Test
			void finds_last_element() {
				list.addLast(1);
				list.addLast(2);
				assertThat(list.contains(2)).isTrue();
			}
		}

		@Nested
		class Clear {

			@Test
			void empties_non_empty_list(SoftAssertions softly) {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.clear();
				softly.assertThat(list.isEmpty()).isTrue();
				softly.assertThat(list.size).isZero();
			}

			@Test
			void does_nothing_on_empty_list() {
				list.clear();
				assertThat(list.isEmpty()).isTrue();
			}

			@Test
			void allows_adding_after_clear(SoftAssertions softly) {
				list.addLast(1);
				list.clear();
				list.addLast(2);
				softly.assertThat(list.size).isEqualTo(1);
				softly.assertThat(list.getFirst()).isEqualTo(2);
			}

			@Test
			void throws_on_getFirst_after_clear() {
				list.addLast(1);
				list.clear();
				assertThatThrownBy(() -> list.getFirst())
					.isInstanceOf(IndexOutOfBoundsException.class);
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
			void returns_array_with_elements() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				Object[] arr = list.toArray();
				assertThat(arr).containsExactly(1, 2, 3);
			}

			@Test
			void array_length_equals_size() {
				list.addLast(1);
				list.addLast(2);
				assertThat(list.toArray()).hasSize(list.size);
			}

			@Test
			void includes_null_elements() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast(null);
				s.addLast("b");
				assertThat(s.toArray()).containsExactly("a", null, "b");
			}

			@Test
			void maintains_order() {
				for (int i = 0; i < 10; i++)
					list.addLast(i);
				Object[] arr = list.toArray();
				for (int i = 0; i < 10; i++)
					assertThat(arr[i]).isEqualTo(i);
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
				list.setAt(0, 99);
				assertThat(list.get(0)).isEqualTo(99);
			}

			@Test
			void sets_element_at_middle() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.setAt(1, 88);
				assertThat(list.get(1)).isEqualTo(88);
			}

			@Test
			void sets_element_at_tail() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.setAt(2, 77);
				assertThat(list.get(2)).isEqualTo(77);
			}

			@Test
			void near_end_uses_tail_traversal() {
				for (int i = 0; i < 10; i++)
					list.addLast(i);
				list.setAt(8, 99);
				assertThat(list.get(8)).isEqualTo(99);
			}

			@Test
			void does_not_change_size() {
				list.addLast(1);
				list.addLast(2);
				int sz = list.size;
				list.setAt(0, 99);
				assertThat(list.size).isEqualTo(sz);
			}

			@Test
			void can_set_to_null() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.setAt(0, null);
				assertThat(s.get(0)).isNull();
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
				assertThatThrownBy(() -> list.setAt(0, 99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}
		}

		@Nested
		class SetFirst {

			@Test
			void sets_first_element() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.setFirst(99);
				assertThat(list.getFirst()).isEqualTo(99);
			}

			@Test
			void can_set_to_null() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast("b");
				s.setFirst(null);
				assertThat(s.getFirst()).isNull();
			}

			@Test
			void does_not_change_size() {
				list.addLast(1);
				list.addLast(2);
				int sz = list.size;
				list.setFirst(99);
				assertThat(list.size).isEqualTo(sz);
			}

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.setFirst(99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void does_not_affect_other_elements() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.setFirst(99);
				assertThat(list.get(1)).isEqualTo(2);
			}
		}

		@Nested
		class SetLast {

			@Test
			void sets_last_element() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.setLast(99);
				assertThat(list.getLast()).isEqualTo(99);
			}

			@Test
			void can_set_to_null() {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast("b");
				s.setLast(null);
				assertThat(s.getLast()).isNull();
			}

			@Test
			void does_not_change_size() {
				list.addLast(1);
				list.addLast(2);
				int sz = list.size;
				list.setLast(99);
				assertThat(list.size).isEqualTo(sz);
			}

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.setLast(99))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void does_not_affect_other_elements() {
				list.addLast(1);
				list.addLast(2);
				list.addLast(3);
				list.setLast(99);
				assertThat(list.get(1)).isEqualTo(2);
			}

			@Test
			void single_element_affects_both_first_and_last() {
				list.addLast(1);
				list.setLast(99);
				assertThat(list.getFirst()).isEqualTo(99);
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
				Integer removed = list.deleteFirst();
				softly.assertThat(removed).isEqualTo(1);
				softly.assertThat(list.getFirst()).isEqualTo(2);
				softly.assertThat(list.size).isEqualTo(1);
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
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
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
				Integer removed = list.deleteLast();
				softly.assertThat(removed).isEqualTo(2);
				softly.assertThat(list.getLast()).isEqualTo(1);
				softly.assertThat(list.size).isEqualTo(1);
			}

			@Test
			void throws_on_empty_list() {
				assertThatThrownBy(() -> list.deleteLast())
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void single_element_becomes_empty(SoftAssertions softly) {
				list.addFirst(42);
				Integer removed = list.deleteLast();
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
				Integer removed = list.deleteAt(1);
				softly.assertThat(removed).isEqualTo(2);
				softly.assertThat(list.get(1)).isEqualTo(3);
				softly.assertThat(list.size).isEqualTo(2);
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
				assertThat(list.getLast()).isEqualTo(1);
			}

			@Test
			void near_end_uses_tail_traversal(SoftAssertions softly) {
				for (int i = 0; i < 10; i++)
					list.addLast(i);
				Integer removed = list.deleteAt(8);
				softly.assertThat(removed).isEqualTo(8);
				softly.assertThat(list.size).isEqualTo(9);
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

		@Nested
		class ByValue {

			@Test
			void removes_first_occurrence(SoftAssertions softly) {
				list.addLast(1);
				list.addLast(2);
				list.addLast(1);
				boolean removed = list.deleteValue(1);
				softly.assertThat(removed).isTrue();
				softly.assertThat(list.size).isEqualTo(2);
				softly.assertThat(list.get(0)).isEqualTo(2);
				softly.assertThat(list.get(1)).isEqualTo(1);
			}

			@Test
			void returns_false_when_not_found() {
				list.addLast(1);
				boolean removed = list.deleteValue(99);
				assertThat(removed).isFalse();
			}

			@Test
			void returns_false_on_empty_list() {
				boolean removed = list.deleteValue(99);
				assertThat(removed).isFalse();
			}

			@Test
			void only_removes_one_occurrence_when_multiple_exist(SoftAssertions softly) {
				list.addLast(5);
				list.addLast(5);
				list.addLast(5);
				boolean removed = list.deleteValue(5);
				softly.assertThat(removed).isTrue();
				softly.assertThat(list.size).isEqualTo(2);
				softly.assertThat(list.get(0)).isEqualTo(5);
				softly.assertThat(list.get(1)).isEqualTo(5);
			}

			@Test
			void removes_null_value(SoftAssertions softly) {
				DoublyLinkedList<String> s = new DoublyLinkedList<>("H", "T");
				s.addLast("a");
				s.addLast(null);
				s.addLast("b");
				boolean removed = s.deleteValue(null);
				softly.assertThat(removed).isTrue();
				softly.assertThat(s.size).isEqualTo(2);
				softly.assertThat(s.get(0)).isEqualTo("a");
				softly.assertThat(s.get(1)).isEqualTo("b");
			}

			@Test
			void does_not_change_size_when_value_not_found() {
				list.addLast(1);
				list.addLast(2);
				int sz = list.size;
				list.deleteValue(99);
				assertThat(list.size).isEqualTo(sz);
			}
		}
	}

	//endregion

	//region - Stress

	@Nested
	class Stress {

		@Test
		void large_sequential_add_and_delete(SoftAssertions softly) {
			for (int i = 0; i < 1000; i++)
				list.addLast(i);
			softly.assertThat(list.size).isEqualTo(1000);

			for (int i = 0; i < 500; i++)
				list.deleteFirst();
			softly.assertThat(list.getFirst()).isEqualTo(500);
			softly.assertThat(list.size).isEqualTo(500);

			for (int i = 0; i < 500; i++)
				list.deleteLast();
			softly.assertThat(list.isEmpty()).isTrue();
		}

		@Test
		void interleaved_head_and_tail_operations(SoftAssertions softly) {
			for (int i = 0; i < 10; i++) {
				list.addFirst(i);
				list.addLast(i + 100);
			}
			softly.assertThat(list.size).isEqualTo(20);

			for (int i = 0; i < 5; i++) {
				list.deleteFirst();
				list.deleteLast();
			}
			softly.assertThat(list.size).isEqualTo(10);
		}

		@Test
		void midpoint_optimization_for_operations(SoftAssertions softly) {
			for (int i = 0; i < 100; i++)
				list.addLast(i);
			softly.assertThat(list.get(10)).isEqualTo(10);
			softly.assertThat(list.get(90)).isEqualTo(90);

			list.setAt(90, 999);
			softly.assertThat(list.get(90)).isEqualTo(999);

			list.deleteAt(90);
			softly.assertThat(list.size).isEqualTo(99);
		}

		@Test
		void boundary_values_integer_max_and_min(SoftAssertions softly) {
			list.addLast(Integer.MAX_VALUE);
			list.addFirst(Integer.MIN_VALUE);
			list.addAt(1, 0);
			softly.assertThat(list.get(0)).isEqualTo(Integer.MIN_VALUE);
			softly.assertThat(list.get(1)).isEqualTo(0);
			softly.assertThat(list.get(2)).isEqualTo(Integer.MAX_VALUE);
		}

		@Test
		void boundaries_after_multiple_removals(SoftAssertions softly) {
			for (int i = 0; i < 10; i++)
				list.addLast(i);
			list.deleteFirst();
			list.deleteFirst();
			list.deleteLast();
			list.deleteLast();
			softly.assertThat(list.getFirst()).isEqualTo(2);
			softly.assertThat(list.getLast()).isEqualTo(7);
			softly.assertThat(list.get(0)).isEqualTo(2);
			softly.assertThat(list.get(list.size - 1)).isEqualTo(7);
		}

		@Test
		void size_never_goes_negative(SoftAssertions softly) {
			softly.assertThat(list.size).isGreaterThanOrEqualTo(0);
			list.addLast(1);
			softly.assertThat(list.size).isGreaterThanOrEqualTo(0);
			list.deleteFirst();
			softly.assertThat(list.size).isGreaterThanOrEqualTo(0);
		}

		@Test
		void consecutive_add_delete_cycles(SoftAssertions softly) {
			for (int cycle = 0; cycle < 5; cycle++) {
				list.addLast(cycle);
				list.addFirst(cycle * 10);
				softly.assertThat(list.size).isEqualTo(2);
				list.deleteFirst();
				list.deleteLast();
				softly.assertThat(list.isEmpty()).isTrue();
			}
		}
	}

	//endregion

	//region - Bidirectional Navigation

	@Nested
	class BidirectionalNavigation {

		@Test
		void forward_traversal_matches_backward_traversal() {
			for (int i = 0; i < 10; i++)
				list.addLast(i);

			for (int i = 0; i < list.size; i++) {
				int forwardValue = list.get(i);
				int backwardValue = list.get(list.size - 1 - i);
				assertThat(forwardValue).isEqualTo(i);
				assertThat(backwardValue).isEqualTo(list.size - 1 - i);
			}
		}

		@Test
		void bidirectional_pointer_integrity_after_add(SoftAssertions softly) {
			list.addLast(1);
			list.addLast(2);
			list.addLast(3);

			softly.assertThat(list.getFirst()).isEqualTo(1);
			softly.assertThat(list.getLast()).isEqualTo(3);
		}

		@Test
		void bidirectional_pointer_integrity_after_delete(SoftAssertions softly) {
			for (int i = 0; i < 5; i++)
				list.addLast(i);

			list.deleteAt(2);

			softly.assertThat(list.get(2)).isEqualTo(3);
			softly.assertThat(list.size).isEqualTo(4);
		}

		@Test
		void accessing_from_both_ends() {
			for (int i = 0; i < 100; i++)
				list.addLast(i);

			assertThat(list.get(10)).isEqualTo(10);
			assertThat(list.get(90)).isEqualTo(90);
		}
	}

	//endregion
}
