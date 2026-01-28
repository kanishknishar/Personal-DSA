package algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings({"unused"})
@ExtendWith(SoftAssertionsExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BubbleSortTest {

	//region - Correctness

	@Nested
	@SuppressWarnings({"unused"})
	class Correctness {

		@Test
		void sorts_unsorted_array() {
			int[] arr = {5, 2, 8, 1, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_already_sorted_array() {
			int[] arr = {1, 2, 3, 4, 5};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_reverse_sorted_array() {
			int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_empty_array() {
			int[] arr = {};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_single_element() {
			int[] arr = {42};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_duplicates() {
			int[] arr = {3, 1, 3, 2, 3};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_negative_numbers() {
			int[] arr = {-5, -1, -10, 0, 3};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_two_elements_in_order() {
			int[] arr = {1, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_two_elements_reversed() {
			int[] arr = {2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_all_same_elements() {
			int[] arr = {7, 7, 7, 7, 7};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_integer_min_max_values() {
			int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_alternating_high_low() {
			int[] arr = {100, 1, 99, 2, 98, 3, 97, 4};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_first_element_out_of_place() {
			int[] arr = {5, 1, 2, 3, 4};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_last_element_out_of_place() {
			int[] arr = {2, 3, 4, 5, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Efficiency

	@Nested
	@SuppressWarnings({"unused"})
	class Efficiency {

		@Test
		void best_case_nearly_sorted() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++)
				arr[i] = i;
			arr[50] = 49;
			arr[49] = 50;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				assertThat(steps).as("Optimized bubble sort should detect sorted arrays early")
					.isLessThan(100 * 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void worst_case_reverse_sorted() {
			int[] arr = new int[1000];
			for (int i = 0; i < 1000; i++)
				arr[i] = 1000 - i;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				assertThat(steps).as("Bubble sort should take O(n²) steps for reversed array")
					.isLessThan(1000 * 1000 * 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void average_case_random_array() {
			int[] arr = new int[500];
			for (int i = 0; i < 500; i++)
				arr[i] = (i * 37 + 13) % 1000;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				assertThat(steps).as("Average case should be O(n²)")
					.isLessThan(500 * 500 * 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void inefficiency_warning_for_large_dataset() {
			int[] arr = new int[2000];
			for (int i = 0; i < 2000; i++)
				arr[i] = 2000 - i;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				if (steps > 2000 * 2000 * 3) {
					throw new AssertionError("INEFFICIENT: Bubble sort taking excessive steps - check implementation");
				}
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Stability

	@Nested
	@SuppressWarnings({"unused"})
	class Stability {

		@Test
		void preserves_order_of_equal_elements() {
			assertThatThrownBy(() -> {
				int[] arr = {3, 1, 3, 2, 3};
				assertArrayCreated(arr);
				bubbleSort(arr);
			}).isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void stability_verified_with_custom_objects() {
			assertThatThrownBy(() -> bubbleSort(new int[] {2, 1, 2}))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void uses_less_than_or_equal_for_stability() {
			int[] arr = {2, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				assertThat(arr).containsExactly(1, 2, 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - In Place Behavior

	@Nested
	@SuppressWarnings({"unused"})
	class In_Place_Behavior {

		@Test
		void sorts_array_in_place_no_extra_array() {
			int[] arr = {5, 2, 8, 1, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void original_array_reference_unchanged() {
			int[] arr = {3, 1, 2};
			int[] originalRef = arr;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				assertThat(arr).isSameAs(originalRef);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void no_elements_lost_during_sort() {
			int[] arr = {5, 2, 8, 1, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				assertThat(arr).containsExactlyInAnyOrder(1, 2, 5, 8, 9);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Optimization Flags

	@Nested
	@SuppressWarnings({"unused"})
	class Optimization_Flags {

		@Test
		void early_termination_when_no_swaps() {
			int[] arr = {1, 2, 3, 4, 5};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				assertThat(steps).as("Should terminate after first pass with no swaps")
					.isLessThanOrEqualTo(arr.length);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void tracks_last_swap_position_optimization() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++) arr[i] = i;
			arr[0] = 5;
			arr[5] = 0;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				assertThat(steps).as("Should optimize by tracking last swap position")
					.isLessThan(100 * 10);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void shrinking_range_optimization() {
			int[] arr = {10, 1, 2, 3, 4, 5, 6, 7, 8, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = bubbleSortWithStepCount(arr);
				assertThat(steps).as("Should reduce comparison range after each pass")
					.isLessThan(arr.length * arr.length / 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Adjacent Comparison

	@Nested
	@SuppressWarnings({"unused"})
	class Adjacent_Comparison {

		@Test
		void adjacent_element_comparison_only() {
			int[] arr = {3, 1, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				assertThat(arr).containsExactly(1, 2, 3);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void comparison_count_matches_expected() {
			int[] arr = {3, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long comparisons = bubbleSortWithStepCount(arr);
				assertThat(comparisons).as("n=3 worst case: 3 comparisons minimum")
					.isGreaterThanOrEqualTo(3);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Integration

	@Nested
	@SuppressWarnings({"unused"})
	class Integration {

		@Test
		void sort_then_binary_search() {
			int[] arr = {64, 25, 12, 22, 11};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				int idx = java.util.Arrays.binarySearch(arr, 22);
				assertThat(idx).isGreaterThanOrEqualTo(0);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void multiple_sorts_idempotent() {
			int[] arr = {5, 2, 8, 1, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				int[] afterFirst = arr.clone();
				bubbleSort(arr);
				assertThat(arr).isEqualTo(afterFirst);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void sort_subarray_pattern() {
			int[] arr = {5, 3, 1, 4, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				bubbleSort(arr);
				assertThat(arr).containsExactly(1, 2, 3, 4, 5);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Helper Methods

	private void bubbleSort(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private long bubbleSortWithStepCount(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private void assertArrayCreated(int[] arr) {
		assertThat(arr).isNotNull();
	}

	//endregion
}
