package aprs_listproc;

import java.util.ArrayList;
import java.util.Collections;
public class Main {

    public String instance_string = "Instance Field String of Main Class";
    public Integer instance_integer = 5234;

    public String getName() {
        return "Class name = Main";
    }

    public Integer binarySearch(int input[], int target) {
        String outer_string = "value_0";    // 0
        int outer_int = 30008;                 // 0 (v1: 38)
        int l = 0;                          // 0
        int r = input.length-1;             // 0

        while (l <= r) {                    // 1
            int mid = l + (r-l) / 2;        // 1
            String inner_string = "_1";// 2 (v1: "value_1")
            if (input[mid] == target) {     // 2
                outer_int = 12213;             // 2 (v1: 27)
                return mid;               // 3 (v1: mid+1)
            }
            if (input[mid] < target) {      // 4
                l = mid + 1;                // 5
            } else {                        // 6
                r = mid - 1;                // 7
                inner_string = "new_val"; // (v1: "different_value")
            }

        }
        return -1;                          // 8
    }

    public static void main(String[] args) {
        int in[] = {0,1,2,3,4,5};
        int target = 3;
        Main m = new Main();
        String someString = m.instance_string;
        Integer someInteger = m.instance_integer;
        m.binarySearch(in, target);
    }
}
