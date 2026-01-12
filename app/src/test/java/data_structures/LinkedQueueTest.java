//package data_structures;
//
//import data_structures.queue.LinkedQueue;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.DisplayName;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.NoSuchElementException;
//import java.util.Iterator;
//
//@DisplayName("LinkedQueue Tests")
//class LinkedQueueTest {
//
//    private LinkedQueue<Integer> linkedQueue;
//
//    @BeforeEach
//    void setUp() {
//        linkedQueue = new LinkedQueue<>();
//    }
//
//    @Nested
//    @DisplayName("Basic Operations Tests")
//    class BasicOperationsTests {
//
//        @Test
//        @DisplayName("Should create empty linkedQueue")
//        void testNewQueueIsEmpty() {
//            assertTrue(linkedQueue.isEmpty());
//            assertEquals(0, linkedQueue.size());
//        }
//
//        @Test
//        @DisplayName("Should enqueue single element")
//        void testEnqueueSingleElement() {
//            linkedQueue.enqueue(1);
//            assertFalse(linkedQueue.isEmpty());
//            assertEquals(1, linkedQueue.size());
//            assertEquals(1, linkedQueue.peek());
//        }
//
//        @Test
//        @DisplayName("Should enqueue multiple elements")
//        void testEnqueueMultipleElements() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            assertEquals(3, linkedQueue.size());
//            assertEquals(1, linkedQueue.peek()); // First element should be at front
//        }
//
//        @Test
//        @DisplayName("Should dequeue single element")
//        void testDequeueSingleElement() {
//            linkedQueue.enqueue(1);
//            int dequeued = linkedQueue.dequeue();
//            assertEquals(1, dequeued);
//            assertTrue(linkedQueue.isEmpty());
//            assertEquals(0, linkedQueue.size());
//        }
//
//        @Test
//        @DisplayName("Should dequeue in FIFO order")
//        void testDequeueFIFOOrder() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            assertEquals(1, linkedQueue.dequeue()); // First in, first out
//            assertEquals(2, linkedQueue.dequeue());
//            assertEquals(3, linkedQueue.dequeue());
//            assertTrue(linkedQueue.isEmpty());
//        }
//
//        @Test
//        @DisplayName("Should peek without removing")
//        void testPeekDoesNotRemove() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            assertEquals(1, linkedQueue.peek());
//            assertEquals(1, linkedQueue.peek()); // Still there
//            assertEquals(2, linkedQueue.size());
//        }
//    }
//
//    @Nested
//    @DisplayName("Edge Cases Tests")
//    class EdgeCasesTests {
//
//        @Test
//        @DisplayName("Should throw exception when dequeuing empty linkedQueue")
//        void testDequeueEmptyQueue() {
//            assertThrows(NoSuchElementException.class, () -> linkedQueue.dequeue());
//        }
//
//        @Test
//        @DisplayName("Should throw exception when peeking empty linkedQueue")
//        void testPeekEmptyQueue() {
//            assertThrows(NoSuchElementException.class, () -> linkedQueue.peek());
//        }
//
//        @Test
//        @DisplayName("Should handle enqueue after dequeue to empty")
//        void testEnqueueAfterDequeueToEmpty() {
//            linkedQueue.enqueue(1);
//            linkedQueue.dequeue();
//            linkedQueue.enqueue(2);
//            assertEquals(2, linkedQueue.peek());
//            assertEquals(1, linkedQueue.size());
//        }
//
//        @Test
//        @DisplayName("Should handle null values")
//        void testEnqueueNullValue() {
//            linkedQueue.enqueue(null);
//            assertFalse(linkedQueue.isEmpty());
//            assertEquals(1, linkedQueue.size());
//            assertNull(linkedQueue.peek());
//            assertNull(linkedQueue.dequeue());
//        }
//
//        @Test
//        @DisplayName("Should handle mixed null and non-null values")
//        void testMixedNullAndNonNull() {
//            linkedQueue.enqueue(null);
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            assertNull(linkedQueue.dequeue());
//            assertEquals(1, linkedQueue.dequeue());
//            assertEquals(2, linkedQueue.dequeue());
//        }
//
//        @Test
//        @DisplayName("Should handle single element linkedQueue operations")
//        void testSingleElementOperations() {
//            linkedQueue.enqueue(42);
//            assertEquals(42, linkedQueue.peek());
//            assertFalse(linkedQueue.isEmpty());
//            assertEquals(1, linkedQueue.size());
//            assertEquals(42, linkedQueue.dequeue());
//            assertTrue(linkedQueue.isEmpty());
//        }
//    }
//
//    @Nested
//    @DisplayName("FIFO Order Verification Tests")
//    class FIFOOrderTests {
//
//        @Test
//        @DisplayName("Should maintain FIFO order with multiple operations")
//        void testFIFOOrderMultipleOperations() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            assertEquals(1, linkedQueue.dequeue());
//            linkedQueue.enqueue(3);
//            linkedQueue.enqueue(4);
//            assertEquals(2, linkedQueue.dequeue());
//            assertEquals(3, linkedQueue.dequeue());
//            assertEquals(4, linkedQueue.dequeue());
//        }
//
//        @Test
//        @DisplayName("Should verify order with peek between enqueues")
//        void testPeekBetweenEnqueues() {
//            linkedQueue.enqueue(1);
//            assertEquals(1, linkedQueue.peek());
//            linkedQueue.enqueue(2);
//            assertEquals(1, linkedQueue.peek()); // First element still at front
//            linkedQueue.enqueue(3);
//            assertEquals(1, linkedQueue.peek());
//        }
//
//        @Test
//        @DisplayName("Should maintain order after partial dequeue")
//        void testOrderAfterPartialDequeue() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            linkedQueue.dequeue(); // Remove 1
//            assertEquals(2, linkedQueue.peek());
//            linkedQueue.dequeue(); // Remove 2
//            assertEquals(3, linkedQueue.peek());
//        }
//    }
//
//    @Nested
//    @DisplayName("Clear Operations Tests")
//    class ClearOperationsTests {
//
//        @Test
//        @DisplayName("Should clear empty linkedQueue")
//        void testClearEmptyQueue() {
//            linkedQueue.clear();
//            assertTrue(linkedQueue.isEmpty());
//            assertEquals(0, linkedQueue.size());
//        }
//
//        @Test
//        @DisplayName("Should clear linkedQueue with elements")
//        void testClearNonEmptyQueue() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            linkedQueue.clear();
//            assertTrue(linkedQueue.isEmpty());
//            assertEquals(0, linkedQueue.size());
//        }
//
//        @Test
//        @DisplayName("Should allow operations after clear")
//        void testOperationsAfterClear() {
//            linkedQueue.enqueue(1);
//            linkedQueue.clear();
//            linkedQueue.enqueue(2);
//            assertEquals(2, linkedQueue.peek());
//            assertEquals(1, linkedQueue.size());
//        }
//
//        @Test
//        @DisplayName("Should maintain FIFO after clear")
//        void testFIFOAfterClear() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.clear();
//            linkedQueue.enqueue(3);
//            linkedQueue.enqueue(4);
//            assertEquals(3, linkedQueue.dequeue());
//            assertEquals(4, linkedQueue.dequeue());
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
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            assertTrue(linkedQueue.contains(1));
//            assertTrue(linkedQueue.contains(2));
//            assertTrue(linkedQueue.contains(3));
//        }
//
//        @Test
//        @DisplayName("Should return false for non-existing element")
//        void testContainsNonExisting() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            assertFalse(linkedQueue.contains(3));
//        }
//
//        @Test
//        @DisplayName("Should return false for empty linkedQueue")
//        void testContainsEmpty() {
//            assertFalse(linkedQueue.contains(1));
//        }
//
//        @Test
//        @DisplayName("Should handle null in contains")
//        void testContainsNull() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(null);
//            linkedQueue.enqueue(2);
//            assertTrue(linkedQueue.contains(null));
//        }
//
//        @Test
//        @DisplayName("Should find duplicates")
//        void testContainsDuplicates() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(1);
//            assertTrue(linkedQueue.contains(1));
//            assertEquals(3, linkedQueue.size());
//        }
//    }
//
//    @Nested
//    @DisplayName("Iterator Tests")
//    class IteratorTests {
//
//        @Test
//        @DisplayName("Should iterate from front to rear")
//        void testIteratorOrder() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            Iterator<Integer> iter = linkedQueue.iterator();
//            assertTrue(iter.hasNext());
//            assertEquals(1, iter.next()); // Front
//            assertEquals(2, iter.next());
//            assertEquals(3, iter.next()); // Rear
//            assertFalse(iter.hasNext());
//        }
//
//        @Test
//        @DisplayName("Should handle empty linkedQueue iteration")
//        void testIteratorEmpty() {
//            Iterator<Integer> iter = linkedQueue.iterator();
//            assertFalse(iter.hasNext());
//        }
//
//        @Test
//        @DisplayName("Should throw exception when iterating past end")
//        void testIteratorPastEnd() {
//            linkedQueue.enqueue(1);
//            Iterator<Integer> iter = linkedQueue.iterator();
//            iter.next();
//            assertThrows(NoSuchElementException.class, () -> iter.next());
//        }
//
//        @Test
//        @DisplayName("Should iterate over single element")
//        void testIteratorSingleElement() {
//            linkedQueue.enqueue(42);
//            Iterator<Integer> iter = linkedQueue.iterator();
//            assertTrue(iter.hasNext());
//            assertEquals(42, iter.next());
//            assertFalse(iter.hasNext());
//        }
//    }
//
//    @Nested
//    @DisplayName("ToArray Tests")
//    class ToArrayTests {
//
//        @Test
//        @DisplayName("Should convert linkedQueue to array (front to rear)")
//        void testToArray() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            Object[] array = linkedQueue.toArray();
//            assertEquals(3, array.length);
//            assertEquals(1, array[0]); // Front
//            assertEquals(2, array[1]);
//            assertEquals(3, array[2]); // Rear
//        }
//
//        @Test
//        @DisplayName("Should return empty array for empty linkedQueue")
//        void testToArrayEmpty() {
//            Object[] array = linkedQueue.toArray();
//            assertEquals(0, array.length);
//        }
//
//        @Test
//        @DisplayName("Should reflect current linkedQueue state")
//        void testToArrayAfterDequeue() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            linkedQueue.dequeue(); // Remove 1
//            Object[] array = linkedQueue.toArray();
//            assertEquals(2, array.length);
//            assertEquals(2, array[0]);
//            assertEquals(3, array[1]);
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
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            String str = linkedQueue.toString();
//            assertNotNull(str);
//            // Should contain the elements in some form
//            assertTrue(str.contains("1") || str.contains("2") || str.contains("3"));
//        }
//
//        @Test
//        @DisplayName("Should handle empty linkedQueue toString")
//        void testToStringEmpty() {
//            String str = linkedQueue.toString();
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
//        void testLargeQueue() {
//            int count = 10000;
//            for (int i = 0; i < count; i++) {
//                linkedQueue.enqueue(i);
//            }
//            assertEquals(count, linkedQueue.size());
//            assertEquals(0, linkedQueue.peek());
//
//            for (int i = 0; i < count; i++) {
//                assertEquals(i, linkedQueue.dequeue());
//            }
//            assertTrue(linkedQueue.isEmpty());
//        }
//
//        @Test
//        @DisplayName("Should handle alternating enqueue/dequeue operations")
//        void testAlternatingEnqueueDequeue() {
//            linkedQueue.enqueue(1);
//            assertEquals(1, linkedQueue.dequeue());
//            linkedQueue.enqueue(2);
//            assertEquals(2, linkedQueue.dequeue());
//            linkedQueue.enqueue(3);
//            assertEquals(3, linkedQueue.dequeue());
//            assertTrue(linkedQueue.isEmpty());
//        }
//
//        @Test
//        @DisplayName("Should handle circular behavior (enqueue after many dequeues)")
//        void testCircularBehavior() {
//            // Fill linkedQueue
//            for (int i = 0; i < 100; i++) {
//                linkedQueue.enqueue(i);
//            }
//            // Dequeue half
//            for (int i = 0; i < 50; i++) {
//                linkedQueue.dequeue();
//            }
//            // Enqueue more
//            for (int i = 100; i < 150; i++) {
//                linkedQueue.enqueue(i);
//            }
//            assertEquals(100, linkedQueue.size());
//            assertEquals(50, linkedQueue.peek());
//        }
//
//        @Test
//        @DisplayName("Should handle multiple clear operations")
//        void testMultipleClear() {
//            linkedQueue.enqueue(1);
//            linkedQueue.clear();
//            linkedQueue.clear(); // Clear already empty
//            linkedQueue.enqueue(2);
//            linkedQueue.clear();
//            assertTrue(linkedQueue.isEmpty());
//        }
//
//        @Test
//        @DisplayName("Should handle rapid enqueue/dequeue cycles")
//        void testRapidCycles() {
//            for (int cycle = 0; cycle < 100; cycle++) {
//                linkedQueue.enqueue(cycle);
//                linkedQueue.enqueue(cycle + 1);
//                assertEquals(cycle, linkedQueue.dequeue());
//                assertEquals(cycle + 1, linkedQueue.dequeue());
//            }
//            assertTrue(linkedQueue.isEmpty());
//        }
//    }
//
//    @Nested
//    @DisplayName("Type Safety Tests")
//    class TypeSafetyTests {
//
//        @Test
//        @DisplayName("Should handle String type")
//        void testStringQueue() {
//            LinkedQueue<String> stringLinkedQueue = new LinkedQueue<>();
//            stringLinkedQueue.enqueue("first");
//            stringLinkedQueue.enqueue("second");
//            stringLinkedQueue.enqueue("third");
//            assertEquals("first", stringLinkedQueue.dequeue());
//            assertEquals("second", stringLinkedQueue.dequeue());
//            assertEquals("third", stringLinkedQueue.dequeue());
//        }
//
//        @Test
//        @DisplayName("Should handle custom object type")
//        void testCustomObjectQueue() {
//            LinkedQueue<TestObject> objLinkedQueue = new LinkedQueue<>();
//            TestObject obj1 = new TestObject(1);
//            TestObject obj2 = new TestObject(2);
//            TestObject obj3 = new TestObject(3);
//            objLinkedQueue.enqueue(obj1);
//            objLinkedQueue.enqueue(obj2);
//            objLinkedQueue.enqueue(obj3);
//            assertEquals(obj1, objLinkedQueue.dequeue());
//            assertEquals(obj2, objLinkedQueue.dequeue());
//            assertEquals(obj3, objLinkedQueue.dequeue());
//        }
//    }
//
//    @Nested
//    @DisplayName("Complex Scenarios Tests")
//    class ComplexScenariosTests {
//
//        @Test
//        @DisplayName("Should handle interleaved enqueue and dequeue")
//        void testInterleavedOperations() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            assertEquals(1, linkedQueue.dequeue());
//            linkedQueue.enqueue(3);
//            linkedQueue.enqueue(4);
//            assertEquals(2, linkedQueue.dequeue());
//            assertEquals(3, linkedQueue.dequeue());
//            linkedQueue.enqueue(5);
//            assertEquals(4, linkedQueue.dequeue());
//            assertEquals(5, linkedQueue.dequeue());
//        }
//
//        @Test
//        @DisplayName("Should maintain integrity after exception")
//        void testIntegrityAfterException() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.dequeue();
//            linkedQueue.dequeue();
//            assertThrows(NoSuchElementException.class, () -> linkedQueue.dequeue());
//            // LinkedQueue should still work after exception
//            linkedQueue.enqueue(3);
//            assertEquals(3, linkedQueue.peek());
//        }
//
//        @Test
//        @DisplayName("Should handle peek after multiple dequeues")
//        void testPeekAfterDequeues() {
//            linkedQueue.enqueue(1);
//            linkedQueue.enqueue(2);
//            linkedQueue.enqueue(3);
//            linkedQueue.dequeue();
//            assertEquals(2, linkedQueue.peek());
//            linkedQueue.dequeue();
//            assertEquals(3, linkedQueue.peek());
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
