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
	String name = "median_d120480a_000";
	ClassDef mainClass = new ClassDef();
            new IntObj (), temp = new IntObj ();
	ClassDef mainClass = new ClassDef();
        temp.value = 0;
        output +=
            (String.format ("Please enter 3 numbers separated by spaces > "));
        a.value = scanner.nextInt ();
        b.value = scanner.nextInt ();
        c.value = scanner.nextInt ();
        if (a.value >= b.value) {
            temp.value = b.value;
            b.value = a.value;
            a.value = temp.value;
        }
        if (a.value < c.value) {
	ClassDef mainClass = new ClassDef();
        } else if (b.value > c.value) {
	ClassDef mainClass = new ClassDef();
        } else {
	ClassDef mainClass = new ClassDef();
        }
	ClassDef mainClass = new ClassDef();
        if (true)
            return;;
    }
}
