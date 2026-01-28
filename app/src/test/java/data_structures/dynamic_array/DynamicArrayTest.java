package data_structures.dynamic_array;

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
class DynamicArrayTest {

	DynamicArray<Integer> array;

	@BeforeEach
	void setUp() {
		array = new DynamicArray<>();
	}

	//region Constructor
	@Nested
	class Constructor {

		@Nested
		class Default_Constructor {

			@Test
			void creates_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					DynamicArray<Integer> arr = new DynamicArray<>();
					softly.assertThat(arr.size()).isZero();
					softly.assertThat(arr.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void has_default_capacity(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					DynamicArray<Integer> arr = new DynamicArray<>();
					softly.assertThat(arr.capacity()).isEqualTo(10);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Capacity_Constructor {

			@Test
			void creates_array_with_specified_capacity(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					DynamicArray<Integer> arr = new DynamicArray<>(20);
					softly.assertThat(arr.capacity()).isEqualTo(20);
					softly.assertThat(arr.size()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_on_negative_capacity() {
				assertThatThrownBy(() -> new DynamicArray<Integer>(-1))
					.isInstanceOf(IllegalArgumentException.class);
			}

			@Test
			void throws_on_zero_capacity() {
				assertThatThrownBy(() -> new DynamicArray<Integer>(0))
					.isInstanceOf(IllegalArgumentException.class);
			}
		}

		@Nested
		class Array_Constructor {

			@Test
			void creates_from_existing_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					Integer[] source = {1, 2, 3, 4, 5};
					DynamicArray<Integer> arr = new DynamicArray<>(source);
					softly.assertThat(arr.size()).isEqualTo(5);
					softly.assertThat(arr.get(0)).isEqualTo(1);
					softly.assertThat(arr.get(4)).isEqualTo(5);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_on_null_array() {
				assertThatThrownBy(() -> new DynamicArray<Integer>((Integer[]) null))
					.isInstanceOf(NullPointerException.class);
			}

			@Test
			void creates_from_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					Integer[] source = {};
					DynamicArray<Integer> arr = new DynamicArray<>(source);
					softly.assertThat(arr.size()).isZero();
					softly.assertThat(arr.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Add
	@Nested
	class Add {

		@Nested
		class Size_0_Empty {

			@Test
			void add_to_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					softly.assertThat(array.size()).isEqualTo(1);
					softly.assertThat(array.get(0)).isEqualTo(42);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void addFirst_to_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.addFirst(42);
					softly.assertThat(array.size()).isEqualTo(1);
					softly.assertThat(array.getFirst()).isEqualTo(42);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void addAt_index_0_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.addAt(0, 42);
					softly.assertThat(array.size()).isEqualTo(1);
					softly.assertThat(array.get(0)).isEqualTo(42);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void add_to_single_element_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					softly.assertThat(array.size()).isEqualTo(2);
					softly.assertThat(array.getLast()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void addFirst_to_single_element_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.addFirst(0);
					softly.assertThat(array.size()).isEqualTo(2);
					softly.assertThat(array.getFirst()).isEqualTo(0);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void addAt_middle_index(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.addAt(0, 0);
					softly.assertThat(array.get(0)).isEqualTo(0);
					softly.assertThat(array.get(1)).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void add_maintains_order(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(3);
					softly.assertThat(array.get(0)).isEqualTo(1);
					softly.assertThat(array.get(1)).isEqualTo(2);
					softly.assertThat(array.get(2)).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void addAt_shifts_elements_right(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(3);
					array.addAt(1, 2);
					softly.assertThat(array.get(0)).isEqualTo(1);
					softly.assertThat(array.get(1)).isEqualTo(2);
					softly.assertThat(array.get(2)).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void add_stress_1000_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 1000; i++) array.add(i);
					softly.assertThat(array.size()).isEqualTo(1000);
					softly.assertThat(array.getFirst()).isEqualTo(0);
					softly.assertThat(array.getLast()).isEqualTo(999);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Resize_Behavior {

			@Test
			void triggers_resize_when_full(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					DynamicArray<Integer> arr = new DynamicArray<>(2);
					arr.add(1);
					arr.add(2);
					arr.add(3);
					softly.assertThat(arr.size()).isEqualTo(3);
					softly.assertThat(arr.capacity()).isGreaterThanOrEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void doubles_capacity_on_resize(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					DynamicArray<Integer> arr = new DynamicArray<>(4);
					for (int i = 0; i < 5; i++) arr.add(i);
					softly.assertThat(arr.capacity()).isEqualTo(8);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Null_And_Duplicates {

			@Test
			void adds_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(null);
					softly.assertThat(array.size()).isEqualTo(1);
					softly.assertThat(array.get(0)).isNull();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void adds_duplicate_values(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					array.add(42);
					array.add(42);
					softly.assertThat(array.size()).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Invalid_Index {

			@Test
			void throws_on_negative_index() {
				assertThatThrownBy(() -> array.addAt(-1, 42))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_index_greater_than_size() {
				assertThatThrownBy(() -> array.addAt(1, 42))
					.isInstanceOf(IndexOutOfBoundsException.class);
			}
		}
	}
	//endregion

	//region Remove
	@Nested
	class Remove {

		@Nested
		class Size_0_Empty {

			@Test
			void removeFirst_throws_on_empty() {
				assertThatThrownBy(() -> array.removeFirst())
					.isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void removeLast_throws_on_empty() {
				assertThatThrownBy(() -> array.removeLast())
					.isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void removeAt_throws_on_empty() {
				assertThatThrownBy(() -> array.removeAt(0))
					.isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void removeFirst_removes_only_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					Integer removed = array.removeFirst();
					softly.assertThat(removed).isEqualTo(42);
					softly.assertThat(array.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void removeLast_removes_only_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					Integer removed = array.removeLast();
					softly.assertThat(removed).isEqualTo(42);
					softly.assertThat(array.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void removeFirst_leaves_remaining_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					Integer removed = array.removeFirst();
					softly.assertThat(removed).isEqualTo(1);
					softly.assertThat(array.getFirst()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void removeAt_middle_shifts_left(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(3);
					Integer removed = array.removeAt(1);
					softly.assertThat(removed).isEqualTo(2);
					softly.assertThat(array.get(1)).isEqualTo(3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void consecutive_removes_until_empty(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 100; i++) array.add(i);
					for (int i = 0; i < 100; i++) array.removeFirst();
					softly.assertThat(array.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Remove_By_Value {

			@Test
			void removeValue_returns_true_when_found(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(3);
					boolean removed = array.removeValue(2);
					softly.assertThat(removed).isTrue();
					softly.assertThat(array.size()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void removeValue_returns_false_when_not_found(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					boolean removed = array.removeValue(99);
					softly.assertThat(removed).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void removeValue_removes_first_occurrence_only(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(1);
					array.removeValue(1);
					softly.assertThat(array.size()).isEqualTo(2);
					softly.assertThat(array.get(1)).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Clear {

			@Test
			void clear_removes_all_elements(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 50; i++) array.add(i);
					array.clear();
					softly.assertThat(array.isEmpty()).isTrue();
					softly.assertThat(array.size()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void clear_on_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.clear();
					softly.assertThat(array.isEmpty()).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Invalid_Index {

			@Test
			void removeAt_throws_on_negative_index() {
				assertThatThrownBy(() -> {
					array.add(1);
					array.removeAt(-1);
				}).isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void removeAt_throws_on_index_equal_to_size() {
				assertThatThrownBy(() -> {
					array.add(1);
					array.removeAt(1);
				}).isInstanceOf(IndexOutOfBoundsException.class);
			}
		}
	}
	//endregion

	//region Get
	@Nested
	class Get {

		@Nested
		class Size_0_Empty {

			@Test
			void get_throws_on_empty() {
				assertThatThrownBy(() -> array.get(0))
					.isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void getFirst_throws_on_empty() {
				assertThatThrownBy(() -> array.getFirst())
					.isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void getLast_throws_on_empty() {
				assertThatThrownBy(() -> array.getLast())
					.isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_1_Single {

			@Test
			void get_returns_only_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					softly.assertThat(array.get(0)).isEqualTo(42);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void getFirst_same_as_getLast(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					softly.assertThat(array.getFirst()).isEqualTo(array.getLast());
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Size_N_Multiple {

			@Test
			void get_at_various_indices(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 100; i++) array.add(i);
					softly.assertThat(array.get(0)).isEqualTo(0);
					softly.assertThat(array.get(50)).isEqualTo(50);
					softly.assertThat(array.get(99)).isEqualTo(99);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Invalid_Index {

			@Test
			void throws_on_negative_index() {
				assertThatThrownBy(() -> {
					array.add(1);
					array.get(-1);
				}).isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_index_equal_to_size() {
				assertThatThrownBy(() -> {
					array.add(1);
					array.get(1);
				}).isInstanceOf(IndexOutOfBoundsException.class);
			}
		}
	}
	//endregion

	//region Index_Search
	@Nested
	class Index_Search {

		@Nested
		class IndexOf {

			@Test
			void returns_first_occurrence(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(1);
					softly.assertThat(array.indexOf(1)).isEqualTo(0);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void returns_negative_one_when_not_found(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					softly.assertThat(array.indexOf(99)).isEqualTo(-1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void returns_negative_one_on_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					softly.assertThat(array.indexOf(1)).isEqualTo(-1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void finds_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(null);
					array.add(2);
					softly.assertThat(array.indexOf(null)).isEqualTo(1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class LastIndexOf {

			@Test
			void returns_last_occurrence(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(1);
					softly.assertThat(array.lastIndexOf(1)).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void returns_negative_one_when_not_found(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					softly.assertThat(array.lastIndexOf(99)).isEqualTo(-1);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void same_as_indexOf_for_unique_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(3);
					softly.assertThat(array.indexOf(2)).isEqualTo(array.lastIndexOf(2));
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void finds_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(null);
					array.add(1);
					array.add(null);
					softly.assertThat(array.lastIndexOf(null)).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Set
	@Nested
	class Set {

		@Nested
		class SetAt {

			@Test
			void set_at_valid_index(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.set(1, 99);
					softly.assertThat(array.get(1)).isEqualTo(99);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void set_does_not_change_size(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					int sizeBefore = array.size();
					array.set(1, 99);
					softly.assertThat(array.size()).isEqualTo(sizeBefore);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void set_with_null_value(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.set(0, null);
					softly.assertThat(array.get(0)).isNull();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class SetFirst {

			@Test
			void replaces_first_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.setFirst(99);
					softly.assertThat(array.getFirst()).isEqualTo(99);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void on_single_element_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.setFirst(99);
					softly.assertThat(array.getFirst()).isEqualTo(99);
					softly.assertThat(array.getLast()).isEqualTo(99);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class SetLast {

			@Test
			void replaces_last_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.setLast(99);
					softly.assertThat(array.getLast()).isEqualTo(99);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void on_single_element_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.setLast(99);
					softly.assertThat(array.getFirst()).isEqualTo(99);
					softly.assertThat(array.getLast()).isEqualTo(99);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Invalid_Index {

			@Test
			void throws_on_empty_array() {
				assertThatThrownBy(() -> array.set(0, 42))
					.isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void throws_on_index_out_of_bounds() {
				assertThatThrownBy(() -> {
					array.add(1);
					array.set(5, 42);
				}).isInstanceOf(IndexOutOfBoundsException.class);
			}

			@Test
			void throws_on_negative_index() {
				assertThatThrownBy(() -> {
					array.add(1);
					array.set(-1, 42);
				}).isInstanceOf(IndexOutOfBoundsException.class);
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
					softly.assertThat(array.size()).isZero();
					array.add(1);
					softly.assertThat(array.size()).isEqualTo(1);
					array.add(2);
					softly.assertThat(array.size()).isEqualTo(2);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void isEmpty_returns_true_when_empty(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					softly.assertThat(array.isEmpty()).isTrue();
					array.add(1);
					softly.assertThat(array.isEmpty()).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void capacity_tracks_internal_array_size(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					DynamicArray<Integer> arr = new DynamicArray<>(5);
					softly.assertThat(arr.capacity()).isEqualTo(5);
					for (int i = 0; i < 6; i++) arr.add(i);
					softly.assertThat(arr.capacity()).isGreaterThan(5);
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Contains {

			@Test
			void contains_returns_true_when_present(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					softly.assertThat(array.contains(42)).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void contains_returns_false_when_absent(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(42);
					softly.assertThat(array.contains(99)).isFalse();
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void contains_null_element(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(null);
					softly.assertThat(array.contains(null)).isTrue();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class ToArray {

			@Test
			void toArray_returns_copy(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.add(1);
					array.add(2);
					array.add(3);
					Object[] result = array.toArray();
					softly.assertThat(result).containsExactly(1, 2, 3);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void toArray_empty_returns_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					Object[] result = array.toArray();
					softly.assertThat(result).isEmpty();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}

		@Nested
		class Capacity_Management {

			@Test
			void ensureCapacity_increases_capacity(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.ensureCapacity(100);
					softly.assertThat(array.capacity()).isGreaterThanOrEqualTo(100);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void trimToSize_reduces_capacity_to_size(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					for (int i = 0; i < 5; i++) array.add(i);
					array.trimToSize();
					softly.assertThat(array.capacity()).isEqualTo(5);
				}).isInstanceOf(UnsupportedOperationException.class);
			}

			@Test
			void trimToSize_on_empty_array(SoftAssertions softly) {
				assertThatThrownBy(() -> {
					array.trimToSize();
					softly.assertThat(array.capacity()).isZero();
				}).isInstanceOf(UnsupportedOperationException.class);
			}
		}
	}
	//endregion

	//region Integration
	@Nested
	class Integration {

		@Test
		void add_remove_interleaved(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 50; i++) {
					array.add(i);
					array.add(i + 100);
					array.removeFirst();
				}
				softly.assertThat(array.size()).isEqualTo(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void stress_mixed_operations(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 100; i++) array.add(i);
				for (int i = 0; i < 25; i++) array.removeFirst();
				for (int i = 0; i < 25; i++) array.removeLast();
				for (int i = 0; i < 10; i++) array.set(i, array.get(i) * 2);
				softly.assertThat(array.size()).isEqualTo(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void rebuild_after_clear(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 100; i++) array.add(i);
				array.clear();
				for (int i = 0; i < 50; i++) array.add(i + 1000);
				softly.assertThat(array.getFirst()).isEqualTo(1000);
				softly.assertThat(array.getLast()).isEqualTo(1049);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void random_access_after_modifications(SoftAssertions softly) {
			assertThatThrownBy(() -> {
				for (int i = 0; i < 20; i++) array.add(i);
				array.removeAt(10);
				array.addAt(5, 999);
				softly.assertThat(array.indexOf(999)).isEqualTo(5);
				softly.assertThat(array.size()).isEqualTo(20);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}
	//endregion
}
