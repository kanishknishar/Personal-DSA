package data_structures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import data_structures.linked_list.SinglyLinkedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.NoSuchElementException;

@DisplayName("SinglyLinkedList")
class SinglyLinkedListTest {

    private SinglyLinkedList<Integer> list;
    private static int totalTests = 0;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        list = new SinglyLinkedList<>();
        totalTests++;
        System.out.printf("[TEST %d] Running: %s%n", totalTests, testInfo.getDisplayName());
    }

    @AfterAll
    static void printTestSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("SINGLY LINKED LIST TEST SUMMARY");
        System.out.println("=".repeat(80));
        System.out.printf("Total Tests:   %d%n", totalTests);
        int passedTests = 0;
        System.out.printf("Passed:        %d (%.1f%%)%n", passedTests, (passedTests * 100.0 / totalTests));
        int failedTests = 0;
        System.out.printf("Failed:        %d (%.1f%%)%n", failedTests, (failedTests * 100.0 / totalTests));
        System.out.println("=".repeat(80));
    }

    // ===============================================================================================
    // INSERTION OPERATIONS
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
                // List: 3 -> 2 -> 1
                assertThat(list.getFirst())
                        .as("Expected getFirst() to be 3, but got %s", list.getFirst())
                        .isEqualTo(3);
            }

            @Test
            @DisplayName("handles null value")
            void handlesNullValue() {
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
                stringList.addFirst(null);
                assertThat(stringList.getFirst())
                        .as("Expected getFirst() to return null, but got %s", stringList.getFirst())
                        .isNull();
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
                // List: 1 -> 2 -> 3
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
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
                stringList.addLast(null);
                assertThat(stringList.getLast())
                        .as("Expected getLast() to return null, but got %s", stringList.getLast())
                        .isNull();
            }

            @Test
            @DisplayName("stress: handles many sequential adds")
            void handlesManySequentialAdds() {
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
                // List: 1 -> 2 -> 3
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
            @DisplayName("inserts at end (index equals size)")
            void insertsAtEnd() {
                list.addLast(1);
                list.addAt(1, 2);
                assertThat(list.get(1))
                        .as("Expected get(1) to be 2, but got %s", list.get(1))
                        .isEqualTo(2);
                assertThat(list.size())
                        .as("Expected size to be 2, but got %d", list.size())
                        .isEqualTo(2);
            }

            @Test
            @DisplayName("handles null value insertion")
            void handlesNullValueInsertion() {
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
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
            @DisplayName("returns element at index 0")
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
            @DisplayName("returns element at last index")
            void returnsElementAtLastIndex() {
                list.addLast(1);
                list.addLast(2);
                list.addLast(3);
                assertThat(list.get(2))
                        .as("Expected get(2) to return 3 from list [1, 2, 3], but got %s", list.get(2))
                        .isEqualTo(3);
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
                // Code matches simpler exception strategy: throws IndexOutOfBounds instead of NoSuchElement
                assertThatThrownBy(() -> list.get(0))
                        .as("Expected get(0) on empty list to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }
        }

        @Nested
        @DisplayName("getFirst()")
        class GetFirstTests {
            @Test
            @DisplayName("returns first element")
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
                        .as("Expected getFirst() on empty list to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            @DisplayName("returns null when first element is null")
            void returnsNullWhenFirstIsNull() {
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
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
            @DisplayName("returns last element")
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
                        .as("Expected getLast() on empty list to throw IndexOutOfBoundsException")
                        .isInstanceOf(IndexOutOfBoundsException.class);
            }

            @Test
            @DisplayName("returns null when last element is null")
            void returnsNullWhenLastIsNull() {
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
                stringList.addLast(null);
                assertThat(stringList.getLast())
                        .as("Expected getLast() to return null, but got %s", stringList.getLast())
                        .isNull();
            }
        }

//        @Nested
//        @DisplayName("indexOf()")
//        class IndexOfTests {
//            @Test
//            @DisplayName("returns index of existing element")
//            void returnsIndexOfExistingElement() {
//                list.addLast(1);
//                list.addLast(2);
//                list.addLast(3);
//                assertThat(list.indexOf(2))
//                        .as("Expected indexOf(2) to return 1, but got %d", list.indexOf(2))
//                        .isEqualTo(1);
//            }
//
//            @Test
//            @DisplayName("returns 0 for first element")
//            void returnsZeroForFirstElement() {
//                list.addLast(1);
//                list.addLast(2);
//                assertThat(list.indexOf(1))
//                        .as("Expected indexOf(1) to return 0, but got %d", list.indexOf(1))
//                        .isEqualTo(0);
//            }
//
//            @Test
//            @DisplayName("returns last index for last element")
//            void returnsLastIndexForLastElement() {
//                list.addLast(1);
//                list.addLast(2);
//                list.addLast(3);
//                assertThat(list.indexOf(3))
//                        .as("Expected indexOf(3) to return 2, but got %d", list.indexOf(3))
//                        .isEqualTo(2);
//            }
//
//            @Test
//            @DisplayName("returns -1 for non-existing element")
//            void returnsMinusOneForNonExistingElement() {
//                list.addLast(1);
//                assertThat(list.indexOf(99))
//                        .as("Expected indexOf(99) to return -1, but got %d", list.indexOf(99))
//                        .isEqualTo(-1);
//            }
//
//            @Test
//            @DisplayName("returns -1 on empty list")
//            void returnsMinusOneOnEmptyList() {
//                assertThat(list.indexOf(42))
//                        .as("Expected indexOf(42) on empty list to return -1, but got %d", list.indexOf(42))
//                        .isEqualTo(-1);
//            }
//
//            @Test
//            @DisplayName("duplicates: returns first index")
//            void returnsFirstIndexWhenDuplicatesExist() {
//                list.addLast(1);
//                list.addLast(2);
//                list.addLast(1);
//                assertThat(list.indexOf(1))
//                        .as("Expected indexOf(1) to return 0 (first occurrence), but got %d", list.indexOf(1))
//                        .isEqualTo(0);
//            }
//
//            @Test
//            @DisplayName("finds null element")
//            void findsNullElement() {
//                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
//                stringList.addLast("a");
//                stringList.addLast(null);
//                stringList.addLast("b");
//                assertThat(stringList.indexOf(null))
//                        .as("Expected indexOf(null) to return 1, but got %d", stringList.indexOf(null))
//                        .isEqualTo(1);
//            }
//
//            @Test
//            @DisplayName("correct index after multiple removals")
//            void indexOfAfterMultipleRemoves() {
//                list.addLast(1);
//                list.addLast(2);
//                list.addLast(3);
//                list.addLast(4);
//                list.deleteFirst(); // list: 2, 3, 4
//                list.deleteLast();  // list: 2, 3
//
//                assertThat(list.indexOf(2))
//                        .as("Expected indexOf(2) to be 0 after removes, but got %d", list.indexOf(2))
//                        .isEqualTo(0);
//                assertThat(list.indexOf(3))
//                        .as("Expected indexOf(3) to be 1 after removes, but got %d", list.indexOf(3))
//                        .isEqualTo(1);
//            }
//        }
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

                // Set at 0
                list.set(0, 99);
                assertThat(list.get(0))
                        .as("Expected get(0) to return 99 after set(0, 99), but got %s", list.get(0))
                        .isEqualTo(99);

                // Set at middle
                list.set(1, 88);
                assertThat(list.get(1))
                        .as("Expected get(1) to return 88 after set(1, 88), but got %s", list.get(1))
                        .isEqualTo(88);

                // Set at last
                list.set(2, 77);
                assertThat(list.get(2))
                        .as("Expected get(2) to return 77 after set(2, 77), but got %s", list.get(2))
                        .isEqualTo(77);
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
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
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
            @DisplayName("removes and returns first element")
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
                SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
                stringList.addFirst(null);
                String removed = stringList.deleteFirst();
                assertThat(removed)
                        .as("Expected deleteFirst() to return null, but got %s", removed)
                        .isNull();
            }
        }

        @Nested
        @DisplayName("deleteLast()")
        class DeleteLastTests {
            @Test
            @DisplayName("removes and returns last element")
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
                // List: 1 -> 2 -> 3
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
                assertThat(list.size()).as("Size should be 1000").isEqualTo(1000);

                for (int i = 0; i < 500; i++) list.deleteFirst();
                assertThat(list.size()).as("Size should be 500").isEqualTo(500);
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
            @DisplayName("large list formatting")
            void largeListFormatting() {
                for (int i = 0; i < 5; i++) list.addLast(i);

                String str = list.toString();
                assertThat(str).as("Should start with [").startsWith("[");
                assertThat(str).as("Should end with ]").endsWith("]");
                assertThat(str).as("Should contain proper commas").contains(", ");
            }
        }
    }
}

