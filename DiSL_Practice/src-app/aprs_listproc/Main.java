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
        int outer_int = 38;                 // 0
        int l = 0;                          // 0
        int r = input.length-1;             // 0

        while (l <= r) {                    // 1
            int mid = l + (r-l) / 2;        // 1
            String inner_string = "value_1";// 2
            if (input[mid] == target) {     // 2
                outer_int = 27;             // 2
                return mid+1;               // 3
            }
            if (input[mid] < target) {      // 4
                l = mid + 1;                // 5
            } else {                        // 6
                r = mid - 1;                // 7
                inner_string = "different_value";
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
