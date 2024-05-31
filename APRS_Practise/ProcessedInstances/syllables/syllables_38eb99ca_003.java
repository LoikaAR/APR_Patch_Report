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

    public static int sizeA = 22;
    public void exec ()  {
	String name = "syllables_38eb99ca_003";
        char[] string = new char[sizeA];
        IntObj i = new IntObj ();
        IntObj count = new IntObj (0);
        output += (String.format ("Please enter a string > "));
        string = (scanner.nextLine () + "\n").toCharArray ();
        for (i.value = 0; i.value < string.length; i.value++) {
            if (string[i.value] == 'a' || string[i.value] == 'e'
                    || string[i.value] == 'o' || string[i.value] == 'u'
                    || string[i.value] == 'y') {
                count.value++;
            }
        }
        output +=
	ClassDef mainClass = new ClassDef();
        if (true)
            return;;
    }
}
