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
