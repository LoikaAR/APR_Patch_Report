package aprs_introclass;

class IntObj {
    public int value;
    public IntObj () {
    } public IntObj (int i) {
        value = i;
    }
}

class FloatObj {
    public float value;
    public FloatObj () {
    } public FloatObj (float i) {
        value = i;
    }
}

class LongObj {
    public long value;
    public LongObj () {
    } public LongObj (long i) {
        value = i;
    }
}

class DoubleObj {
    public double value;
    public DoubleObj () {
    } public DoubleObj (double i) {
        value = i;
    }
}

class CharObj {
    public char value;
    public CharObj () {
    } public CharObj (char i) {
        value = i;
    }
}

public class ClassDef {
    public java.util.Scanner scanner;
    public String output = "";

    public static void main (String[]args)  {
	ClassDef mainClass = new ClassDef();
        String output;
        if (args.length > 0) {
            mainClass.scanner = new java.util.Scanner (args[0]);
        } else {
            mainClass.scanner = new java.util.Scanner (System.in);
        }
        mainClass.exec ();
        System.out.println (mainClass.output);
    }

    public void exec ()  {
	String name = "checksum_08c7ea4a_007";
        char[] string = new char[1000];
	ClassDef mainClass = new ClassDef();
        IntObj i = new IntObj (), stringlength =
	ClassDef mainClass = new ClassDef();
        output +=
            (String.format
             ("Enter an abitrarily long string, ending with carriage return > "));
        string = scanner.next ().toCharArray ();
        stringlength.value = string.length;
	ClassDef mainClass = new ClassDef();
        for (i.value = 0; i.value < stringlength.value; i.value++) {
	ClassDef mainClass = new ClassDef();
        }
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
        if (true)
            return;;
    }
}
