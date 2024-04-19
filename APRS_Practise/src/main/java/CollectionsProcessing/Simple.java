package CollectionsProcessing;;

import java.io.ObjectOutputStream;

public class Simple {

    public static int adder(int a, int b) {
        int h;
        h = a+b;
        return h;
    }
    public static void main(String[] args) {
        int i = 1;
        int j = 3;

        int s = adder(i, j);

        System.out.println(s);
    }
}
