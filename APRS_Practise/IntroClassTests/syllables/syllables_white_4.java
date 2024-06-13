package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected = "Please enter a string > The number of syllables is 3.";
        mainClass.scanner = new java.util.Scanner ("hello world");
	mainClass.exec();
    }
}