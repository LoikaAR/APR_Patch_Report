package aprs_introclass;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Enter an integer >  1 5 0 6 2 5 1 That's all, have a nice day!";
        mainClass.scanner = new java.util.Scanner ("1526051");
        mainClass.exec ();
    }
}