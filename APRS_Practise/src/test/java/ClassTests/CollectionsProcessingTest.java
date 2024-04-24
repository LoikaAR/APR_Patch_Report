package ClassTests;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import CollectionsProcessing.ListProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsProcessingTest {
    @Test
    public void testBinSearchPass() {
        ListProcessor lp = new ListProcessor();
        int[] in = {1,2,3,4,5,6,7,8,9};
        assertEquals(4, lp.binarySearch(in, 4));
    }

    @Test
    public void testNotFound() {
        ListProcessor lp = new ListProcessor();
        int[] in = {1,2,3,4,5,6,7,8,9};
        assertEquals(-1, lp.binarySearch(in, 12));
    }

//    @Test
//    public void testBinSearchFail() {
//        ListProcessor lp = new ListProcessor();
//        int[] in = {1,2,3,4,5,6,7,8,9};
//        assertEquals(4, lp.binarySearch(in, 5));
//    }
}
