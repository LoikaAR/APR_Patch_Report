package aprs_listproc;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public Main() {
    }

    public Integer binarySearch(int input[], int target) {
        int l = 0;                          // 1
        int r = input.length-1;             // 2

        while (l <= r) {                    // 3
            int mid = l + (r-l) / 2;        // 4

            if (input[mid] == target) {     // 5
                return mid;
            }

            if (input[mid] < target) {      // 6
                l = mid + 1;
            } else {                        // 7
                r = mid - 1;
            }
        }
        return -1;                          // 8
    }

    public static void main(String[] args) {
        int in[] = {0,1,2,3,4,5};
        int target = 1;
        Main m = new Main();
        m.binarySearch(in, target);
    }
}
