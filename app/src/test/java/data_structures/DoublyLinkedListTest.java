//package data_structures;
//
//import java.util.Iterator;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import data_structures.singly_linked_list.DoublyLinkedList;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInfo;
//
//@DisplayName("DoublyLinkedList")
//class DoublyLinkedListTest {
//
//    private DoublyLinkedList<Integer> list;
//    private static int totalTests = 0;
//    private static int passedTests = 0;
//    private static int failedTests = 0;
//
//    @BeforeEach
//    void setUp(TestInfo testInfo) {
//        list = new DoublyLinkedList<>();
//        totalTests++;
//        System.out.printf("[TEST %d] Running: %s%n", totalTests, testInfo.getDisplayName());
//    }
//
//    @AfterAll
//    static void printTestSummary() {
//        System.out.println("\n" + "=".repeat(80));
//        System.out.println("DOUBLY LINKED LIST TEST SUMMARY");
//        System.out.println("=".repeat(80));
//        System.out.printf("Total Tests:   %d%n", totalTests);
//        System.out.printf("Passed:        %d (%.1f%%)%n", passedTests, (passedTests * 100.0 / totalTests));
//        System.out.printf("Failed:        %d (%.1f%%)%n", failedTests, (failedTests * 100.0 / totalTests));
//        System.out.println("=".repeat(80));
//    }
//
//    @Nested
//    @DisplayName("size() and isEmpty()")
//    class SizeAndEmptyTests {
//
//        @Test
//        @DisplayName("new list reports size 0 and isEmpty true")
//        void newListIsEmpty() {
//            assertThat(list.size()).as("Expected size 0 for new list but got %d", list.size()).isZero();
//            assertThat(list.isEmpty()).as("Expected isEmpty true for new list").isTrue();
//        }
//
//        @Test
//        @DisplayName("size updates after adds and removes")
//        void sizeUpdatesAfterOps() {
//            list.addFirst(1);
//            list.addLast(2);
//            list.addLast(3);
//            assertThat(list.size()).as("Expected size 3 after three adds but got %d", list.size()).isEqualTo(3);
//
//            list.deleteFirst();
//            list.deleteLast();
//            assertThat(list.size()).as("Expected size 1 after two removals but got %d", list.size()).isEqualTo(1);
//        }
//    }
//
//    @Nested
//    @DisplayName("addFirst() and addLast()")
//    class AddTests {
//
//        @Test
//        @DisplayName("addFirst prepends elements")
//        void addFirstPrepends() {
//            list.addFirst(2);
//            list.addFirst(1);
//            assertThat(list.getFirst()).as("Head should be 1 after prepend").isEqualTo(1);
//            assertThat(list.getLast()).as("Tail should be 2 after prepend").isEqualTo(2);
//        }
//
//        @Test
//        @DisplayName("addLast appends elements")
//        void addLastAppends() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            assertThat(list.getFirst()).as("Head should be 1 after append").isEqualTo(1);
//            assertThat(list.getLast()).as("Tail should be 3 after append").isEqualTo(3);
//        }
//
//        @Test
//        @DisplayName("supports null elements at head and tail")
//        void supportsNullElements() {
//            DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
//            stringList.addFirst(null);
//            stringList.addLast("a");
//            assertThat(stringList.getFirst()).as("First element should be null").isNull();
//            assertThat(stringList.getLast()).as("Last element should be 'a'").isEqualTo("a");
//        }
//    }
//
//    @Nested
//    @DisplayName("addAt()")
//    class addAtTests {
//
//        @Test
//        @DisplayName("inserts at index 0 into empty list")
//        void insertsAtZeroIntoEmpty() {
//            list.addAt(0, 10);
//            assertThat(list.getFirst()).isEqualTo(10);
//            assertThat(list.size()).isEqualTo(1);
//        }
//
//        @Test
//        @DisplayName("inserts at head, middle, and tail")
//        void insertsVariousPositions() {
//            list.addLast(1);
//            list.addLast(3);
//            list.addAt(1, 2);
//            list.addAt(0, 0);
//            list.addAt(4, 4);
//            assertThat(list.size()).isEqualTo(5);
//            assertThat(list.get(0)).isEqualTo(0);
//            assertThat(list.get(1)).isEqualTo(1);
//            assertThat(list.get(2)).isEqualTo(2);
//            assertThat(list.get(3)).isEqualTo(3);
//            assertThat(list.get(4)).isEqualTo(4);
//        }
//
//        @Test
//        @DisplayName("throws for negative index")
//        void throwsForNegativeIndex() {
//            assertThatThrownBy(() -> list.addAt(-1, 5)).as("addAt(-1) should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//        }
//
//        @Test
//        @DisplayName("throws for index greater than size")
//        void throwsForIndexGreaterThanSize() {
//            list.addLast(1);
//            assertThatThrownBy(() -> list.addAt(3, 5)).as("addAt(3) when size 1 should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//        }
//    }
//
//    @Nested
//    @DisplayName("getFirst()/getLast()/get(index)")
//    class GetTests {
//
//        @Test
//        @DisplayName("getFirst and getLast on populated list")
//        void getFirstAndLast() {
//            list.addLast(1);
//            list.addLast(2);
//            assertThat(list.getFirst()).as("Expected head 1 but got %s", list.getFirst()).isEqualTo(1);
//            assertThat(list.getLast()).as("Expected tail 2 but got %s", list.getLast()).isEqualTo(2);
//        }
//
//        @Test
//        @DisplayName("getFirst throws on empty list")
//        void getFirstThrowsOnEmpty() {
//            assertThatThrownBy(() -> list.getFirst()).as("getFirst on empty should throw NoSuchElementException").isInstanceOf(java.util.NoSuchElementException.class);
//        }
//
//        @Test
//        @DisplayName("getLast throws on empty list")
//        void getLastThrowsOnEmpty() {
//            assertThatThrownBy(() -> list.getLast()).as("getLast on empty should throw NoSuchElementException").isInstanceOf(java.util.NoSuchElementException.class);
//        }
//
//        @Test
//        @DisplayName("get by index valid positions")
//        void getByIndexValid() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            assertThat(list.get(0)).as("Expected index 0 to be 1 but got %s", list.get(0)).isEqualTo(1);
//            assertThat(list.get(1)).as("Expected index 1 to be 2 but got %s", list.get(1)).isEqualTo(2);
//            assertThat(list.get(2)).as("Expected index 2 to be 3 but got %s", list.get(2)).isEqualTo(3);
//        }
//
//        @Test
//        @DisplayName("get throws for invalid indices")
//        void getThrowsForInvalidIndices() {
//            list.addLast(1);
//            assertThatThrownBy(() -> list.get(-1)).as("get(-1) should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//            assertThatThrownBy(() -> list.get(2)).as("get(2) with size 1 should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//        }
//    }
//
//    @Nested
//    @DisplayName("set(index, value)")
//    class SetTests {
//
//        @Test
//        @DisplayName("replaces values at head, middle, tail without changing size")
//        void replacesValues() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            list.set(0, 10);
//            list.set(1, 20);
//            list.set(2, 30);
//            assertThat(list.get(0)).isEqualTo(10);
//            assertThat(list.get(1)).isEqualTo(20);
//            assertThat(list.get(2)).isEqualTo(30);
//            assertThat(list.size()).as("Size should remain 3 after set operations").isEqualTo(3);
//        }
//
//        @Test
//        @DisplayName("allows setting null value")
//        void allowsSettingNull() {
//            DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
//            stringList.addLast("a");
//            stringList.set(0, null);
//            assertThat(stringList.get(0)).as("Expected null at index 0 after set").isNull();
//        }
//
//        @Test
//        @DisplayName("throws for invalid indices")
//        void throwsForInvalidIndices() {
//            list.addLast(1);
//            assertThatThrownBy(() -> list.set(-1, 5)).as("set(-1) should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//            assertThatThrownBy(() -> list.set(2, 5)).as("set(2) with size 1 should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//        }
//    }
//
//    @Nested
//    @DisplayName("deleteFirst()/deleteLast()/deleteAt()")
//    class RemoveTests {
//
//        @Test
//        @DisplayName("deleteFirst removes head and returns value")
//        void deleteFirstRemovesHead() {
//            list.addLast(1);
//            list.addLast(2);
//            Integer removed = list.deleteFirst();
//            assertThat(removed).as("Expected deleteFirst to return 1 but got %s", removed).isEqualTo(1);
//            assertThat(list.getFirst()).as("New head should be 2 after removing first").isEqualTo(2);
//            assertThat(list.size()).as("Size should be 1 after deleteFirst").isEqualTo(1);
//        }
//
//        @Test
//        @DisplayName("deleteLast removes tail and returns value")
//        void deleteLastRemovesTail() {
//            list.addLast(1);
//            list.addLast(2);
//            Integer removed = list.deleteLast();
//            assertThat(removed).as("Expected deleteLast to return 2 but got %s", removed).isEqualTo(2);
//            assertThat(list.getLast()).as("New tail should be 1 after removing last").isEqualTo(1);
//            assertThat(list.size()).as("Size should be 1 after deleteLast").isEqualTo(1);
//        }
//
//        @Test
//        @DisplayName("deleteAt removes value at index")
//        void deleteAtRemovesValue() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            Integer removed = list.deleteAt(1);
//            assertThat(removed).as("Expected deleteAt(1) to return 2 but got %s", removed).isEqualTo(2);
//            assertThat(list.size()).isEqualTo(2);
//            assertThat(list.get(0)).isEqualTo(1);
//            assertThat(list.get(1)).isEqualTo(3);
//        }
//
//        @Test
//        @DisplayName("remove operations throw on empty list")
//        void removeThrowsOnEmpty() {
//            assertThatThrownBy(() -> list.deleteFirst()).as("deleteFirst on empty should throw NoSuchElementException").isInstanceOf(java.util.NoSuchElementException.class);
//            assertThatThrownBy(() -> list.deleteLast()).as("deleteLast on empty should throw NoSuchElementException").isInstanceOf(java.util.NoSuchElementException.class);
//            assertThatThrownBy(() -> list.deleteAt(0)).as("deleteAt on empty should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//        }
//
//        @Test
//        @DisplayName("deleteAt invalid indices throw")
//        void deleteAtInvalidIndicesThrow() {
//            list.addLast(1);
//            assertThatThrownBy(() -> list.deleteAt(-1)).as("deleteAt(-1) should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//            assertThatThrownBy(() -> list.deleteAt(2)).as("deleteAt(2) with size 1 should throw IndexOutOfBoundsException").isInstanceOf(IndexOutOfBoundsException.class);
//        }
//
//        @Test
//        @DisplayName("deleteFirst/Last make list empty when single element")
//        void removeMakesListEmpty() {
//            list.addLast(42);
//            Integer first = list.deleteFirst();
//            assertThat(first).as("deleteFirst should return 42 for single element").isEqualTo(42);
//            assertThat(list.isEmpty()).as("List should be empty after removing only element").isTrue();
//
//            list.addLast(99);
//            Integer last = list.deleteLast();
//            assertThat(last).as("deleteLast should return 99 for single element").isEqualTo(99);
//            assertThat(list.isEmpty()).as("List should be empty after removing only element").isTrue();
//        }
//    }
//
//    @Nested
//    @DisplayName("remove(value)")
//    class RemoveByValueTests {
//
//        @Test
//        @DisplayName("removes first occurrence and returns true")
//        void removesFirstOccurrence() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(1);
//            boolean removed = list.delete(1);
//            assertThat(removed).as("remove(1) should return true when element exists").isTrue();
//            assertThat(list.size()).isEqualTo(2);
//            assertThat(list.get(0)).isEqualTo(2);
//            assertThat(list.get(1)).isEqualTo(1);
//        }
//
//        @Test
//        @DisplayName("returns false when value not found")
//        void returnsFalseWhenNotFound() {
//            list.addLast(1);
//            boolean removed = list.delete(99);
//            assertThat(removed).as("remove on missing element should return false").isFalse();
//        }
//
//        @Test
//        @DisplayName("removes null value if present")
//        void removesNullValue() {
//            DoublyLinkedList<String> stringList = new DoublyLinkedList<>();
//            stringList.addLast("a");
//            stringList.addLast(null);
//            stringList.addLast("b");
//            boolean removed = stringList.delete(null);
//            assertThat(removed).as("remove(null) should return true when present").isTrue();
//            assertThat(stringList.size()).isEqualTo(2);
//            assertThat(stringList.get(0)).isEqualTo("a");
//            assertThat(stringList.get(1)).isEqualTo("b");
//        }
//    }
//
//    @Nested
//    @DisplayName("iterators")
//    class IteratorTests {
//
//        @Test
//        @DisplayName("forward iterator traverses head to tail")
//        void forwardIteratorTraverses() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            Iterator<Integer> it = list.iterator();
//            assertThat(it.hasNext()).as("Iterator should have first element").isTrue();
//            assertThat(it.next()).as("First next should be 1").isEqualTo(1);
//            assertThat(it.next()).as("Second next should be 2").isEqualTo(2);
//            assertThat(it.next()).as("Third next should be 3").isEqualTo(3);
//            assertThat(it.hasNext()).as("Iterator should be exhausted").isFalse();
//            assertThatThrownBy(it::next).as("Calling next past end should throw").isInstanceOf(java.util.NoSuchElementException.class);
//        }
//
//        @Test
//        @DisplayName("descending iterator traverses tail to head")
//        void descendingIteratorTraverses() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            Iterator<Integer> it = list.descendingIterator();
//            assertThat(it.next()).as("First descending next should be 3").isEqualTo(3);
//            assertThat(it.next()).as("Second descending next should be 2").isEqualTo(2);
//            assertThat(it.next()).as("Third descending next should be 1").isEqualTo(1);
//            assertThat(it.hasNext()).as("Descending iterator should be exhausted").isFalse();
//        }
//
//        @Test
//        @DisplayName("iterators on empty list")
//        void iteratorsOnEmptyList() {
//            Iterator<Integer> forward = list.iterator();
//            assertThat(forward.hasNext()).as("Empty list iterator should have no elements").isFalse();
//            assertThatThrownBy(forward::next).as("next on empty iterator should throw").isInstanceOf(java.util.NoSuchElementException.class);
//
//            Iterator<Integer> descending = list.descendingIterator();
//            assertThat(descending.hasNext()).as("Descending iterator on empty list should have no elements").isFalse();
//            assertThatThrownBy(descending::next).as("Descending next on empty iterator should throw").isInstanceOf(java.util.NoSuchElementException.class);
//        }
//    }
//
//    @Nested
//    @DisplayName("toString()")
//    class ToStringTests {
//
//        @Test
//        @DisplayName("formats empty list")
//        void formatsEmptyList() {
//            assertThat(list.toString()).as("toString for empty list should be [] but got %s", list.toString()).isEqualTo("[]");
//        }
//
//        @Test
//        @DisplayName("formats single element list")
//        void formatsSingleElementList() {
//            list.addLast(1);
//            assertThat(list.toString()).as("toString for [1] should be [1] but got %s", list.toString()).isEqualTo("[1]");
//        }
//
//        @Test
//        @DisplayName("formats multiple elements with nulls")
//        void formatsMultipleElements() {
//            list.addLast(1);
//            list.addLast(null);
//            list.addLast(3);
//            assertThat(list.toString()).as("toString should show [1, null, 3] but got %s", list.toString()).isEqualTo("[1, null, 3]");
//        }
//    }
//
//    @Nested
//    @DisplayName("robustness and stress")
//    class RobustnessTests {
//
//        @Test
//        @DisplayName("handles large number of sequential adds and removes")
//        void handlesLargeSequentialOperations() {
//            for (int i = 0; i < 1000; i++) {
//                list.addLast(i);
//            }
//            assertThat(list.size()).as("Expected size 1000 after bulk add but got %d", list.size()).isEqualTo(1000);
//
//            for (int i = 0; i < 500; i++) {
//                list.deleteFirst();
//            }
//            assertThat(list.getFirst()).as("Head after removing 500 should be 500 but got %s", list.getFirst()).isEqualTo(500);
//            assertThat(list.size()).as("Size should be 500 after removing 500 elements").isEqualTo(500);
//
//            for (int i = 0; i < 500; i++) {
//                list.deleteLast();
//            }
//            assertThat(list.isEmpty()).as("List should be empty after removing all elements").isTrue();
//        }
//
//        @Test
//        @DisplayName("interleaved insert and removal at various positions")
//        void interleavedOps() {
//            list.addAt(0, 1);
//            list.addAt(1, 3);
//            list.addAt(1, 2);
//            list.deleteAt(0);
//            list.deleteAt(1);
//            list.addAt(1, 4);
//            assertThat(list.size()).isEqualTo(2);
//            assertThat(list.get(0)).isEqualTo(2);
//            assertThat(list.get(1)).isEqualTo(4);
//        }
//
//        @Test
//        @DisplayName("add and remove at both ends alternately")
//        void alternateAddRemoveBothEnds() {
//            for (int i = 0; i < 10; i++) {
//                list.addFirst(i);
//                list.addLast(i + 100);
//            }
//
//            for (int i = 0; i < 5; i++) {
//                list.deleteFirst();
//                list.deleteLast();
//            }
//
//            assertThat(list.size())
//                .as("Expected size 10 after alternating operations")
//                .isEqualTo(10);
//        }
//
//        @Test
//        @DisplayName("bidirectional traversal consistency")
//        void bidirectionalTraversalConsistency() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//
//            Iterator<Integer> forward = list.iterator();
//            Iterator<Integer> backward = list.descendingIterator();
//
//            Integer[] forwardArray = new Integer[3];
//            Integer[] backwardArray = new Integer[3];
//
//            for (int i = 0; i < 3; i++) {
//                forwardArray[i] = forward.next();
//            }
//
//            for (int i = 0; i < 3; i++) {
//                backwardArray[i] = backward.next();
//            }
//
//            assertThat(forwardArray).as("Forward should be [1, 2, 3]").containsExactly(1, 2, 3);
//            assertThat(backwardArray).as("Backward should be [3, 2, 1]").containsExactly(3, 2, 1);
//        }
//
//        @Test
//        @DisplayName("indexOf and lastIndexOf with all identical elements")
//        void indexOfWithAllIdentical() {
//            list.addLast(5);
//            list.addLast(5);
//            list.addLast(5);
//
//            assertThat(list.indexOf(5)).as("indexOf should be 0").isEqualTo(0);
//            assertThat(list.lastIndexOf(5)).as("lastIndexOf should be 2").isEqualTo(2);
//        }
//
//
//
//        @Test
//        @DisplayName("iterator modification throws ConcurrentModificationException")
//        void iteratorDetectsModification() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//
//            Iterator<Integer> it = list.iterator();
//            it.next();
//
//            list.addLast(4);
//
//            assertThatThrownBy(() -> it.next())
//                .as("Iterator should detect concurrent modification")
//                .isInstanceOf(java.util.ConcurrentModificationException.class);
//        }
//
//        @Test
//        @DisplayName("multiple iterators can coexist without interference")
//        void multipleIteratorsCoexist() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//
//            Iterator<Integer> it1 = list.iterator();
//            Iterator<Integer> it2 = list.iterator();
//
//            assertThat(it1.next()).as("First iterator first next").isEqualTo(1);
//            assertThat(it2.next()).as("Second iterator first next").isEqualTo(1);
//            assertThat(it1.next()).as("First iterator second next").isEqualTo(2);
//            assertThat(it2.next()).as("Second iterator second next").isEqualTo(2);
//        }
//
//
//
//        @Test
//        @DisplayName("lastIndexOf returns different from indexOf with duplicates")
//        void lastIndexOfDiffersWithDuplicates() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            list.addLast(2);
//            list.addLast(1);
//
//            assertThat(list.indexOf(2)).as("First occurrence of 2").isEqualTo(1);
//            assertThat(list.lastIndexOf(2)).as("Last occurrence of 2").isEqualTo(3);
//            assertThat(list.indexOf(1)).as("First occurrence of 1").isEqualTo(0);
//            assertThat(list.lastIndexOf(1)).as("Last occurrence of 1").isEqualTo(4);
//        }
//
//
//
//        @Test
//        @DisplayName("get operations at boundaries after removals")
//        void getAtBoundariesAfterRemovals() {
//            for (int i = 0; i < 10; i++) {
//                list.addLast(i);
//            }
//
//            list.deleteFirst();
//            list.deleteFirst();
//            list.deleteLast();
//            list.deleteLast();
//
//            assertThat(list.getFirst()).as("First after removals").isEqualTo(2);
//            assertThat(list.getLast()).as("Last after removals").isEqualTo(7);
//            assertThat(list.get(0)).as("Index 0 after removals").isEqualTo(2);
//            assertThat(list.get(list.size() - 1)).as("Last index after removals").isEqualTo(7);
//        }
//
//        @Test
//        @DisplayName("set then get returns updated value immediately")
//        void setThenGetReturnsUpdated() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//
//            list.set(1, 99);
//
//            assertThat(list.get(1)).as("get after set should return new value").isEqualTo(99);
//        }
//
//
//
//
//
//
//
//        @Test
//        @DisplayName("descending iterator after list modifications")
//        void descendingIteratorAfterModifications() {
//            list.addLast(1);
//            list.addLast(2);
//            list.addLast(3);
//            list.deleteFirst();
//            list.addFirst(0);
//
//            Iterator<Integer> it = list.descendingIterator();
//
//            assertThat(it.next()).as("First descending").isEqualTo(3);
//            assertThat(it.next()).as("Second descending").isEqualTo(2);
//            assertThat(it.next()).as("Third descending").isEqualTo(0);
//            assertThat(it.hasNext()).as("Should be exhausted").isFalse();
//        }
//
//        @Test
//        @DisplayName("toString after complex operations")
//        void toStringAfterComplexOps() {
//            list.addLast(1);
//            list.addFirst(0);
//            list.addAt(1, 99);
//            list.delete(99);
//
//            String str = list.toString();
//
//            assertThat(str).as("toString should be properly formatted").matches("\\[.*\\]");
//        }
//
//        @Test
//        @DisplayName("size never goes negative")
//        void sizeNeverNegative() {
//            assertThat(list.size()).as("Empty list size").isGreaterThanOrEqualTo(0);
//
//            list.addLast(1);
//            assertThat(list.size()).as("After add").isGreaterThanOrEqualTo(0);
//
//            list.deleteFirst();
//            assertThat(list.size()).as("After remove").isGreaterThanOrEqualTo(0);
//        }
//
//        @Test
//        @DisplayName("boundary values Integer MAX and MIN")
//        void boundaryValuesIntegerMaxMin() {
//            list.addLast(Integer.MAX_VALUE);
//            list.addFirst(Integer.MIN_VALUE);
//            list.addAt(1, 0);
//
//            assertThat(list.get(0)).as("MIN_VALUE at head").isEqualTo(Integer.MIN_VALUE);
//            assertThat(list.get(1)).as("0 at middle").isZero();
//            assertThat(list.get(2)).as("MAX_VALUE at tail").isEqualTo(Integer.MAX_VALUE);
//        }
//    }
//}
