package CollectionsProcessing;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.api.trace.Tracer;

import java.util.ArrayList;
import java.util.Collections;

import static spark.Spark.*;

public class ListProcessor {
    TracerProvider tracerProvider = GlobalOpenTelemetry.get().getTracerProvider();
    private final Tracer tracer;


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
//        port(3000);


        ArrayList<Integer> input = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            input.add(i);
        }

//        get("/test", (request, response) -> {
//            System.out.println(binarySearch(input, 5, 0, input.size()-1));
//            return "Traces received";
//        });

        String big_bad_string = "HELLO THERE PLEASE NOTICE ME";

        int h = 3;
        int w = 4;

        int p = h+w;

        System.out.println(h + w + p);
//
//        ListProcessor lp = new ListProcessor(input, OpenTelemetry.noop());
//        lp.newMethod(5);
//        System.out.println(lp.tracer);
//
//        Span span = lp.tracer.spanBuilder("binarySearch").startSpan();
//        span.getSpanContext();
//        System.out.println(span);

        System.out.println("main called");
        System.out.println(binarySearch(input, 5, 0, input.size()-1));
    }
}

