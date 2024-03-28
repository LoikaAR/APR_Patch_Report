package ClassTests;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import CollectionsProcessing.ListProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsProcessingTest {
    @Test
    public void testBinSearchPass() {
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }
        assertEquals(5, ListProcessor.binarySearch(input, 4, 0, input.size()-1));
    }

    @Test
    public void testNotFound() {
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }
        assertEquals(-1, ListProcessor.binarySearch(input, 12, 0, input.size()-1));
    }

    @Test
    public void testBinSearchFail() {
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }
        assertEquals(4, ListProcessor.binarySearch(input, 3, 0, input.size()-1));
    }
}
