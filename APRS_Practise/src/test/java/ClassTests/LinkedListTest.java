//package ClassTests;
//
//import LinkedList.LinkedList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class LinkedListTest {
//    LinkedList l = new LinkedList();
//
//    @Test
//    void testGetTrue() {
//        l = LinkedList.insert(l, 1);
//        l = LinkedList.insert(l, 2);
//        l = LinkedList.insert(l, 3);
//        assertEquals(2, l.getIndex(l, 3));
//    }
//
//    @Test
//    void testGetFalse() {
//        l = LinkedList.insert(l, 1);
//        l = LinkedList.insert(l, 2);
//        l = LinkedList.insert(l, 3);
//        assertEquals(-1, l.getIndex(l,5));
//    }
//
//    @Test
//    void testCountValid() {
//        l = LinkedList.insert(l, 3);
//        l = LinkedList.insert(l, 3);
//        l = LinkedList.insert(l, 3);
//        assertEquals(3, l.getCount(l,3));
//    }
//
//    @Test
//    void testCountInvalid() {
//        l = LinkedList.insert(l, 1);
//        l = LinkedList.insert(l, 2);
//        l = LinkedList.insert(l, 3);
//        assertEquals(0, l.getCount(l,5));
//    }
//}