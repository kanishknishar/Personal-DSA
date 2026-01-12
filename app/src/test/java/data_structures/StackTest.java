//package data_structures;
//
//import data_structures.stack.Stack;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.DisplayName;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.NoSuchElementException;
//import java.util.Iterator;
//
//@DisplayName("Stack Tests")
//class StackTest {
//
//    private Stack<Integer> stack;
//
//    @BeforeEach
//    void setUp() {
//        stack = new Stack<>();
//    }
//
//    @Nested
//    @DisplayName("Basic Operations Tests")
//    class BasicOperationsTests {
//
//        @Test
//        @DisplayName("Should create empty stack")
//        void testNewStackIsEmpty() {
//            assertTrue(stack.isEmpty());
//            assertEquals(0, stack.size());
//        }
//
//        @Test
//        @DisplayName("Should push single element")
//        void testPushSingleElement() {
//            stack.push(1);
//            assertFalse(stack.isEmpty());
//            assertEquals(1, stack.size());
//            assertEquals(1, stack.peek());
//        }
//
//        @Test
//        @DisplayName("Should push multiple elements")
//        void testPushMultipleElements() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            assertEquals(3, stack.size());
//            assertEquals(3, stack.peek());
//        }
//
//        @Test
//        @DisplayName("Should pop single element")
//        void testPopSingleElement() {
//            stack.push(1);
//            int popped = stack.pop();
//            assertEquals(1, popped);
//            assertTrue(stack.isEmpty());
//            assertEquals(0, stack.size());
//        }
//
//        @Test
//        @DisplayName("Should pop in LIFO order")
//        void testPopLIFOOrder() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            assertEquals(3, stack.pop());
//            assertEquals(2, stack.pop());
//            assertEquals(1, stack.pop());
//        }
//
//        @Test
//        @DisplayName("Should peek without removing")
//        void testPeekDoesNotRemove() {
//            stack.push(1);
//            stack.push(2);
//            assertEquals(2, stack.peek());
//            assertEquals(2, stack.peek()); // Still there
//            assertEquals(2, stack.size());
//        }
//    }
//
//    @Nested
//    @DisplayName("Edge Cases Tests")
//    class EdgeCasesTests {
//
//        @Test
//        @DisplayName("Should throw exception when popping empty stack")
//        void testPopEmptyStack() {
//            assertThrows(NoSuchElementException.class, () -> stack.pop());
//        }
//
//        @Test
//        @DisplayName("Should throw exception when peeking empty stack")
//        void testPeekEmptyStack() {
//            assertThrows(NoSuchElementException.class, () -> stack.peek());
//        }
//
//        @Test
//        @DisplayName("Should handle push after pop to empty")
//        void testPushAfterPopToEmpty() {
//            stack.push(1);
//            stack.pop();
//            stack.push(2);
//            assertEquals(2, stack.peek());
//            assertEquals(1, stack.size());
//        }
//
//        @Test
//        @DisplayName("Should handle null values")
//        void testPushNullValue() {
//            stack.push(null);
//            assertFalse(stack.isEmpty());
//            assertEquals(1, stack.size());
//            assertNull(stack.peek());
//            assertNull(stack.pop());
//        }
//
//        @Test
//        @DisplayName("Should handle mixed null and non-null values")
//        void testMixedNullAndNonNull() {
//            stack.push(1);
//            stack.push(null);
//            stack.push(2);
//            assertEquals(2, stack.pop());
//            assertNull(stack.pop());
//            assertEquals(1, stack.pop());
//        }
//
//        @Test
//        @DisplayName("Should handle single element stack operations")
//        void testSingleElementOperations() {
//            stack.push(42);
//            assertEquals(42, stack.peek());
//            assertFalse(stack.isEmpty());
//            assertEquals(1, stack.size());
//            assertEquals(42, stack.pop());
//            assertTrue(stack.isEmpty());
//        }
//    }
//
//    @Nested
//    @DisplayName("Clear Operations Tests")
//    class ClearOperationsTests {
//
//        @Test
//        @DisplayName("Should clear empty stack")
//        void testClearEmptyStack() {
//            stack.clear();
//            assertTrue(stack.isEmpty());
//            assertEquals(0, stack.size());
//        }
//
//        @Test
//        @DisplayName("Should clear stack with elements")
//        void testClearNonEmptyStack() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            stack.clear();
//            assertTrue(stack.isEmpty());
//            assertEquals(0, stack.size());
//        }
//
//        @Test
//        @DisplayName("Should allow operations after clear")
//        void testOperationsAfterClear() {
//            stack.push(1);
//            stack.clear();
//            stack.push(2);
//            assertEquals(2, stack.peek());
//            assertEquals(1, stack.size());
//        }
//    }
//
//    @Nested
//    @DisplayName("Search Operations Tests")
//    class SearchOperationsTests {
//
//        @Test
//        @DisplayName("Should find element at top (position 1)")
//        void testSearchTopElement() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            assertEquals(1, stack.search(3)); // Top element is at position 1
//        }
//
//        @Test
//        @DisplayName("Should find element in middle")
//        void testSearchMiddleElement() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            assertEquals(2, stack.search(2)); // Second from top
//        }
//
//        @Test
//        @DisplayName("Should find element at bottom")
//        void testSearchBottomElement() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            assertEquals(3, stack.search(1)); // Bottom element
//        }
//
//        @Test
//        @DisplayName("Should return -1 for non-existent element")
//        void testSearchNonExistent() {
//            stack.push(1);
//            stack.push(2);
//            assertEquals(-1, stack.search(99));
//        }
//
//        @Test
//        @DisplayName("Should return -1 when searching empty stack")
//        void testSearchEmptyStack() {
//            assertEquals(-1, stack.search(1));
//        }
//
//        @Test
//        @DisplayName("Should search for null value")
//        void testSearchNull() {
//            stack.push(1);
//            stack.push(null);
//            stack.push(2);
//            assertEquals(2, stack.search(null));
//        }
//
//        @Test
//        @DisplayName("Should find first occurrence from top")
//        void testSearchDuplicates() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(1);
//            assertEquals(1, stack.search(1)); // Should find top occurrence
//        }
//    }
//
//    @Nested
//    @DisplayName("Contains Tests")
//    class ContainsTests {
//
//        @Test
//        @DisplayName("Should return true for existing element")
//        void testContainsExisting() {
//            stack.push(1);
//            stack.push(2);
//            assertTrue(stack.contains(1));
//            assertTrue(stack.contains(2));
//        }
//
//        @Test
//        @DisplayName("Should return false for non-existing element")
//        void testContainsNonExisting() {
//            stack.push(1);
//            assertFalse(stack.contains(2));
//        }
//
//        @Test
//        @DisplayName("Should return false for empty stack")
//        void testContainsEmpty() {
//            assertFalse(stack.contains(1));
//        }
//
//        @Test
//        @DisplayName("Should handle null in contains")
//        void testContainsNull() {
//            stack.push(null);
//            assertTrue(stack.contains(null));
//        }
//    }
//
//    @Nested
//    @DisplayName("Iterator Tests")
//    class IteratorTests {
//
//        @Test
//        @DisplayName("Should iterate from top to bottom")
//        void testIteratorOrder() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            Iterator<Integer> iter = stack.iterator();
//            assertTrue(iter.hasNext());
//            assertEquals(3, iter.next()); // Top
//            assertEquals(2, iter.next());
//            assertEquals(1, iter.next()); // Bottom
//            assertFalse(iter.hasNext());
//        }
//
//        @Test
//        @DisplayName("Should handle empty stack iteration")
//        void testIteratorEmpty() {
//            Iterator<Integer> iter = stack.iterator();
//            assertFalse(iter.hasNext());
//        }
//
//        @Test
//        @DisplayName("Should throw exception when iterating past end")
//        void testIteratorPastEnd() {
//            stack.push(1);
//            Iterator<Integer> iter = stack.iterator();
//            iter.next();
//            assertThrows(NoSuchElementException.class, () -> iter.next());
//        }
//    }
//
//    @Nested
//    @DisplayName("ToArray Tests")
//    class ToArrayTests {
//
//        @Test
//        @DisplayName("Should convert stack to array (bottom to top)")
//        void testToArray() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            Object[] array = stack.toArray();
//            assertEquals(3, array.length);
//            assertEquals(1, array[0]); // Bottom
//            assertEquals(2, array[1]);
//            assertEquals(3, array[2]); // Top
//        }
//
//        @Test
//        @DisplayName("Should return empty array for empty stack")
//        void testToArrayEmpty() {
//            Object[] array = stack.toArray();
//            assertEquals(0, array.length);
//        }
//    }
//
//    @Nested
//    @DisplayName("ToString Tests")
//    class ToStringTests {
//
//        @Test
//        @DisplayName("Should generate string representation")
//        void testToString() {
//            stack.push(1);
//            stack.push(2);
//            stack.push(3);
//            String str = stack.toString();
//            assertNotNull(str);
//            // Should contain the elements in some form
//            assertTrue(str.contains("1") || str.contains("2") || str.contains("3"));
//        }
//
//        @Test
//        @DisplayName("Should handle empty stack toString")
//        void testToStringEmpty() {
//            String str = stack.toString();
//            assertNotNull(str);
//        }
//    }
//
//    @Nested
//    @DisplayName("Stress and Boundary Tests")
//    class StressBoundaryTests {
//
//        @Test
//        @DisplayName("Should handle large number of elements")
//        void testLargeStack() {
//            int count = 10000;
//            for (int i = 0; i < count; i++) {
//                stack.push(i);
//            }
//            assertEquals(count, stack.size());
//            assertEquals(count - 1, stack.peek());
//
//            for (int i = count - 1; i >= 0; i--) {
//                assertEquals(i, stack.pop());
//            }
//            assertTrue(stack.isEmpty());
//        }
//
//        @Test
//        @DisplayName("Should handle alternating push/pop operations")
//        void testAlternatingPushPop() {
//            stack.push(1);
//            assertEquals(1, stack.pop());
//            stack.push(2);
//            assertEquals(2, stack.pop());
//            stack.push(3);
//            assertEquals(3, stack.pop());
//            assertTrue(stack.isEmpty());
//        }
//
//        @Test
//        @DisplayName("Should handle multiple clear operations")
//        void testMultipleClear() {
//            stack.push(1);
//            stack.clear();
//            stack.clear(); // Clear already empty
//            stack.push(2);
//            stack.clear();
//            assertTrue(stack.isEmpty());
//        }
//    }
//
//    @Nested
//    @DisplayName("Type Safety Tests")
//    class TypeSafetyTests {
//
//        @Test
//        @DisplayName("Should handle String type")
//        void testStringStack() {
//            Stack<String> stringStack = new Stack<>();
//            stringStack.push("hello");
//            stringStack.push("world");
//            assertEquals("world", stringStack.pop());
//            assertEquals("hello", stringStack.pop());
//        }
//
//        @Test
//        @DisplayName("Should handle custom object type")
//        void testCustomObjectStack() {
//            Stack<TestObject> objStack = new Stack<>();
//            TestObject obj1 = new TestObject(1);
//            TestObject obj2 = new TestObject(2);
//            objStack.push(obj1);
//            objStack.push(obj2);
//            assertEquals(obj2, objStack.pop());
//            assertEquals(obj1, objStack.pop());
//        }
//    }
//
//    // Helper class for testing custom objects
//    private static class TestObject {
//        private final int value;
//
//        TestObject(int value) {
//            this.value = value;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            TestObject that = (TestObject) o;
//            return value == that.value;
//        }
//
//        @Override
//        public int hashCode() {
//            return value;
//        }
//    }
//}
