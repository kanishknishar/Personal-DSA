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

    @Nested
    @DisplayName("size()")
    class SizeTests {

        @Test
        @DisplayName("returns 0 for newly created list")
        void returnsZeroForNewList() {
            assertThat(list.size())
                    .as("Expected size of new list to be 0, but got %d", list.size())
                    .isEqualTo(0);
        }

        @Test
        @DisplayName("returns correct size after adding one element")
        void returnsOneAfterAddingOneElement() {
            list.addFirst(1);

            assertThat(list.size())
                    .as("Expected size to be 1 after adding one element, but got %d", list.size())
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("returns correct size after adding multiple elements")
        void returnsCorrectSizeAfterMultipleAdds() {
            list.addFirst(1);
            list.addFirst(2);
            list.addFirst(3);

            assertThat(list.size())
                    .as("Expected size to be 3 after adding three elements, but got %d", list.size())
                    .isEqualTo(3);
        }

        @Test
        @DisplayName("returns correct size after adding and removing elements")
        void returnsCorrectSizeAfterAddAndRemove() {
            list.addFirst(1);
            list.addFirst(2);
            list.removeFirst();

            assertThat(list.size())
                    .as("Expected size to be 1 after add(2) then remove(1), but got %d", list.size())
                    .isEqualTo(1);
        }

    }

    @Nested
    @DisplayName("isEmpty()")
    class IsEmptyTests {

        @Test
        @DisplayName("returns true for newly created list")
        void returnsTrueForNewList() {
            assertThat(list.isEmpty())
                    .as("Expected isEmpty() to return true for new list, but got false")
                    .isTrue();
        }

        @Test
        @DisplayName("returns false after adding element")
        void returnsFalseAfterAddingElement() {
            list.addFirst(1);

            assertThat(list.isEmpty())
                    .as("Expected isEmpty() to return false after adding element, but got true")
                    .isFalse();
        }

        @Test
        @DisplayName("returns true after removing all elements")
        void returnsTrueAfterRemovingAllElements() {
            list.addFirst(1);
            list.removeFirst();

            assertThat(list.isEmpty())
                    .as("Expected isEmpty() to return true after removing all elements, but got false")
                    .isTrue();
        }

    }

    @Nested
    @DisplayName("addFirst()")
    class AddFirstTests {

        @Test
        @DisplayName("adds element to empty list")
        void addsElementToEmptyList() {
            list.addFirst(42);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to return 42 after addFirst(42), but got %s", list.getFirst())
                    .isEqualTo(42);
        }

        @Test
        @DisplayName("adds element at head of non-empty list")
        void addsElementAtHead() {
            list.addFirst(1);
            list.addFirst(2);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to return 2 after addFirst(2), but got %s", list.getFirst())
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("maintains correct order after multiple addFirst calls")
        void maintainsCorrectOrder() {
            list.addFirst(1);
            list.addFirst(2);
            list.addFirst(3);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to be 3 after multiple addFirst")
                    .isEqualTo(3);
            assertThat(list.size())
                    .as("Expected size to be 3")
                    .isEqualTo(3);
        }

        @Test
        @DisplayName("handles null value")
        void handlesNullValue() {
            SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
            stringList.addFirst(null);

            assertThat(stringList.getFirst())
                    .as("Expected getFirst() to return null after addFirst(null), but got %s", stringList.getFirst())
                    .isNull();
        }

        @Test
        @DisplayName("increments size correctly")
        void incrementsSizeCorrectly() {
            list.addFirst(1);

            assertThat(list.size())
                    .as("Expected size to be 1 after addFirst(1), but got %d", list.size())
                    .isEqualTo(1);
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
                    .as("Expected getLast() to return 42 after addLast(42), but got %s", list.getLast())
                    .isEqualTo(42);
        }

        @Test
        @DisplayName("adds element at tail of non-empty list")
        void addsElementAtTail() {
            list.addLast(1);
            list.addLast(2);

            assertThat(list.getLast())
                    .as("Expected getLast() to return 2 after addLast(2), but got %s", list.getLast())
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("maintains correct order after multiple addLast calls")
        void maintainsCorrectOrder() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to be 1 after multiple addLast")
                    .isEqualTo(1);
            assertThat(list.getLast())
                    .as("Expected getLast() to be 3 after multiple addLast")
                    .isEqualTo(3);
        }

        @Test
        @DisplayName("handles null value")
        void handlesNullValue() {
            SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
            stringList.addLast(null);

            assertThat(stringList.getLast())
                    .as("Expected getLast() to return null after addLast(null), but got %s", stringList.getLast())
                    .isNull();
        }

        @Test
        @DisplayName("getFirst equals getLast when list has single element")
        void getFirstEqualsGetLastForSingleElement() {
            list.addLast(42);

            assertThat(list.getFirst())
                    .as("Expected getFirst() and getLast() to be equal for single element list")
                    .isEqualTo(list.getLast());
        }
    }

    @Nested
    @DisplayName("insertAt()")
    class InsertAtTests {

        @Test
        @DisplayName("inserts at index 0 in empty list")
        void insertsAtZeroInEmptyList() {
            list.insertAt(0, 42);

            assertThat(list.get(0))
                    .as("Expected get(0) to return 42 after insertAt(0, 42), but got %s", list.get(0))
                    .isEqualTo(42);
        }

        @Test
        @DisplayName("inserts at index 0 in non-empty list")
        void insertsAtZeroInNonEmptyList() {
            list.addLast(1);
            list.addLast(2);
            list.insertAt(0, 0);

            assertThat(list.size()).isEqualTo(3);
            assertThat(list.get(0)).isEqualTo(0);
            assertThat(list.get(1)).isEqualTo(1);
            assertThat(list.get(2)).isEqualTo(2);
        }

        @Test
        @DisplayName("inserts at middle index")
        void insertsAtMiddleIndex() {
            list.addLast(1);
            list.addLast(3);
            list.insertAt(1, 2);

            assertThat(list.size()).isEqualTo(3);
            assertThat(list.get(0)).isEqualTo(1);
            assertThat(list.get(1)).isEqualTo(2);
            assertThat(list.get(2)).isEqualTo(3);
        }

        @Test
        @DisplayName("inserts at end (index equals size)")
        void insertsAtEnd() {
            list.addLast(1);
            list.addLast(2);
            list.insertAt(2, 3);

            assertThat(list.size()).isEqualTo(3);
            assertThat(list.get(0)).isEqualTo(1);
            assertThat(list.get(1)).isEqualTo(2);
            assertThat(list.get(2)).isEqualTo(3);
        }

        @Test
        @DisplayName("throws exception for negative index")
        void throwsForNegativeIndex() {
            assertThatThrownBy(() -> list.insertAt(-1, 42))
                    .as("Expected insertAt(-1, 42) to throw IndexOutOfBoundsException")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception for index greater than size")
        void throwsForIndexGreaterThanSize() {
            list.addLast(1);

            assertThatThrownBy(() -> list.insertAt(5, 42))
                    .as("Expected insertAt(5, 42) to throw IndexOutOfBoundsException when size is 1")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("handles null value insertion")
        void handlesNullValueInsertion() {
            SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
            stringList.addLast("a");
            stringList.insertAt(1, null);

            assertThat(stringList.get(1))
                    .as("Expected get(1) to return null after insertAt(1, null), but got %s", stringList.get(1))
                    .isNull();
        }
    }

    @Nested
    @DisplayName("removeFirst()")
    class RemoveFirstTests {

        @Test
        @DisplayName("removes and returns first element")
        void removesAndReturnsFirstElement() {
            list.addLast(1);
            list.addLast(2);

            Integer removed = list.removeFirst();

            assertThat(removed)
                    .as("Expected removeFirst() to return 1, but got %s", removed)
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("updates head after removal")
        void updatesHeadAfterRemoval() {
            list.addLast(1);
            list.addLast(2);
            list.removeFirst();

            assertThat(list.getFirst())
                    .as("Expected getFirst() to return 2 after removeFirst(), but got %s", list.getFirst())
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("decrements size after removal")
        void decrementsSizeAfterRemoval() {
            list.addLast(1);
            list.addLast(2);
            list.removeFirst();

            assertThat(list.size())
                    .as("Expected size to be 1 after removeFirst(), but got %d", list.size())
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("throws exception on empty list")
        void throwsOnEmptyList() {
            assertThatThrownBy(() -> list.removeFirst())
                    .as("Expected removeFirst() on empty list to throw NoSuchElementException")
                    .isInstanceOf(java.util.NoSuchElementException.class);
        }

        @Test
        @DisplayName("makes list empty when removing last element")
        void makesListEmptyWhenRemovingLastElement() {
            list.addFirst(1);
            list.removeFirst();

            assertThat(list.isEmpty())
                    .as("Expected isEmpty() to return true after removing only element, but got false")
                    .isTrue();
        }

        @Test
        @DisplayName("can remove null element")
        void canRemoveNullElement() {
            SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
            stringList.addFirst(null);

            String removed = stringList.removeFirst();

            assertThat(removed)
                    .as("Expected removeFirst() to return null, but got %s", removed)
                    .isNull();
        }
    }

    @Nested
    @DisplayName("removeLast()")
    class RemoveLastTests {

        @Test
        @DisplayName("removes and returns last element")
        void removesAndReturnsLastElement() {
            list.addLast(1);
            list.addLast(2);

            Integer removed = list.removeLast();

            assertThat(removed)
                    .as("Expected removeLast() to return 2, but got %s", removed)
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("updates tail after removal")
        void updatesTailAfterRemoval() {
            list.addLast(1);
            list.addLast(2);
            list.removeLast();

            assertThat(list.getLast())
                    .as("Expected getLast() to return 1 after removeLast(), but got %s", list.getLast())
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("decrements size after removal")
        void decrementsSizeAfterRemoval() {
            list.addLast(1);
            list.addLast(2);
            list.removeLast();

            assertThat(list.size())
                    .as("Expected size to be 1 after removeLast(), but got %d", list.size())
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("throws exception on empty list")
        void throwsOnEmptyList() {
            assertThatThrownBy(() -> list.removeLast())
                    .as("Expected removeLast() on empty list to throw NoSuchElementException")
                    .isInstanceOf(java.util.NoSuchElementException.class);
        }

        @Test
        @DisplayName("makes list empty when removing last element")
        void makesListEmptyWhenRemovingLastElement() {
            list.addLast(1);
            list.removeLast();

            assertThat(list.isEmpty())
                    .as("Expected isEmpty() to return true after removing only element, but got false")
                    .isTrue();
        }

        @Test
        @DisplayName("works correctly for single element list")
        void worksForSingleElementList() {
            list.addFirst(42);

            Integer removed = list.removeLast();

            assertThat(removed)
                    .as("Expected removeLast() to return 42 for single element list, but got %s", removed)
                    .isEqualTo(42);
            assertThat(list.isEmpty())
                    .as("Expected list to be empty after removeLast() on single element, but isEmpty() returned false")
                    .isTrue();
        }
    }

    @Nested
    @DisplayName("removeAt()")
    class RemoveAtTests {

        @Test
        @DisplayName("removes element at index 0")
        void removesAtIndexZero() {
            list.addLast(1);
            list.addLast(2);

            Integer removed = list.removeAt(0);

            assertThat(removed)
                    .as("Expected removeAt(0) to return 1, but got %s", removed)
                    .isEqualTo(1);
            assertThat(list.size()).isEqualTo(1);
            assertThat(list.get(0)).isEqualTo(2);
        }

        @Test
        @DisplayName("removes element at middle index")
        void removesAtMiddleIndex() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            Integer removed = list.removeAt(1);

            assertThat(removed)
                    .as("Expected removeAt(1) to return 2, but got %s", removed)
                    .isEqualTo(2);
            assertThat(list.size()).isEqualTo(2);
            assertThat(list.get(0)).isEqualTo(1);
            assertThat(list.get(1)).isEqualTo(3);
        }

        @Test
        @DisplayName("removes element at last index")
        void removesAtLastIndex() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            Integer removed = list.removeAt(2);

            assertThat(removed)
                    .as("Expected removeAt(2) to return 3, but got %s", removed)
                    .isEqualTo(3);
            assertThat(list.size()).isEqualTo(2);
            assertThat(list.get(0)).isEqualTo(1);
            assertThat(list.get(1)).isEqualTo(2);
        }

        @Test
        @DisplayName("throws exception for negative index")
        void throwsForNegativeIndex() {
            list.addLast(1);

            assertThatThrownBy(() -> list.removeAt(-1))
                    .as("Expected removeAt(-1) to throw IndexOutOfBoundsException")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception for index equals size")
        void throwsForIndexEqualsSize() {
            list.addLast(1);

            assertThatThrownBy(() -> list.removeAt(1))
                    .as("Expected removeAt(1) to throw IndexOutOfBoundsException when size is 1")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception for index greater than size")
        void throwsForIndexGreaterThanSize() {
            list.addLast(1);

            assertThatThrownBy(() -> list.removeAt(5))
                    .as("Expected removeAt(5) to throw IndexOutOfBoundsException when size is 1")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception on empty list")
        void throwsOnEmptyList() {
            assertThatThrownBy(() -> list.removeAt(0))
                    .as("Expected removeAt(0) on empty list to throw IndexOutOfBoundsException")
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
        @DisplayName("throws exception on empty list")
        void throwsOnEmptyList() {
            assertThatThrownBy(() -> list.getFirst())
                    .as("Expected getFirst() on empty list to throw NoSuchElementException")
                    .isInstanceOf(java.util.NoSuchElementException.class);
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
                    .as("Expected getLast() on empty list to throw NoSuchElementException")
                    .isInstanceOf(java.util.NoSuchElementException.class);
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

    @Nested
    @DisplayName("get(index)")
    class GetByIndexTests {

        @Test
        @DisplayName("returns element at index 0")
        void returnsElementAtIndexZero() {
            list.addLast(1);
            list.addLast(2);

            assertThat(list.get(0))
                    .as("Expected get(0) to return 1, but got %s", list.get(0))
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("returns element at middle index")
        void returnsElementAtMiddleIndex() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertThat(list.get(1))
                    .as("Expected get(1) to return 2, but got %s", list.get(1))
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("returns element at last index")
        void returnsElementAtLastIndex() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertThat(list.get(2))
                    .as("Expected get(2) to return 3, but got %s", list.get(2))
                    .isEqualTo(3);
        }

        @Test
        @DisplayName("throws exception for negative index")
        void throwsForNegativeIndex() {
            list.addLast(1);

            assertThatThrownBy(() -> list.get(-1))
                    .as("Expected get(-1) to throw IndexOutOfBoundsException")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception for index equals size")
        void throwsForIndexEqualsSize() {
            list.addLast(1);

            assertThatThrownBy(() -> list.get(1))
                    .as("Expected get(1) to throw IndexOutOfBoundsException when size is 1")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception for index greater than size")
        void throwsForIndexGreaterThanSize() {
            list.addLast(1);

            assertThatThrownBy(() -> list.get(100))
                    .as("Expected get(100) to throw IndexOutOfBoundsException when size is 1")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception on empty list")
        void throwsOnEmptyList() {
            assertThatThrownBy(() -> list.get(0))
                    .as("Expected get(0) on empty list to throw IndexOutOfBoundsException")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }
    }

    @Nested
    @DisplayName("set(index, value)")
    class SetTests {

        @Test
        @DisplayName("sets element at index 0")
        void setsElementAtIndexZero() {
            list.addLast(1);
            list.addLast(2);

            list.set(0, 99);

            assertThat(list.get(0))
                    .as("Expected get(0) to return 99 after set(0, 99), but got %s", list.get(0))
                    .isEqualTo(99);
        }

        @Test
        @DisplayName("sets element at middle index")
        void setsElementAtMiddleIndex() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            list.set(1, 99);

            assertThat(list.get(1))
                    .as("Expected get(1) to return 99 after set(1, 99), but got %s", list.get(1))
                    .isEqualTo(99);
        }

        @Test
        @DisplayName("sets element at last index")
        void setsElementAtLastIndex() {
            list.addLast(1);
            list.addLast(2);

            list.set(1, 99);

            assertThat(list.get(1))
                    .as("Expected get(1) to return 99 after set(1, 99), but got %s", list.get(1))
                    .isEqualTo(99);
        }

        @Test
        @DisplayName("does not change size")
        void doesNotChangeSize() {
            list.addLast(1);
            list.addLast(2);

            list.set(0, 99);

            assertThat(list.size())
                    .as("Expected size to remain 2 after set(), but got %d", list.size())
                    .isEqualTo(2);
        }

        @Test
        @DisplayName("throws exception for negative index")
        void throwsForNegativeIndex() {
            list.addLast(1);

            assertThatThrownBy(() -> list.set(-1, 99))
                    .as("Expected set(-1, 99) to throw IndexOutOfBoundsException")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception for index equals size")
        void throwsForIndexEqualsSize() {
            list.addLast(1);

            assertThatThrownBy(() -> list.set(1, 99))
                    .as("Expected set(1, 99) to throw IndexOutOfBoundsException when size is 1")
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        @DisplayName("throws exception on empty list")
        void throwsOnEmptyList() {
            assertThatThrownBy(() -> list.set(0, 99))
                    .as("Expected set(0, 99) on empty list to throw IndexOutOfBoundsException")
                    .isInstanceOf(IndexOutOfBoundsException.class);
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
            list.addLast(2);

            assertThat(list.indexOf(99))
                    .as("Expected indexOf(99) to return -1, but got %d", list.indexOf(99))
                    .isEqualTo(-1);
        }

        @Test
        @DisplayName("returns -1 on empty list")
        void returnsMinusOneOnEmptyList() {
            assertThat(list.indexOf(1))
                    .as("Expected indexOf(1) on empty list to return -1, but got %d", list.indexOf(1))
                    .isEqualTo(-1);
        }

        @Test
        @DisplayName("returns first index when duplicates exist")
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
            SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
            stringList.addLast("a");
            stringList.addLast(null);
            stringList.addLast("b");

            assertThat(stringList.indexOf(null))
                    .as("Expected indexOf(null) to return 1, but got %d", stringList.indexOf(null))
                    .isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("toString()")
    class ToStringTests {

        @Test
        @DisplayName("returns proper representation for empty list")
        void returnsProperRepresentationForEmptyList() {
            assertThat(list.toString())
                    .as("Expected toString() for empty list to be '[]', but got '%s'", list.toString())
                    .isEqualTo("[]");
        }

        @Test
        @DisplayName("returns proper representation for single element")
        void returnsProperRepresentationForSingleElement() {
            list.addFirst(1);

            assertThat(list.toString())
                    .as("Expected toString() for [1] to be '[1]', but got '%s'", list.toString())
                    .isEqualTo("[1]");
        }

        @Test
        @DisplayName("returns proper representation for multiple elements")
        void returnsProperRepresentationForMultipleElements() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertThat(list.toString())
                    .as("Expected toString() for [1, 2, 3] to be '[1, 2, 3]', but got '%s'", list.toString())
                    .isEqualTo("[1, 2, 3]");
        }

        @Test
        @DisplayName("handles null elements in toString")
        void handlesNullElements() {
            SinglyLinkedList<String> stringList = new SinglyLinkedList<>();
            stringList.addLast("a");
            stringList.addLast(null);
            stringList.addLast("b");

            assertThat(stringList.toString())
                    .as("Expected toString() to be '[a, null, b]', but got '%s'", stringList.toString())
                    .isEqualTo("[a, null, b]");
        }
    }

    @Nested
    @DisplayName("Edge Cases and Stress Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("handles Integer.MAX_VALUE as element")
        void handlesMaxIntegerValue() {
            list.addFirst(Integer.MAX_VALUE);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to return Integer.MAX_VALUE, but got %s", list.getFirst())
                    .isEqualTo(Integer.MAX_VALUE);
        }

        @Test
        @DisplayName("handles Integer.MIN_VALUE as element")
        void handlesMinIntegerValue() {
            list.addFirst(Integer.MIN_VALUE);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to return Integer.MIN_VALUE, but got %s", list.getFirst())
                    .isEqualTo(Integer.MIN_VALUE);
        }

        @Test
        @DisplayName("handles many sequential operations")
        void handlesManySequentialOperations() {
            for (int i = 0; i < 1000; i++) {
                list.addLast(i);
            }

            assertThat(list.size())
                    .as("Expected size to be 1000 after adding 1000 elements, but got %d", list.size())
                    .isEqualTo(1000);

            for (int i = 0; i < 500; i++) {
                list.removeFirst();
            }

            assertThat(list.size())
                    .as("Expected size to be 500 after removing 500 elements, but got %d", list.size())
                    .isEqualTo(500);

            assertThat(list.getFirst())
                    .as("Expected getFirst() to return 500 after operations, but got %s", list.getFirst())
                    .isEqualTo(500);
        }


        @Test
        @DisplayName("handles duplicate elements correctly")
        void handlesDuplicateElements() {
            list.addLast(1);
            list.addLast(1);
            list.addLast(1);

            assertThat(list.size())
                    .as("Expected size to be 3 with duplicate elements, but got %d", list.size())
                    .isEqualTo(3);
            assertThat(list.indexOf(1))
                    .as("Expected indexOf(1) to return 0 (first occurrence), but got %d", list.indexOf(1))
                    .isEqualTo(0);
        }


        @Test
        @DisplayName("remove all elements one by one from front")
        void removeAllFromFront() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            list.removeFirst();
            list.removeFirst();
            list.removeFirst();

            assertThat(list.isEmpty())
                    .as("Expected list to be empty after removing all elements from front, but isEmpty() returned false")
                    .isTrue();
        }

        @Test
        @DisplayName("remove all elements one by one from back")
        void removeAllFromBack() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            list.removeLast();
            list.removeLast();
            list.removeLast();

            assertThat(list.isEmpty())
                    .as("Expected list to be empty after removing all elements from back, but isEmpty() returned false")
                    .isTrue();
        }


        @Test
        @DisplayName("removes from single element list multiple times throws")
        void removesFromSingleElementMultipleTimes() {
            list.addFirst(1);
            list.removeFirst();

            assertThatThrownBy(() -> list.removeFirst())
                    .as("Second removeFirst on empty list should throw")
                    .isInstanceOf(java.util.NoSuchElementException.class);
            assertThatThrownBy(() -> list.removeLast())
                    .as("removeLast on empty list should throw")
                    .isInstanceOf(java.util.NoSuchElementException.class);
        }

        @Test
        @DisplayName("indexOf returns correct index after multiple removes")
        void indexOfAfterMultipleRemoves() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);
            list.addLast(4);
            list.removeFirst();
            list.removeLast();

            assertThat(list.indexOf(2))
                    .as("Expected indexOf(2) to be 0 after removes, but got %d", list.indexOf(2))
                    .isEqualTo(0);
            assertThat(list.indexOf(3))
                    .as("Expected indexOf(3) to be 1 after removes, but got %d", list.indexOf(3))
                    .isEqualTo(1);
        }


        @Test
        @DisplayName("boundary test with Integer.MAX_VALUE and MIN_VALUE")
        void boundaryTestWithIntegerLimits() {
            list.addFirst(Integer.MAX_VALUE);
            list.addFirst(Integer.MIN_VALUE);
            list.addLast(0);

            assertThat(list.getFirst())
                    .as("First should be MIN_VALUE")
                    .isEqualTo(Integer.MIN_VALUE);
            assertThat(list.get(1))
                    .as("Middle should be MAX_VALUE")
                    .isEqualTo(Integer.MAX_VALUE);
            assertThat(list.getLast())
                    .as("Last should be 0")
                    .isZero();
        }


        @Test
        @DisplayName("toString on large list is properly formatted")
        void toStringOnLargeList() {
            for (int i = 0; i < 5; i++) {
                list.addLast(i);
            }

            String str = list.toString();

            assertThat(str)
                    .as("toString should start with [").startsWith("[");
            assertThat(str)
                    .as("toString should end with ]").endsWith("]");
            assertThat(str)
                    .as("toString should contain commas").contains(",");
        }
    }
}
