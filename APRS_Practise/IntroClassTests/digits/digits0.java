package aprs_introclass;

import org.junit.Test;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected = "Enter an integer >  0 That's all, have a nice day!";
        mainClass.scanner = new java.util.Scanner ("0");
        mainClass.exec ();
    }
}