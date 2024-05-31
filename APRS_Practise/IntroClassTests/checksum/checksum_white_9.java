package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Enter an abitrarily long string, ending with carriage return > Check sum is #";
        mainClass.scanner = new java.util.Scanner ("! word 12 :)");
	mainClass.exec();
    }
}