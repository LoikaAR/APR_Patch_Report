package aprs_introclass;

class IntObj {public int value; public IntObj(){} public IntObj(int i){value = i;}    
	@Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
class FloatObj {public float value; public FloatObj(){} public FloatObj(float i){value = i;}    
	@Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
class LongObj {public long value; public LongObj(){} public LongObj(long i){value = i;}    
	@Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
class DoubleObj {public double value; public DoubleObj(){} public DoubleObj(double i){value = i;}    
	@Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
class CharObj {public char value; public CharObj(){} public CharObj(char i){value = i;}    
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

	public static void main(String[] args)  {
	ClassDef mainClass = new ClassDef();
		String output;
		if(args.length > 0) {
			mainClass.scanner = new java.util.Scanner(args[0]);
		} else {
			mainClass.scanner = new java.util.Scanner(System.in);
		}
		mainClass.exec();
		System.out.println(mainClass.output);
	}

    public void exec ()  {
	String name = "REF_checksum";
        CharObj it = new CharObj();
        LongObj sum = new LongObj();
        IntObj status = new IntObj();
        sum.value = 0;
        output += "Enter an abitrarily long string, ending with carriage return > ";

        it.value = scanner.findInLine(".").charAt(0);
        while(it.value != '\n'){
            sum.value = (sum.value + (long)it.value) % 64;
            try {
	            it.value = scanner.findInLine(".").charAt(0);
	        } catch (java.lang.NullPointerException e) {
	            it.value = '\n';
	        }
        }
        sum.value = sum.value + (long)' ';
        output += String.format("Check sum is %c\n", (char)sum.value);
    }
}
