package aprs_listproc;

import org.junit.jupiter.api.Assertions.assertEquals;

public class ListProcTest {
    @Test
    public void binSearchTest() {
        Main m = new Main();
        int in[] = {0,1,2,3,4,5};
        int target = 3;
        assertEquals(m.binarySearch(in, target), 4);
    }
}