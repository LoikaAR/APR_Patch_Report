package CollectionsProcessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.floor;

public class ListProcessor {
    ArrayList<Integer> list = new ArrayList<Integer>();

    public ListProcessor(ArrayList<Integer> in) {
        this.list = in;
        Collections.sort(this.list);
    }

    /**
     *
     * @param input the input list
     * @param target the target
     * @return the index of target if it exists, -1 otherwise
     */
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
