package CollectionsProcessing;

import java.util.ArrayList;
import java.util.Collections;

public class ListProcessor {

    public String instance_string = "Instance Field String of Main Class";
    public Integer instance_integer = 5234;

    public String getName() {
        return "Class name = Main";
    }

    public ListProcessor() {

    }

    public Integer binarySearch(int input[], int target) {
        int hello = 38;                     // 0
        int l = 0;                          // 0
        int r = input.length-1;             // 0

        while (l <= r) {                    // 1
            int mid = l + (r-l) / 2;        // 1

            if (input[mid] == target) {     // 2
                hello = 27;                 // 2
                return mid+1;                 // 3
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
        System.out.println("Executing main");
        int in[] = {0,1,2,3,4,5};
        int target = 3;

        ListProcessor m = new ListProcessor();
        String someString = m.instance_string;
        Integer someInteger = m.instance_integer;
        System.out.println(m.binarySearch(in, target));

    }
}
