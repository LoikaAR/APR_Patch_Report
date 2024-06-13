package aprs_introclass;

class IntObj {
    public int value;
    public IntObj () {
    } public IntObj (int i) {
        value = i;
    }
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}

class FloatObj {
    public float value;
    public FloatObj () {
    } public FloatObj (float i) {
        value = i;
    }
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}

class LongObj {
    public long value;
    public LongObj () {
    } public LongObj (long i) {
        value = i;
    }
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}

class DoubleObj {
    public double value;
    public DoubleObj () {
    } public DoubleObj (double i) {
        value = i;
    }
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}

class CharObj {
    public char value;
    public CharObj () {
    } public CharObj (char i) {
        value = i;
    }
    @Override
    public String toString() {
        if (this.value == 0) {
            return "null";
        } else {
            return Character.toString(this.value);
        }
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
	String name = "smallest_88394fc0_007";
        IntObj a = new IntObj (), b = new IntObj (), c = new IntObj (), d =
            new IntObj ();
        output +=
            (String.format ("Please enter 4 numbers separated by spaces > "));
        a.value = scanner.nextInt ();
        b.value = scanner.nextInt ();
        c.value = scanner.nextInt ();
        d.value = scanner.nextInt ();
        if (a.value < b.value && a.value < c.value && a.value < d.value) {
            output += (String.format ("%d is the smallest number\n", a.value));
        } else if (b.value < a.value && b.value < c.value && b.value < d.value) {
            output += (String.format ("%d is the smallest number\n", b.value));
        } else if (c.value < a.value && c.value < b.value && c.value < d.value) {
            output += (String.format ("%d is the smallest number\n", c.value));
        } else if (d.value < a.value && d.value < b.value && d.value < c.value) {
            output += (String.format ("%d is the smallest number\n", d.value));
        }
        if (true)
            return;;
    }
}
