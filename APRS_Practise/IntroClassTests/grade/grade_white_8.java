package aprs_introclass;
import aprs_introclass.ClassDef;
public class MainInstance {
	public static void main(String[] args) {
	ClassDef mainClass = new ClassDef();
        String expected =
            "Enter thresholds for A, B, C, D in that order, decreasing percentages > Thank you. Now enter student score (percent) >Student has failed the course";
        mainClass.scanner = new java.util.Scanner ("90 80 70 60 55");
	mainClass.exec();
    }
}