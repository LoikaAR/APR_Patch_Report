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
	String name = "grade_af81ffd4_001";
        DoubleObj a = new DoubleObj (), b = new DoubleObj (), c =
            new DoubleObj (), d = new DoubleObj ();
        DoubleObj per = new DoubleObj ();
        output += (String.format ("Enter thresholds for A, B, C, D\n"));
        output += (String.format ("in that order, decreasing percentages > "));
        a.value = scanner.nextDouble ();
        b.value = scanner.nextDouble ();
        c.value = scanner.nextDouble ();
        d.value = scanner.nextDouble ();
        per.value = (a.value + b.value + c.value + d.value) / 4;
        if (per.value < 60) {
            output +=
                (String.format
                 ("Thank you. Now enter student score (percent) >Student has an D grade\n"));
        } else if (per.value >= 60 && per.value < 70) {
            output +=
                (String.format
                 ("Thank you. Now enter student score (percent) >Studnet has an C grade\n"));
        } else if (per.value >= 70 && per.value < 80) {
            output +=
                (String.format
                 ("Thank you. Now enter student score (percent) >Student has an B grade\n"));
        } else if (per.value >= 80) {
            output +=
                (String.format
                 ("Thank you. Now enter student score (percent) >Student ahs an A grade\n"));
        }
        if (true)
            return;;
    }
}
