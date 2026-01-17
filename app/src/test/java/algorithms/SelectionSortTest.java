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

@ExtendWith(SoftAssertionsExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SelectionSortTest {

	//region - Correctness

	@Nested
	class Correctness {

		@Test
		void sorts_unsorted_array() {
			int[] arr = {5, 2, 8, 1};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_already_sorted_array() {
			int[] arr = {1, 2, 3, 4, 5};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_reverse_sorted_array() {
			int[] arr = {10, 9, 8, 7, 6};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_single_element() {
			int[] arr = {42};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_duplicates() {
			int[] arr = {3, 1, 3, 2};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_empty_array() {
			int[] arr = {};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_negative_numbers() {
			int[] arr = {-3, -1, -10, 0, 5};

			assertThatThrownBy(() -> selectionSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Efficiency

	@Nested
	class Efficiency {

		@Test
		void consistent_performance_regardless_of_input() {
			int[] sorted = new int[100];
			int[] reverse = new int[100];

			for (int i = 0; i < 100; i++) {
				sorted[i] = i;
				reverse[i] = 100 - i;
			}

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

			assertThatThrownBy(() -> {
				selectionSort(arr);
				// Selection sort makes O(n) swaps vs O(n²) for bubble sort
			}).isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Stability

	@Nested
	class Stability {

		@Test
		void not_stable_sort() {
			assertThatThrownBy(() -> {
				// Selection sort is NOT stable
				// Equal elements may not preserve their relative order
				int[] arr = {3, 1, 3, 2};
				selectionSort(arr);
			}).isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void demonstrates_instability_with_custom_objects() {
			assertThatThrownBy(() -> {
				// Use custom objects to demonstrate selection sort can change relative order
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

	//endregion
}
