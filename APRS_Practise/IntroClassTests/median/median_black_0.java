package aprs_introclass;
import aprs_introclass.ClassDef;

public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Please enter 3 numbers separated by spaces > 6 is the median";
        mainClass.scanner = new java.util.Scanner ("2 6 8");
	mainClass.exec();
    }
}