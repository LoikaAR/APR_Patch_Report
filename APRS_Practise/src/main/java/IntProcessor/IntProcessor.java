package IntProcessor;

public class IntProcessor {
    public void process(int inputA, int inputB, int inputC) {
        if (inputA < inputB && inputA % 3 == 0) {
            inputA += 30;
        }
    }
}
