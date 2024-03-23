package aprs_listproc;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public Main() {
    }

    public Integer binarySearch(int input[], int target) {
        int l = 0;                          // 0
        int r = input.length-1;             // 0

        while (l <= r) {                    // 1
            int mid = l + (r-l) / 2;        // 1

            if (input[mid] == target) {     // 2
                return mid;                 // 3
            }

            if (input[mid] < target) {      // 4
                l = mid + 1;                // 5
            } else {                        // 6
                r = mid - 1;                // 7
            }
        }
        return -1;                          // 8
    }

    public static void main(String[] args) {
        int in[] = {0,1,2,3,4,5};
        int target = 3;
        Main m = new Main();
        m.binarySearch(in, target);
    }
}
