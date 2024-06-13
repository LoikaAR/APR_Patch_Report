package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected = "Please enter a string > The number of syllables is 1.";
        mainClass.scanner = new java.util.Scanner ("bbbbbbb a");
	mainClass.exec();
    }
}