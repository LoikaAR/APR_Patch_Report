package CollectionsProcessing;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.api.trace.Tracer;

import java.util.ArrayList;
import java.util.Collections;

public class ListProcessor {
    TracerProvider tracerProvider = GlobalOpenTelemetry.get().getTracerProvider();
    private Tracer tracer;


    ArrayList<Integer> list = new ArrayList<Integer>();

    public ListProcessor(ArrayList<Integer> in, OpenTelemetry openTelemetry) {
        this.list = in;
        Collections.sort(this.list);
        this.tracer = openTelemetry.getTracer(ListProcessor.class.getName());
    }

    /**
     *
     * @param input the input list
     * @param target the target
     * @return the index of target if it exists, -1 otherwise
     */
    public static Integer binarySearch(ArrayList<Integer> input, Integer target, int l, int r) {
        if (r >= l) {
            int midIdx = l + (r-l)/2;
            Integer midElem = input.get(midIdx);
            if (midElem.equals(target)) {
                return midIdx+1;
            }

            if (midElem > target) {
                return binarySearch(input, target, l, midIdx-1);
            } else {
                return binarySearch(input, target,midIdx+1,r);
            }
        }
        return -1;
    }

    public void newMethod(int i) {
        System.out.println("the newMethod called");
        int j = i + 10;
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }

        String big_bad_string = "HELLO THERE PLEASE NOTICE ME";
        big_bad_string = "IM MISTER FROG THIS IS MY SHOW I ATE THE BUG ONMUNMOUM I ATE THE BUG THIS IS THE END I LOVE YOU";
        int h = 3;
        h = 2;
        int w = 4;
        int p = h+w;
        System.out.println(h + w + p);

        System.out.println("main called");
        System.out.println(binarySearch(input, 5, 0, input.size()-1));
    }
}