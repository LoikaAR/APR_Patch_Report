package aprs_introclass;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Enter an integer >  2 6 6 8 6 5 5 0 0 1 That's all, have a nice day!";
        mainClass.scanner = new java.util.Scanner ("1005568662");
        mainClass.exec ();
    }
}