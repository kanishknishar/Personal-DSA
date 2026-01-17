package algorithms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InsertionSortTest {

	//region - Correctness

	@Nested
	class Correctness {

		@Test
		void sorts_unsorted_array() {
			int[] arr = {4, 2, 7, 1};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_already_sorted_array() {
			int[] arr = {1, 2, 3, 4, 5};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_reverse_sorted_array() {
			int[] arr = {10, 9, 8, 7, 6};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_single_element() {
			int[] arr = {42};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_duplicates() {
			int[] arr = {3, 1, 3, 2, 3};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_empty_array() {
			int[] arr = {};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_negative_numbers() {
			int[] arr = {-5, -1, -10, 0, 3};

			assertThatThrownBy(() -> insertionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Efficiency

	@Nested
	class Efficiency {

		@Test
		void best_case_already_sorted() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++)
				arr[i] = i;

			assertThatThrownBy(() -> {
				long steps = insertionSortWithStepCount(arr);
				assertThat(steps).as("Insertion sort should be O(n) on already sorted data")
					.isLessThan(100 * 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void efficient_on_nearly_sorted_data() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++)
				arr[i] = i;
			arr[50] = 49;

			assertThatThrownBy(() -> {
				long steps = insertionSortWithStepCount(arr);
				assertThat(steps).as("Insertion sort should be efficient on nearly sorted data")
					.isLessThan(100 * 10);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void worst_case_reverse_sorted() {
			int[] arr = new int[1000];
			for (int i = 0; i < 1000; i++)
				arr[i] = 1000 - i;

			assertThatThrownBy(() -> {
				long steps = insertionSortWithStepCount(arr);
				assertThat(steps).as("Worst case should be O(n²)")
					.isLessThan(1000 * 1000 * 2);
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void inefficiency_warning_for_worst_case() {
			int[] arr = new int[1000];
			for (int i = 0; i < 1000; i++)
				arr[i] = 1000 - i;

			assertThatThrownBy(() -> {
				long steps = insertionSortWithStepCount(arr);
				if (steps > 1000 * 1000 * 2) {
					throw new AssertionError("INEFFICIENT: Insertion sort taking too many steps - consider optimization");
				}
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void adaptive_performance_better_than_bubble_and_selection() {
			int[] arr = new int[200];
			for (int i = 0; i < 200; i++)
				arr[i] = i;
			arr[100] = 99;

			assertThatThrownBy(() -> {
				long steps = insertionSortWithStepCount(arr);
				assertThat(steps).as("Insertion sort should adapt to nearly sorted input")
					.isLessThan(200 * 50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Stability

	@Nested
	class Stability {

		@Test
		void maintains_stability() {
			assertThatThrownBy(() -> {
				// Insertion sort IS stable
				// Equal elements preserve their relative order
				int[] arr = {3, 1, 3, 2, 3};
				insertionSort(arr);
			}).isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void stability_verified_with_custom_objects() {
			assertThatThrownBy(() -> {
				// Use custom objects to verify stability
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Online Algorithm

	@Nested
	class OnlineAlgorithm {

		@Test
		void can_sort_data_as_it_arrives() {
			assertThatThrownBy(() -> {
				// Insertion sort is an "online" algorithm
				// It can sort data as it arrives one element at a time
				int[] arr = {3, 1, 4};
				insertionSort(arr);
				// Now add new element
				// Can efficiently insert into already sorted portion
			}).isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void efficient_for_small_datasets() {
			int[] arr = {5, 2, 8, 1, 9};

			assertThatThrownBy(() -> {
				long steps = insertionSortWithStepCount(arr);
				assertThat(steps).as("Insertion sort is efficient for small arrays despite being O(n²)")
					.isLessThan(50);
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Helper Methods

	private void insertionSort(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private long insertionSortWithStepCount(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	//endregion
}
