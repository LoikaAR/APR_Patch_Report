package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected = "Enter an integer >  9 1 That's all, have a nice day!";
        mainClass.scanner = new java.util.Scanner ("19");
	mainClass.exec();
    }
}