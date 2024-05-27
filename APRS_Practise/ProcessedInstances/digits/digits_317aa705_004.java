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
	String name = "digits_317aa705_004";
        IntObj integer = new IntObj (), i = new IntObj (), digit = new IntObj ();
        output += (String.format ("\nEnter an integer > "));
        integer.value = scanner.nextInt ();
        digit.value = 0;
        for (i.value = 1; i.value <= 10; i.value += 1) {
            digit.value = integer.value % 10;
            if (integer.value == 0) {
                output += (String.format ("0\n"));
                break;
            } else if (Math.abs (digit.value) < 10) {
                digit.value = Math.abs (digit.value);
                output += (String.format ("%d\n", digit.value));
            }
            integer.value = integer.value / 10;
            if (Math.abs (integer.value) < 10 && integer.value != 0) {
                output += (String.format ("%d\n", integer.value));
                break;
            }
        }
        output += (String.format ("\nThat's all, have a nice day!\n"));
        if (true)
            return;;
    }
}