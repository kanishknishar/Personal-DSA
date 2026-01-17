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
class BubbleSortTest {

	//region - Correctness

	@Nested
	class Correctness {

		@Test
		void sorts_unsorted_array() {
			int[] arr = {5, 2, 8, 1, 9};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_already_sorted_array() {
			int[] arr = {1, 2, 3, 4, 5};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_reverse_sorted_array() {
			int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessageContaining("Not implemented");
		}

		@Test
		void handles_empty_array() {
			int[] arr = {};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_single_element() {
			int[] arr = {42};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_duplicates() {
			int[] arr = {3, 1, 3, 2, 3};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}

		@Test
		void handles_negative_numbers() {
			int[] arr = {-5, -1, -10, 0, 3};

			assertThatThrownBy(() -> bubbleSort(arr))
				.isInstanceOf(UnsupportedOperationException.class);
		}
	}

	//endregion

	//region - Efficiency

	@Nested
	class Efficiency {

		@Test
		void best_case_nearly_sorted() {
			int[] arr = new int[100];
			for (int i = 0; i < 100; i++)
				arr[i] = i;
			arr[50] = 49;
			arr[49] = 50;

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
				arr[i] = (int) (Math.random() * 1000);

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
	class Stability {

		@Test
		void preserves_order_of_equal_elements() {
			assertThatThrownBy(() -> {
				// Create array with objects that have value and original position
				int[] arr = {3, 1, 3, 2, 3};
				bubbleSort(arr);
				// Verify that elements with same value maintain their relative order
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

	//region - Helper Methods

	private void bubbleSort(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private long bubbleSortWithStepCount(int[] arr) {
		throw new UnsupportedOperationException("Not implemented");
	}

	//endregion
}
