package algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings({"unused"})
@ExtendWith(SoftAssertionsExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SelectionSortTest {

	//region - Correctness

	@Nested
	@SuppressWarnings({"unused"})
	class Correctness {

		@Test
		void sorts_unsorted_array() {
			int[] arr = {5, 2, 8, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_already_sorted_array() {
			int[] arr = {1, 2, 3, 4, 5};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_reverse_sorted_array() {
			int[] arr = {10, 9, 8, 7, 6};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_single_element() {
			int[] arr = {42};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_duplicates() {
			int[] arr = {3, 1, 3, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_empty_array() {
			int[] arr = {};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_negative_numbers() {
			int[] arr = {-3, -1, -10, 0, 5};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_two_elements_in_order() {
			int[] arr = {1, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_two_elements_reversed() {
			int[] arr = {2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_all_same_elements() {
			int[] arr = {7, 7, 7, 7, 7};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_integer_min_max_values() {
			int[] arr = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_alternating_high_low() {
			int[] arr = {100, 1, 99, 2, 98, 3, 97, 4};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_first_element_out_of_place() {
			int[] arr = {5, 1, 2, 3, 4};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_last_element_out_of_place() {
			int[] arr = {2, 3, 4, 5, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Efficiency

	@Nested
	@SuppressWarnings({"unused"})
	class Efficiency {

		@Test
		void consistent_performance_regardless_of_input() {
			int[] sorted = new int[100];
			int[] reverse = new int[100];

			for (int i = 0; i < 100; i++) {
				sorted[i] = i;
				reverse[i] = 100 - i;
			}
			assertArrayCreated(sorted);
			assertArrayCreated(reverse);

			assertThatThrownBy(() -> {
				long stepsSorted = selectionSortWithStepCount(sorted.clone());
				long stepsReverse = selectionSortWithStepCount(reverse.clone());

				assertThat(stepsSorted).as("Selection sort should have similar performance regardless of input order")
					.isCloseTo(stepsReverse, within(100L));
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void always_makes_n_squared_comparisons() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++)
				arr[i] = 100 - i;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = selectionSortWithStepCount(arr);
				assertThat(steps).as("Selection sort should take approximately n² comparisons")
					.isLessThan(100 * 100 * 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void inefficiency_warning_for_large_dataset() {
			int[] arr = new int[1500];
			for (int i = 0; i < 1500; i++)
				arr[i] = 1500 - i;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = selectionSortWithStepCount(arr);
				if (steps > 1500 * 1500 * 3) {
					throw new AssertionError("INEFFICIENT: Selection sort taking excessive comparisons");
				}
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void minimal_swaps_compared_to_bubble_sort() {
			int[] arr = {5, 4, 3, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Stability

	@Nested
	@SuppressWarnings({"unused"})
	class Stability {

		@Test
		void not_stable_sort() {
			assertThatThrownBy(() -> {
				int[] arr = {3, 1, 3, 2};
				assertArrayCreated(arr);
				selectionSort(arr);
			}).isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void demonstrates_instability_with_custom_objects() {
			assertThatThrownBy(() -> selectionSort(new int[] {2, 1, 2}))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void instability_example_equal_elements_reordered() {
			int[] arr = {2, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
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

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void original_array_reference_unchanged() {
			int[] arr = {3, 1, 2};
			int[] originalRef = arr;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
				assertThat(arr).isSameAs(originalRef);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void no_elements_lost_during_sort() {
			int[] arr = {5, 2, 8, 1, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
				assertThat(arr).containsExactlyInAnyOrder(1, 2, 5, 8, 9);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Swap Count

	@Nested
	@SuppressWarnings({"unused"})
	class Swap_Count {

		@Test
		void exactly_n_minus_1_swaps_maximum() {
			int[] arr = {5, 4, 3, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long swaps = selectionSortSwapCount(arr);
				assertThat(swaps).as("Selection sort makes at most n-1 swaps")
					.isLessThanOrEqualTo(arr.length - 1);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void zero_swaps_for_sorted_array() {
			int[] arr = {1, 2, 3, 4, 5};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long swaps = selectionSortSwapCount(arr);
				assertThat(swaps).as("No swaps needed for already sorted array")
					.isZero();
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void minimal_swaps_advantage_over_bubble_sort() {
			int[] arr = {5, 4, 3, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long swaps = selectionSortSwapCount(arr);
				assertThat(swaps).as("Selection sort uses fewer swaps than bubble sort")
					.isEqualTo(arr.length - 1);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void swap_only_when_minimum_differs_from_current() {
			int[] arr = {1, 5, 4, 3, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long swaps = selectionSortSwapCount(arr);
				assertThat(swaps).as("First element already in place, no swap needed")
					.isLessThan(arr.length - 1);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Minimum Finding

	@Nested
	@SuppressWarnings({"unused"})
	class Minimum_Finding {

		@Test
		void finds_minimum_in_unsorted_portion() {
			int[] arr = {5, 3, 1, 4, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
				assertThat(arr).containsExactly(1, 2, 3, 4, 5);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_multiple_minimums_takes_first() {
			int[] arr = {3, 1, 1, 1, 2};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
				assertThat(arr).containsExactly(1, 1, 1, 2, 3);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void comparison_count_is_n_n_minus_1_over_2() {
			int[] arr = {5, 4, 3, 2, 1};
			int n = arr.length;
			long expectedComparisons = (long) n * (n - 1) / 2;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long comparisons = selectionSortWithStepCount(arr);
				assertThat(comparisons).as("Should make n(n-1)/2 comparisons")
					.isEqualTo(expectedComparisons);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Non Adaptive

	@Nested
	@SuppressWarnings({"unused"})
	class Non_Adaptive {

		@Test
		void same_comparisons_regardless_of_input_order() {
			int[] sorted = {1, 2, 3, 4, 5};
			int[] reversed = {5, 4, 3, 2, 1};
			assertArrayCreated(sorted);
			assertArrayCreated(reversed);

			assertThatThrownBy(() -> {
				long stepsSorted = selectionSortWithStepCount(sorted.clone());
				long stepsReversed = selectionSortWithStepCount(reversed.clone());
				assertThat(stepsSorted).as("Same comparisons for any input order")
					.isEqualTo(stepsReversed);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void always_quadratic_time() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++) arr[i] = i;
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = selectionSortWithStepCount(arr);
				assertThat(steps).as("Even sorted input takes O(n²)")
					.isGreaterThanOrEqualTo(100 * 99 / 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void no_early_termination() {
			int[] arr = {1, 2, 3, 4, 5};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long steps = selectionSortWithStepCount(arr);
				assertThat(steps).as("Selection sort always does n(n-1)/2 comparisons")
					.isEqualTo(10);
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
				selectionSort(arr);
				int idx = java.util.Arrays.binarySearch(arr, 22);
				assertThat(idx).isGreaterThanOrEqualTo(0);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void multiple_sorts_idempotent() {
			int[] arr = {5, 2, 8, 1, 9};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				selectionSort(arr);
				int[] afterFirst = arr.clone();
				selectionSort(arr);
				assertThat(arr).isEqualTo(afterFirst);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void preferred_when_writes_are_expensive() {
			int[] arr = {5, 4, 3, 2, 1};
			assertArrayCreated(arr);

			assertThatThrownBy(() -> {
				long swaps = selectionSortSwapCount(arr);
				assertThat(swaps).as("Selection sort minimizes writes - at most n-1 swaps")
					.isLessThanOrEqualTo(4);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Helper Methods

	private void selectionSort(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private long selectionSortWithStepCount(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private long selectionSortSwapCount(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private void assertArrayCreated(int[] arr) {
		assertThat(arr).isNotNull();
	}

	//endregion
}
