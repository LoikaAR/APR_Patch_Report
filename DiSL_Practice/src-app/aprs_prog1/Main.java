// observed application

package aprs_prog1;

public class Main {
    public static void main(String[] args) {
        System.out.println("main executed");
        new Main().met1();
    }

    public void met1() {
        System.out.println("met1 (not static) executed");
        met2();
    }

    static void met2() {
        System.out.println("met2 (static) executed");
    }

    public static int sum(int a, int b) {
        return a + b;
    }

}
