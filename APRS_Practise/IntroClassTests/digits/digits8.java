package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Enter an integer >  1 3 6 0 6 9 0 4 That's all, have a nice day!";
        mainClass.scanner = new java.util.Scanner ("40960631");
	mainClass.exec();
    }
}