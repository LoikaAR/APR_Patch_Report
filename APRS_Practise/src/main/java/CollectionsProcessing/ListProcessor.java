package CollectionsProcessing;

import java.util.ArrayList;
import java.util.Collections;

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

    public void newMethod() {
        System.out.println("method called");
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<Integer>();
        new ListProcessor(input).newMethod();
        int j = 3;
        j = j + 1;
        System.out.println(j);
        System.out.println("main called");
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }
        System.out.println(binarySearch(input, 5, 0, input.size()-1));
    }
}
