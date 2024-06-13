package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Please enter 4 numbers separated by spaces > -1 is the smallest";
        mainClass.scanner = new java.util.Scanner ("0 0 0 -1");
	mainClass.exec();
    }
}