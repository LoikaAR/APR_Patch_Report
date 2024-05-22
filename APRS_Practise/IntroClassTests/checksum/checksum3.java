package aprs_introclass;
public class MainInstance {
	public static void main(String[] args) {
	checksum mainClass = new checksum();
        String expected =
            "Enter an abitrarily long string, ending with carriage return > Check sum is K";
        mainClass.scanner = new java.util.Scanner ("A*");
        mainClass.exec ();
    }
}