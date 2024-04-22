package aprs_listproc;

import java.util.ArrayList;
import java.util.Collections;
public class Main {

    public String instance_string = "Instance Field String of Main Class";
    public Integer instance_integer = 5234;

    public String getName() {
        return "this is called Main";
    }

    public Main() {
    }


//    public int binarySearch(int f[]) {
//        int d = f[1];
//        return f[2];
//    }

    public Integer binarySearch(int input[], int target) {
        int hello = 38;                     // 0
        int l = 0;                          // 0
        int r = input.length-1;             // 0

        while (l <= r) {                    // 1
            int mid = l + (r-l) / 2;        // 1

            if (input[mid] == target) {     // 2
                hello = 27;                 // 2?
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
        String someString = m.instance_string;
        m.binarySearch(in, target);
//        m.binarySearch(in);
    }
}
