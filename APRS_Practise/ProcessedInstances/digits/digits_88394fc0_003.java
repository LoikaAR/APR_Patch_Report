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
	String name = "digits_88394fc0_003";
        IntObj n = new IntObj ();
	ClassDef mainClass = new ClassDef();
        output += (String.format ("\nEnter an integer > "));
        n.value = scanner.nextInt ();
        output += (String.format ("\n"));
        n.value = Math.abs (n.value);
	ClassDef mainClass = new ClassDef();
        while (n.value > 0) {
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
            n.value = n.value / 10;
        }
        output += (String.format ("That's all, have a nice day!\n"));
        if (true)
            return;;
    }
}