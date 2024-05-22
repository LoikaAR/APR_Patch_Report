package aprs_introclass;

import org.junit.Test;
public class MainInstance {
	public static void main(String[] args) {
	checksum mainClass = new checksum();
        String expected =
            "Enter an abitrarily long string, ending with carriage return > Check sum is ]";
        mainClass.scanner = new java.util.Scanner ("hello world!");
        mainClass.exec ();
    }
}