package aprs_prog1;

import java.util.ArrayList;
import java.util.Collections;

public class ListProcessor {
    ArrayList<Integer> list = new ArrayList<Integer>();

    public ListProcessor(ArrayList<Integer> in) {
        this.list = in;
        Collections.sort(this.list);
    }

    public static Integer binarySearch(ArrayList<Integer> input, Integer target, int l, int r) {
        if (r >= l) {
            int midIdx = l + (r-l)/2;
            Integer midElem = input.get(midIdx);
            if (midElem.equals(target)) {
                return midIdx+1;
            }

            if (midElem > target) {
                return binarySearch(input, target, l, midIdx-1);
            } else {
                return binarySearch(input, target,midIdx+1,r);
            }
        }
        return -1;
    }
}
