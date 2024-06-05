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
	return Character.toString(this.value);
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
	String name = "grade_cd2d9b5b_009";
        FloatObj num1 = new FloatObj (), num2 = new FloatObj (), num3 =
            new FloatObj (), num4 = new FloatObj ();
        FloatObj score = new FloatObj ();
        output +=
            (String.format
             ("Enter thresholds for A, B, C, D\nin that order, decreasing percentages > "));
        num1.value = scanner.nextFloat ();
        num2.value = scanner.nextFloat ();
        num3.value = scanner.nextFloat ();
        num4.value = scanner.nextFloat ();
        output +=
            (String.format ("Thank you. Now enter student score (percent) >"));
        score.value = scanner.nextFloat ();
        if (score.value >= num1.value) {
            output += (String.format ("Student has an A grade"));
        } else if (score.value >= num2.value) {
            output += (String.format ("Student has an B grade"));
        } else if (score.value >= num3.value) {
            output += (String.format ("Student has an C grade"));
        } else if (score.value >= num4.value) {
            output += (String.format ("Student has an D grade"));
        }
        output += (String.format ("\n"));
        if (true)
            return;;
    }
}
