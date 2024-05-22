package aprs_introclass;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Enter an integer >  0 2 5 9 7 6 0 4 1 That's all, have a nice day!";
        mainClass.scanner = new java.util.Scanner ("140679520");
        mainClass.exec ();
    }
}