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
	String name = "syllables_d5059e2b_000";
        char[] list = new char[21];
        IntObj i = new IntObj (0), counter = new IntObj (0), totChar =
            new IntObj ();
        output += (String.format ("Please enter a string > "));
        list = scanner.next ().toCharArray ();
        totChar.value = list.length;
        while (i.value < totChar.value) {
            if ((list[i.value] == 'i') || (list[i.value] == 'a')
                    || (list[i.value] == 'e') || (list[i.value] == 'o')
                    || (list[i.value] == 'u') || (list[i.value] == 'y')) {
                counter.value = counter.value + 1;
            }
            i.value++;
        }
        output +=
            (String.format ("The number of syllables is %d.\n", counter.value));
        if (true)
            return;;
    }
}
