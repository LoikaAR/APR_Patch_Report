package aprs_listproc;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public Main() {
    }

    public static Integer binarySearch(int input[], int target) {
        int l = 0;
        int r = input.length-1;

        while (l <= r) {
            int mid = l + (r-l) / 2;

            if (input[mid] == target) {
                return mid;
            }

            if (input[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int in[] = {0,1,2,3,4,5};
        int target = 3;
        binarySearch(in, target);
    }
}



















//public class Main {
//
//    public Main() {
//    }
//
//    public static Integer binarySearch(ArrayList<Integer> input, Integer target, int l, int r) {
//        if (r >= l) {
//            int midIdx = l + (r-l)/2;
//            Integer midElem = input.get(midIdx);
//            if (midElem.equals(target)) {
//                return midIdx+1;
//            }
//
//            if (midElem > target) {
//                return binarySearch(input, target, l, midIdx-1);
//            } else {
//                return binarySearch(input, target,midIdx+1,r);
//            }
//        }
//        return -1;
//    }
//
//    public static void main(String[] args) {
//        ArrayList<Integer> in = new ArrayList<Integer>();
//        in.add(0);
//        in.add(1);
//        in.add(2);
//        in.add(3);
//        in.add(4);
//        binarySearch(in, 3, 0, in.size()-1);
//    }
//}
