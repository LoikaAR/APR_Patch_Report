package ClassTests;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import CollectionsProcessing.ListProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsProcessingTest {
    ArrayList<Integer> input = new ArrayList<Integer>();

    @Test
    public void testBinSearchPass() {
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }
        assertEquals(5, ListProcessor.binarySearch(input, 4, 0, input.size()-1));
    }

    @Test
    public void testBinSearchFail() {
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }
        assertEquals(3, ListProcessor.binarySearch(input, 3, 0, input.size()-1));
    }
}
