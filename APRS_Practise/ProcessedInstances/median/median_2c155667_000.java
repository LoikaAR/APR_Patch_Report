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
	String name = "median_2c155667_000";
        IntObj i1 = new IntObj (), i2 = new IntObj (), i3 = new IntObj ();
        output +=
            (String.format ("Please enter 3 numbers separated by spaces > "));
        i1.value = scanner.nextInt ();
        i2.value = scanner.nextInt ();
        i3.value = scanner.nextInt ();
        if ((i1.value >= i2.value && i1.value <= i3.value)
                || (i1.value == i2.value && i1.value == i3.value)
                || (i1.value > i2.value && i1.value < i3.value)) {
            output += (String.format ("%d is the median\n", i1.value));
        } else if ((i2.value >= i1.value && i2.value <= i3.value)
                   || (i2.value == i1.value && i2.value == i3.value)
                   || (i2.value > i1.value && i2.value < i3.value)) {
            output += (String.format ("%d is the median\n", i2.value));
        } else if ((i3.value >= i2.value && i3.value <= i1.value)
                   || (i3.value == i2.value && i3.value == i1.value)
                   || (i3.value > i2.value && i3.value < i1.value)) {
            output += (String.format ("%d is the median\n", i3.value));
        }
        if (true)
            return;;
    }
}
