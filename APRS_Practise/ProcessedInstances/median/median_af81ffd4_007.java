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
	String name = "median_af81ffd4_007";
        DoubleObj first = new DoubleObj (), second = new DoubleObj (), third =
            new DoubleObj ();
	ClassDef mainClass = new ClassDef();
        DoubleObj comp_fir = new DoubleObj (), comp_sec =
            new DoubleObj (), comp_thi = new DoubleObj ();
        output +=
            (String.format ("Please enter 3 numbers separated by spaces > "));
        first.value = scanner.nextDouble ();
        second.value = scanner.nextDouble ();
        third.value = scanner.nextDouble ();
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
	ClassDef mainClass = new ClassDef();
        if (comp_fir.value < comp_sec.value && comp_fir.value < comp_thi.value) {
	ClassDef mainClass = new ClassDef();
        } else if (comp_sec.value < comp_fir.value
                   && comp_sec.value < comp_thi.value) {
	ClassDef mainClass = new ClassDef();
        } else if (comp_thi.value < comp_fir.value
                   && comp_thi.value < comp_sec.value) {
	ClassDef mainClass = new ClassDef();
        }
        if (true)
            return;;
    }
}
