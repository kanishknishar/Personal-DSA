package data_structures;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import data_structures.linked_list.DoublyLinkedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@DisplayName("DoublyLinkedList")
class DoublyLinkedListTest {

    private DoublyLinkedList<Integer> list;
    private static int totalTests = 0;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        list = new DoublyLinkedList<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        totalTests++;
        System.out.printf("[TEST %d] Running: %s%n", totalTests, testInfo.getDisplayName());
    }

    @AfterAll
    static void printTestSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("DOUBLY LINKED LIST TEST SUMMARY");
        System.out.println("=".repeat(80));
        System.out.printf("Total Tests:   %d%n", totalTests);
        int passedTests = 0;
        System.out.printf("Passed:        %d (%.1f%%)%n", passedTests, (passedTests * 100.0 / totalTests));
        int failedTests = 0;
        System.out.printf("Failed:        %d (%.1f%%)%n", failedTests, (failedTests * 100.0 / totalTests));
        System.out.println("=".repeat(80));
    }

    // ===============================================================================================
    // ADDITION OPERATIONS
    // ===============================================================================================

    @Nested
    @DisplayName("Addition Operations")
    class AdditionOperations {

        @Nested
        @DisplayName("addFirst()")
        class AddFirstTests {
            @Test
            @DisplayName("adds element to empty list")
            void addsElementToEmptyList() {
                list.addFirst(42);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return 42, but got %s", list.getFirst())
                        .isEqualTo(42);
                assertThat(list.getLast())
                        .as("Expected getLast() to return 42 (head == tail), but got %s", list.getLast())
                        .isEqualTo(42);
                assertThat(list.size())
                        .as("Expected size to be 1, but got %d", list.size())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("adds element at head of non-empty list")
            void addsElementAtHead() {
                list.addFirst(1);
                list.addFirst(2);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return 2, but got %s", list.getFirst())
                        .isEqualTo(2);
                assertThat(list.getLast())
                        .as("Expected getLast() to return 1 (tail unchanged), but got %s", list.getLast())
                        .isEqualTo(1);
                assertThat(list.size())
                        .as("Expected size to be 2, but got %d", list.size())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("maintains correct order (LIFO behavior for addFirst)")
            void maintainsCorrectOrder() {
                list.addFirst(1);
                list.addFirst(2);
                list.addFirst(3);
                // List: 3 <-> 2 <-> 1
                assertThat(list.getFirst())
                        .as("Expected getFirst() to be 3, but got %s", list.getFirst())
                        .isEqualTo(3);
                assertThat(list.getLast())
                        .as("Expected getLast() to be 1, but got %s", list.getLast())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("handles null value")
            void handlesNullValue() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addFirst(null);
                assertThat(stringList.getFirst())
                        .as("Expected getFirst() to return null, but got %s", stringList.getFirst())
                        .isNull();
            }

            @Test
            @DisplayName("stress: handles many sequential addFirst operations")
            void handlesManySequentialAddFirst() {
                int count = 1000;
                for (int i = 0; i < count; i++) {
                    list.addFirst(i);
                }
                assertThat(list.size())
                        .as("Expected size to be %d, but got %d", count, list.size())
                        .isEqualTo(count);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to be %d (last added), but got %s", count - 1, list.getFirst())
                        .isEqualTo(count - 1);
                assertThat(list.getLast())
                        .as("Expected getLast() to be 0 (first added), but got %s", list.getLast())
                        .isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("addLast()")
        class AddLastTests {
            @Test
            @DisplayName("adds element to empty list")
            void addsElementToEmptyList() {
                list.addLast(42);
                assertThat(list.getLast())
                        .as("Expected getLast() to return 42, but got %s", list.getLast())
                        .isEqualTo(42);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return 42 (head == tail), but got %s", list.getFirst())
                        .isEqualTo(42);
                assertThat(list.size())
                        .as("Expected size to be 1, but got %d", list.size())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("adds element at tail of non-empty list")
            void addsElementAtTail() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.getLast())
                        .as("Expected getLast() to return 2, but got %s", list.getLast())
                        .isEqualTo(2);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return 1 (head unchanged), but got %s", list.getFirst())
                        .isEqualTo(1);
                assertThat(list.size())
                        .as("Expected size to be 2, but got %d", list.size())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("maintains correct order")
            void maintainsCorrectOrder() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                // List: 1 <-> 2 <-> 3
                assertThat(list.getFirst())
                        .as("Expected getFirst() to be 1, but got %s", list.getFirst())
                        .isEqualTo(1);
                assertThat(list.getLast())
                        .as("Expected getLast() to be 3, but got %s", list.getLast())
                        .isEqualTo(3);
            }

            @Test
            @DisplayName("handles null value")
            void handlesNullValue() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast(null);
                assertThat(stringList.getLast())
                        .as("Expected getLast() to return null, but got %s", stringList.getLast())
                        .isNull();
            }

            @Test
            @DisplayName("stress: handles many sequential addLast operations")
            void handlesManySequentialAddLast() {
                int count = 1000;
                for (int i = 0; i < count; i++) {
                    list.addLast(i);
                }
                assertThat(list.size())
                        .as("Expected size to be %d, but got %d", count, list.size())
                        .isEqualTo(count);
                assertThat(list.getLast())
                        .as("Expected getLast() to be %d, but got %s", count - 1, list.getLast())
                        .isEqualTo(count - 1);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to be 0, but got %s", list.getFirst())
                        .isEqualTo(0);
            }

            @Test
            @DisplayName("handles duplicate elements")
            void handlesDuplicateElements() {
                list.addLast(1);
                list.addLast(1);
                list.addLast(1);

                assertThat(list.size())
                        .as("Expected size to be 3 with duplicate elements, but got %d", list.size())
                        .isEqualTo(3);
            }
        }

        @Nested
        @DisplayName("addAt()")
        class AddAtTests {
            @Test
            @DisplayName("inserts at index 0 (equivalent to addFirst)")
            void insertsAtZero() {
                list.addAt(0, 42);
                assertThat(list.get(0))
                        .as("Expected get(0) to return 42, but got %s", list.get(0))
                        .isEqualTo(42);
                assertThat(list.size())
                        .as("Expected size to be 1, but got %d", list.size())
                        .isEqualTo(1);

                // Add to existing
                list.addAt(0, 99);
                assertThat(list.get(0))
                        .as("Expected get(0) to be 99, but got %s", list.get(0))
                        .isEqualTo(99);
                assertThat(list.get(1))
                        .as("Expected get(1) to be 42, but got %s", list.get(1))
                        .isEqualTo(42);
            }

            @Test
            @DisplayName("inserts at middle index")
            void insertsAtMiddleIndex() {
                list.addLast(1);
                list.addLast(3);
                list.addAt(1, 2);
                // List: 1 <-> 2 <-> 3
                assertThat(list.get(1))
                        .as("Expected get(1) to be 2, but got %s", list.get(1))
                        .isEqualTo(2);
                assertThat(list.get(2))
                        .as("Expected get(2) to be 3, but got %s", list.get(2))
                        .isEqualTo(3);
                assertThat(list.size())
                        .as("Expected size to be 3, but got %d", list.size())
                        .isEqualTo(3);
            }

            @Test
            @DisplayName("inserts at end (index equals size, equivalent to addLast)")
            void insertsAtEnd() {
                list.addLast(1);
                list.addAt(1, 2);
                assertThat(list.get(1))
                        .as("Expected get(1) to be 2, but got %s", list.get(1))
                        .isEqualTo(2);
                assertThat(list.getLast())
                        .as("Expected getLast() to be 2 (used tail reference), but got %s", list.getLast())
                        .isEqualTo(2);
                assertThat(list.size())
                        .as("Expected size to be 2, but got %d", list.size())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("optimization: inserts near end using tail traversal")
            void insertsNearEndUsingTail() {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }
                // Insert at index 8 (near tail) - should traverse from tail
                list.addAt(8, 99);
                assertThat(list.get(8))
                        .as("Expected get(8) to be 99, but got %s", list.get(8))
                        .isEqualTo(99);
                assertThat(list.size())
                        .as("Expected size to be 11, but got %d", list.size())
                        .isEqualTo(11);
            }

            @Test
            @DisplayName("handles null value insertion")
            void handlesNullValueInsertion() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast("a");
                stringList.addAt(1, null);
                assertThat(stringList.get(1))
                        .as("Expected get(1) to return null, but got %s", stringList.get(1))
                        .isNull();
            }

            @Test
            @DisplayName("throws exception when invalid index")
            void throwsExceptionWhenInvalidIndex() {
                list.addLast(1);

                assertThatThrownBy(() -> list.addAt(-1, 42))
                        .as("Expected addAt(-1, 42) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);

                assertThatThrownBy(() -> list.addAt(5, 42))
                        .as("Expected addAt(5, 42) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }
    }

    // ===============================================================================================
    // ACCESS & SEARCH OPERATIONS
    // ===============================================================================================

    @Nested
    @DisplayName("Access Operations")
    class AccessOperations {

        @Nested
        @DisplayName("get()")
        class GetTests {
            @Test
            @DisplayName("returns element at index 0 using head")
            void returnsElementAtIndexZero() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.get(0))
                        .as("Expected get(0) to return 1 from list [1, 2], but got %s", list.get(0))
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("returns element at middle index")
            void returnsElementAtMiddleIndex() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.get(1))
                        .as("Expected get(1) to return 2 from list [1, 2, 3], but got %s", list.get(1))
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("returns element at last index using tail")
            void returnsElementAtLastIndex() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.get(2))
                        .as("Expected get(2) to return 3 from list [1, 2, 3], but got %s", list.get(2))
                        .isEqualTo(3);
            }

            @Test
            @DisplayName("optimization: gets from first half using head traversal")
            void getsFromFirstHalfUsingHead() {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }
                // Get index 2 (first half) - should traverse from head
                assertThat(list.get(2))
                        .as("Expected get(2) to be 2, but got %s", list.get(2))
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("optimization: gets from second half using tail traversal")
            void getsFromSecondHalfUsingTail() {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }
                // Get index 8 (second half) - should traverse from tail
                assertThat(list.get(8))
                        .as("Expected get(8) to be 8, but got %s", list.get(8))
                        .isEqualTo(8);
            }

            @Test
            @DisplayName("boundary: Integer.MAX_VALUE and MIN_VALUE")
            void boundaryTestWithIntegerLimits() {
                list.addFirst(Integer.MAX_VALUE);
                list.addFirst(Integer.MIN_VALUE);
                list.addLast(0);

                // List: [MIN_VALUE, MAX_VALUE, 0]

                assertThat(list.get(1))
                        .as("Expected get(1) to be MAX_VALUE, but got %s", list.get(1))
                        .isEqualTo(Integer.MAX_VALUE);
            }

            @Test
            @DisplayName("throws exception when input is invalid")
            void throwsExceptionWhenInputInvalid() {
                list.addLast(1);

                // Negative index
                assertThatThrownBy(() -> list.get(-1))
                        .as("Expected get(-1) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);

                // Index >= size
                assertThatThrownBy(() -> list.get(1))
                        .as("Expected get(1) checks index >= size, throws IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);

                // Much larger index
                assertThatThrownBy(() -> list.get(100))
                        .as("Expected get(100) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            @DisplayName("throws on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.get(0))
                        .as("Expected get(0) on empty list to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        @DisplayName("getFirst()")
        class GetFirstTests {
            @Test
            @DisplayName("returns first element using head reference")
            void returnsFirstElement() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return 1, but got %s", list.getFirst())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("handles Integer.MAX_VALUE")
            void handlesMaxIntegerValue() {
                list.addFirst(Integer.MAX_VALUE);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return Integer.MAX_VALUE, but got %s", list.getFirst())
                        .isEqualTo(Integer.MAX_VALUE);
            }

            @Test
            @DisplayName("handles Integer.MIN_VALUE")
            void handlesMinIntegerValue() {
                list.addFirst(Integer.MIN_VALUE);
                assertThat(list.getFirst())
                        .as("Expected getFirst() to return Integer.MIN_VALUE, but got %s", list.getFirst())
                        .isEqualTo(Integer.MIN_VALUE);
            }

            @Test
            @DisplayName("throws exception on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.getFirst())
                        .as("Expected getFirst() on empty list to throw NoSuchElementException")
                        .isInstanceOf(NoSuchElementException.class);
            }

            @Test
            @DisplayName("returns null when first element is null")
            void returnsNullWhenFirstIsNull() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addFirst(null);
                assertThat(stringList.getFirst())
                        .as("Expected getFirst() to return null, but got %s", stringList.getFirst())
                        .isNull();
            }

            @Test
            @DisplayName("getFirst equals getLast when list has single element")
            void getFirstEqualsGetLastForSingleElement() {
                list.addLast(42);
                assertThat(list.getFirst())
                        .as("Expected getFirst() (%s) and getLast() (%s) to be equal", list.getFirst(), list.getLast())
                        .isEqualTo(list.getLast());
            }
        }

        @Nested
        @DisplayName("getLast()")
        class GetLastTests {
            @Test
            @DisplayName("returns last element using tail reference")
            void returnsLastElement() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.getLast())
                        .as("Expected getLast() to return 2, but got %s", list.getLast())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("throws exception on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.getLast())
                        .as("Expected getLast() on empty list to throw NoSuchElementException")
                        .isInstanceOf(NoSuchElementException.class);
            }

            @Test
            @DisplayName("returns null when last element is null")
            void returnsNullWhenLastIsNull() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast(null);
                assertThat(stringList.getLast())
                        .as("Expected getLast() to return null, but got %s", stringList.getLast())
                        .isNull();
            }
        }

        @Nested
        @DisplayName("indexOf()")
        class IndexOfTests {
            @Test
            @DisplayName("returns index of existing element")
            void returnsIndexOfExistingElement() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.indexOf(2))
                        .as("Expected indexOf(2) to return 1, but got %d", list.indexOf(2))
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("returns 0 for first element")
            void returnsZeroForFirstElement() {
                list.addLast(1);
                list.addLast(2);
                assertThat(list.indexOf(1))
                        .as("Expected indexOf(1) to return 0, but got %d", list.indexOf(1))
                        .isEqualTo(0);
            }

            @Test
            @DisplayName("returns last index for last element")
            void returnsLastIndexForLastElement() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.indexOf(3))
                        .as("Expected indexOf(3) to return 2, but got %d", list.indexOf(3))
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("returns -1 for non-existing element")
            void returnsMinusOneForNonExistingElement() {
                list.addLast(1);
                assertThat(list.indexOf(99))
                        .as("Expected indexOf(99) to return -1, but got %d", list.indexOf(99))
                        .isEqualTo(-1);
            }

            @Test
            @DisplayName("returns -1 on empty list")
            void returnsMinusOneOnEmptyList() {
                assertThat(list.indexOf(42))
                        .as("Expected indexOf(42) on empty list to return -1, but got %d", list.indexOf(42))
                        .isEqualTo(-1);
            }

            @Test
            @DisplayName("duplicates: returns first index")
            void returnsFirstIndexWhenDuplicatesExist() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(1);
                assertThat(list.indexOf(1))
                        .as("Expected indexOf(1) to return 0 (first occurrence), but got %d", list.indexOf(1))
                        .isEqualTo(0);
            }

            @Test
            @DisplayName("finds null element")
            void findsNullElement() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast("a");
                stringList.addLast(null);
                stringList.addLast("b");
                assertThat(stringList.indexOf(null))
                        .as("Expected indexOf(null) to return 1, but got %d", stringList.indexOf(null))
                        .isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("lastIndexOf()")
        class LastIndexOfTests {
            @Test
            @DisplayName("optimization: returns last index traversing from tail")
            void returnsLastIndexFromTail() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.lastIndexOf(3))
                        .as("Expected lastIndexOf(3) to return 2, but got %d", list.lastIndexOf(3))
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("duplicates: returns last occurrence")
            void returnsLastOccurrence() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(1);
                assertThat(list.lastIndexOf(1))
                        .as("Expected lastIndexOf(1) to return 2 (last occurrence), but got %d", list.lastIndexOf(1))
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("returns -1 for non-existing element")
            void returnsMinusOneForNonExisting() {
                list.addLast(1);
                assertThat(list.lastIndexOf(99))
                        .as("Expected lastIndexOf(99) to return -1, but got %d", list.lastIndexOf(99))
                        .isEqualTo(-1);
            }

            @Test
            @DisplayName("finds null element from tail")
            void findsNullElementFromTail() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast("a");
                stringList.addLast(null);
                stringList.addLast("b");
                stringList.addLast(null);
                assertThat(stringList.lastIndexOf(null))
                        .as("Expected lastIndexOf(null) to return 3, but got %d", stringList.lastIndexOf(null))
                        .isEqualTo(3);
            }
        }
    }

    // ===============================================================================================
    // UPDATE OPERATIONS
    // ===============================================================================================

    @Nested
    @DisplayName("Update Operations")
    class UpdateOperations {

        @Nested
        @DisplayName("set()")
        class SetTests {
            @Test
            @DisplayName("sets element at valid indices")
            void setsElementAtIndices() {
                list.addLast(1); // 0
                list.addLast(2); // 1
                list.addLast(3); // 2

                // Set at 0 (head)
                list.set(0, 99);
                assertThat(list.get(0))
                        .as("Expected get(0) to return 99 after set(0, 99), but got %s", list.get(0))
                        .isEqualTo(99);

                // Set at middle
                list.set(1, 88);
                assertThat(list.get(1))
                        .as("Expected get(1) to return 88 after set(1, 88), but got %s", list.get(1))
                        .isEqualTo(88);

                // Set at last (tail)
                list.set(2, 77);
                assertThat(list.get(2))
                        .as("Expected get(2) to return 77 after set(2, 77), but got %s", list.get(2))
                        .isEqualTo(77);
            }

            @Test
            @DisplayName("optimization: sets near end using tail traversal")
            void setsNearEndUsingTail() {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }
                // Set at index 8 (near tail) - should traverse from tail
                list.set(8, 99);
                assertThat(list.get(8))
                        .as("Expected get(8) to be 99, but got %s", list.get(8))
                        .isEqualTo(99);
            }

            @Test
            @DisplayName("does not change size")
            void doesNotChangeSize() {
                list.addLast(1);
                list.addLast(2);
                int initialSize = list.size();
                list.set(0, 99);
                assertThat(list.size())
                        .as("Expected size to remain %d, but got %d", initialSize, list.size())
                        .isEqualTo(initialSize);
            }

            @Test
            @DisplayName("can set value to null")
            void canSetValueToNull() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast("a");
                stringList.set(0, null);
                assertThat(stringList.get(0))
                        .as("Expected get(0) to return null after set(0, null), but got %s", stringList.get(0))
                        .isNull();
            }

            @Test
            @DisplayName("throws exception when input is invalid")
            void throwsExceptionWhenInputInvalid() {
                list.addLast(1);

                assertThatThrownBy(() -> list.set(-1, 99))
                        .as("Expected set(-1, 99) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);

                assertThatThrownBy(() -> list.set(1, 99))
                        .as("Expected set(1, 99) (index == size) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            @DisplayName("throws exception on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.set(0, 99))
                        .as("Expected set(0, 99) on empty list to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }
    }

    // ===============================================================================================
    // DELETION OPERATIONS
    // ===============================================================================================

    @Nested
    @DisplayName("Deletion Operations")
    class DeletionOperations {

        @Nested
        @DisplayName("deleteFirst()")
        class DeleteFirstTests {
            @Test
            @DisplayName("removes and returns first element using head")
            void removesAndReturnsFirstElement() {
                list.addLast(1);
                list.addLast(2);
                Integer removed = list.deleteFirst();

                assertThat(removed)
                        .as("Expected returned value to be 1, but got %s", removed)
                        .isEqualTo(1);
                assertThat(list.getFirst())
                        .as("Expected new head to be 2, but got %s", list.getFirst())
                        .isEqualTo(2);
                assertThat(list.size())
                        .as("Expected size to be 1, but got %d", list.size())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("throws exception on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.deleteFirst())
                        .as("Expected deleteFirst() on empty list to throw NoSuchElementException")
                        .isInstanceOf(NoSuchElementException.class);
            }

            @Test
            @DisplayName("consecutive deletes: clears list")
            void consecutiveDeletesClearsList() {
                list.addLast(1);
                list.addLast(2);
                list.deleteFirst();
                list.deleteFirst();
                assertThat(list.isEmpty())
                        .as("Expected list to be empty after deleting all elements, but isEmpty() is false")
                        .isTrue();

                // Verify expecting throw again
                assertThatThrownBy(() -> list.deleteFirst())
                        .as("deleteFirst() on now empty list should throw")
                        .isInstanceOf(NoSuchElementException.class);
            }

            @Test
            @DisplayName("can remove null element")
            void canRemoveNullElement() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addFirst(null);
                String removed = stringList.deleteFirst();
                assertThat(removed)
                        .as("Expected deleteFirst() to return null, but got %s", removed)
                        .isNull();
            }

            @Test
            @DisplayName("single element: head and tail become null")
            void singleElementListBecomesEmpty() {
                list.addFirst(42);
                list.deleteFirst();
                assertThat(list.isEmpty())
                        .as("Expected list to be empty (head and tail null), but got false")
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("deleteLast()")
        class DeleteLastTests {
            @Test
            @DisplayName("removes and returns last element using tail")
            void removesAndReturnsLastElement() {
                list.addLast(1);
                list.addLast(2);
                Integer removed = list.deleteLast();

                assertThat(removed)
                        .as("Expected returned value to be 2, but got %s", removed)
                        .isEqualTo(2);
                assertThat(list.getLast())
                        .as("Expected new tail to be 1, but got %s", list.getLast())
                        .isEqualTo(1);
                assertThat(list.size())
                        .as("Expected size to be 1, but got %d", list.size())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("throws exception on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.deleteLast())
                        .as("Expected deleteLast() on empty list to throw NoSuchElementException")
                        .isInstanceOf(NoSuchElementException.class);
            }

            @Test
            @DisplayName("single element list: becomes empty")
            void singleElementListBecomesEmpty() {
                list.addFirst(42);
                Integer removed = list.deleteLast();
                assertThat(removed)
                        .as("Expected deleteLast() to return 42, but got %s", removed)
                        .isEqualTo(42);
                assertThat(list.isEmpty())
                        .as("Expected list to be empty, but got false")
                        .isTrue();
            }

            @Test
            @DisplayName("consecutive deletes from back")
            void consecutiveDeletesFromBack() {
                list.addLast(1);
                list.addLast(2);
                list.deleteLast();
                list.deleteLast();
                assertThat(list.isEmpty())
                        .as("Expected list to be empty, but got false")
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("deleteAt()")
        class DeleteAtTests {
            @Test
            @DisplayName("removes element at index")
            void removesElementAtIndex() {
                // List: 1 <-> 2 <-> 3
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);

                // Delete '2' at index 1
                Integer removed = list.deleteAt(1);
                assertThat(removed)
                        .as("Expected returned value to be 2, but got %s", removed)
                        .isEqualTo(2);
                assertThat(list.get(1))
                        .as("Expected element at index 1 to be 3 after shift, but got %s", list.get(1))
                        .isEqualTo(3);
                assertThat(list.size())
                        .as("Expected size to be 2, but got %d", list.size())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("removes head (index 0)")
            void removesHead() {
                list.addLast(1);
                list.addLast(2);
                list.deleteAt(0);
                assertThat(list.getFirst())
                        .as("Expected new head to be 2, but got %s", list.getFirst())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("removes tail (index size-1)")
            void removesTail() {
                list.addLast(1);
                list.addLast(2);
                list.deleteAt(1);
                assertThat(list.getLast())
                        .as("Expected new tail to be 1, but got %s", list.getLast())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("optimization: deletes near end using tail traversal")
            void deletesNearEndUsingTail() {
                for (int i = 0; i < 10; i++) {
                    list.addLast(i);
                }
                // Delete at index 8 (near tail) - should traverse from tail
                Integer removed = list.deleteAt(8);
                assertThat(removed)
                        .as("Expected deleteAt(8) to return 8, but got %s", removed)
                        .isEqualTo(8);
                assertThat(list.size())
                        .as("Expected size to be 9, but got %d", list.size())
                        .isEqualTo(9);
            }

            @Test
            @DisplayName("throws exception when invalid index")
            void throwsExceptionWhenInvalidIndex() {
                list.addLast(1);

                assertThatThrownBy(() -> list.deleteAt(-1))
                        .as("Expected deleteAt(-1) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);

                assertThatThrownBy(() -> list.deleteAt(1))
                        .as("Expected deleteAt(1) (index == size) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);

                assertThatThrownBy(() -> list.deleteAt(5))
                        .as("Expected deleteAt(5) to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            @DisplayName("throws on empty list")
            void throwsOnEmptyList() {
                assertThatThrownBy(() -> list.deleteAt(0))
                        .as("Expected deleteAt(0) on empty list to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        @DisplayName("delete()")
        class DeleteByValueTests {
            @Test
            @DisplayName("removes first occurrence and returns true")
            void removesFirstOccurrence() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(1);
                boolean removed = list.delete(1);
                assertThat(removed)
                        .as("Expected delete(1) to return true when element exists, but got %s", removed)
                        .isTrue();
                assertThat(list.size())
                        .as("Expected size to be 2, but got %d", list.size())
                        .isEqualTo(2);
                assertThat(list.get(0))
                        .as("Expected get(0) to be 2, but got %s", list.get(0))
                        .isEqualTo(2);
                assertThat(list.get(1))
                        .as("Expected get(1) to be 1, but got %s", list.get(1))
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("returns false when value not found")
            void returnsFalseWhenNotFound() {
                list.addLast(1);
                boolean removed = list.delete(99);
                assertThat(removed)
                        .as("Expected delete(99) to return false when not found, but got %s", removed)
                        .isFalse();
            }

            @Test
            @DisplayName("removes null value if present")
            void removesNullValue() {
                DoublyLinkedList<String> stringList = new DoublyLinkedList<>("HEAD", "TAIL");
                stringList.addLast("a");
                stringList.addLast(null);
                stringList.addLast("b");
                boolean removed = stringList.delete(null);
                assertThat(removed)
                        .as("Expected delete(null) to return true when present, but got %s", removed)
                        .isTrue();
                assertThat(stringList.size())
                        .as("Expected size to be 2, but got %d", stringList.size())
                        .isEqualTo(2);
                assertThat(stringList.get(0))
                        .as("Expected get(0) to be 'a', but got %s", stringList.get(0))
                        .isEqualTo("a");
                assertThat(stringList.get(1))
                        .as("Expected get(1) to be 'b', but got %s", stringList.get(1))
                        .isEqualTo("b");
            }
        }
    }

    // ===============================================================================================
    // UTILITY OPERATIONS
    // ===============================================================================================

    @Nested
    @DisplayName("Utility Operations")
    class UtilityOperations {

        @Nested
        @DisplayName("size()")
        class SizeTests {
            @Test
            @DisplayName("correctly tracks size")
            void correctlyTracksSize() {
                assertThat(list.size())
                        .as("Expected size 0 for new list, but got %d", list.size())
                        .isEqualTo(0);

                list.addFirst(1);
                assertThat(list.size())
                        .as("Expected size 1 after add, but got %d", list.size())
                        .isEqualTo(1);

                list.addLast(2);
                assertThat(list.size())
                        .as("Expected size 2 after add, but got %d", list.size())
                        .isEqualTo(2);

                list.deleteFirst();
                assertThat(list.size())
                        .as("Expected size 1 after delete, but got %d", list.size())
                        .isEqualTo(1);
            }

            @Test
            @DisplayName("stress: size after many operations")
            void sizeAfterManyOperations() {
                for (int i = 0; i < 1000; i++) list.addLast(i);
                assertThat(list.size())
                        .as("Expected size to be 1000, but got %d", list.size())
                        .isEqualTo(1000);

                for (int i = 0; i < 500; i++) list.deleteFirst();
                assertThat(list.size())
                        .as("Expected size to be 500, but got %d", list.size())
                        .isEqualTo(500);
            }
        }

        @Nested
        @DisplayName("isEmpty()")
        class IsEmptyTests {
            @Test
            @DisplayName("returns true if empty")
            void returnsTrueIfEmpty() {
                assertThat(list.isEmpty())
                        .as("Expected isEmpty() to be true, but got false")
                        .isTrue();
            }

            @Test
            @DisplayName("returns false if not empty")
            void returnsFalseIfNotEmpty() {
                list.addFirst(1);
                assertThat(list.isEmpty())
                        .as("Expected isEmpty() to be false, but got true")
                        .isFalse();
            }

            @Test
            @DisplayName("returns true after clear/removal")
            void returnsTrueAfterRemoval() {
                list.addFirst(1);
                list.deleteFirst();
                assertThat(list.isEmpty())
                        .as("Expected isEmpty() to be true after removal, but got false")
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("toString()")
        class ToStringTests {
            @Test
            @DisplayName("formats empty list")
            void formatsEmptyList() {
                assertThat(list.toString())
                        .as("Expected empty list toString to be [], but got %s", list.toString())
                        .isEqualTo("[]");
            }

            @Test
            @DisplayName("formats list with elements")
            void formatsListWithElements() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.toString())
                        .as("Expected toString to be [1, 2, 3], but got %s", list.toString())
                        .isEqualTo("[1, 2, 3]");
            }

            @Test
            @DisplayName("formats list with null elements")
            void formatsListWithNulls() {
                list.addLast(1);
                list.addLast(null);
                list.addLast(3);
                assertThat(list.toString())
                        .as("Expected toString to be [1, null, 3], but got %s", list.toString())
                        .isEqualTo("[1, null, 3]");
            }

            @Test
            @DisplayName("large list formatting")
            void largeListFormatting() {
                for (int i = 0; i < 5; i++) list.addLast(i);

                String str = list.toString();
                assertThat(str)
                        .as("Expected toString to start with '[', but got %s", str)
                        .startsWith("[");
                assertThat(str)
                        .as("Expected toString to end with ']', but got %s", str)
                        .endsWith("]");
                assertThat(str)
                        .as("Expected toString to contain ', ', but got %s", str)
                        .contains(", ");
            }
        }


    }

    // ===============================================================================================
    // OPTIMIZATION & ROBUSTNESS TESTS
    // ===============================================================================================

    @Nested
    @DisplayName("Optimization & Robustness Tests")
    class OptimizationAndRobustnessTests {



        @Test
        @DisplayName("stress: large number of sequential adds and removes")
        void handlesLargeSequentialOperations() {
            for (int i = 0; i < 1000; i++) {
                list.addLast(i);
            }
            assertThat(list.size())
                    .as("Expected size 1000 after bulk add, but got %d", list.size())
                    .isEqualTo(1000);

            for (int i = 0; i < 500; i++) {
                list.deleteFirst();
            }
            assertThat(list.getFirst())
                    .as("Expected head after removing 500 to be 500, but got %s", list.getFirst())
                    .isEqualTo(500);
            assertThat(list.size())
                    .as("Expected size to be 500 after removing 500 elements, but got %d", list.size())
                    .isEqualTo(500);

            for (int i = 0; i < 500; i++) {
                list.deleteLast();
            }
            assertThat(list.isEmpty())
                    .as("Expected list to be empty after removing all elements, but got false")
                    .isTrue();
        }

        @Test
        @DisplayName("stress: interleaved operations using head and tail")
        void interleavedHeadTailOperations() {
            for (int i = 0; i < 10; i++) {
                list.addFirst(i);
                list.addLast(i + 100);
            }

            assertThat(list.size())
                    .as("Expected size to be 20, but got %d", list.size())
                    .isEqualTo(20);

            for (int i = 0; i < 5; i++) {
                list.deleteFirst();
                list.deleteLast();
            }

            assertThat(list.size())
                    .as("Expected size 10 after alternating operations, but got %d", list.size())
                    .isEqualTo(10);
        }

        @Test
        @DisplayName("optimization: midpoint traversal for get/set/delete")
        void midpointOptimizationForOperations() {
            for (int i = 0; i < 100; i++) {
                list.addLast(i);
            }

            // Access first quarter (should use head)
            assertThat(list.get(10))
                    .as("Expected get(10) to be 10, but got %s", list.get(10))
                    .isEqualTo(10);

            // Access last quarter (should use tail)
            assertThat(list.get(90))
                    .as("Expected get(90) to be 90, but got %s", list.get(90))
                    .isEqualTo(90);

            // Set near end (should use tail)
            list.set(90, 999);
            assertThat(list.get(90))
                    .as("Expected get(90) to be 999 after set, but got %s", list.get(90))
                    .isEqualTo(999);

            // Delete near end (should use tail)
            list.deleteAt(90);
            assertThat(list.size())
                    .as("Expected size to be 99, but got %d", list.size())
                    .isEqualTo(99);
        }

        @Test
        @DisplayName("boundary values: Integer.MAX_VALUE and MIN_VALUE")
        void boundaryValuesIntegerMaxMin() {
            list.addLast(Integer.MAX_VALUE);
            list.addFirst(Integer.MIN_VALUE);
            list.addAt(1, 0);

            assertThat(list.get(0))
                    .as("Expected get(0) to be MIN_VALUE, but got %s", list.get(0))
                    .isEqualTo(Integer.MIN_VALUE);
            assertThat(list.get(1))
                    .as("Expected get(1) to be 0, but got %s", list.get(1))
                    .isZero();
            assertThat(list.get(2))
                    .as("Expected get(2) to be MAX_VALUE, but got %s", list.get(2))
                    .isEqualTo(Integer.MAX_VALUE);
        }





        @Test
        @DisplayName("get operations at boundaries after removals")
        void getAtBoundariesAfterRemovals() {
            for (int i = 0; i < 10; i++) {
                list.addLast(i);
            }

            list.deleteFirst();
            list.deleteFirst();
            list.deleteLast();
            list.deleteLast();

            assertThat(list.getFirst())
                    .as("Expected first after removals to be 2, but got %s", list.getFirst())
                    .isEqualTo(2);
            assertThat(list.getLast())
                    .as("Expected last after removals to be 7, but got %s", list.getLast())
                    .isEqualTo(7);
            assertThat(list.get(0))
                    .as("Expected index 0 after removals to be 2, but got %s", list.get(0))
                    .isEqualTo(2);
            assertThat(list.get(list.size() - 1))
                    .as("Expected last index after removals to be 7, but got %s", list.get(list.size() - 1))
                    .isEqualTo(7);
        }

        @Test
        @DisplayName("size never goes negative")
        void sizeNeverNegative() {
            assertThat(list.size())
                    .as("Expected empty list size >= 0, but got %d", list.size())
                    .isGreaterThanOrEqualTo(0);

            list.addLast(1);
            assertThat(list.size())
                    .as("Expected size after add >= 0, but got %d", list.size())
                    .isGreaterThanOrEqualTo(0);

            list.deleteFirst();
            assertThat(list.size())
                    .as("Expected size after remove >= 0, but got %d", list.size())
                    .isGreaterThanOrEqualTo(0);
        }
    }
}

