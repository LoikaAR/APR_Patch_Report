package CollectionsProcessing;

import static CollectionsProcessing.ListProcessor.binarySearch;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }

        String big_bad_string = "HELLO THERE PLEASE NOTICE ME";
        int h = 3;
        int w = 4;
        int p = h+w;

        System.out.println(h + w + p);

        System.out.println("main called");
        System.out.println(binarySearch(input, 5, 0, input.size()-1));
    }
}
