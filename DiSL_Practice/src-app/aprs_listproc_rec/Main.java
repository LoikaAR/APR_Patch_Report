package aprs_listproc_rec;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public Main() {
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

    public static void main(String[] args) {
        ArrayList<Integer> in = new ArrayList<Integer>();
        for (Integer i = 0; i < 50; i++) {
            in.add(i);
        }
        binarySearch(in, 3, 0, in.size()-1);
    }
}
