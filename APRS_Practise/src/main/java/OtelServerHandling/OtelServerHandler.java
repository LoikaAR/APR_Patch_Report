package OtelServerHandling;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import CollectionsProcessing.ListProcessor;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.Scope;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;

import static CollectionsProcessing.ListProcessor.binarySearch;
import static spark.Spark.*;

public class OtelServerHandler {
    private static final Logger logger = LoggerFactory.getLogger(ListProcessor.class);
    TracerProvider tracerProvider = GlobalOpenTelemetry.get().getTracerProvider();
    private static Tracer tracer;

    OtelServerHandler(OpenTelemetry openTelemetry) {
        tracer = openTelemetry.getTracer(OtelServerHandler.class.getName());
    }

    public static void main(String[] args) {


        port(4318);

        post("/v1/traces", (request, response) -> {
            String body = request.body();
            System.out.println("Received traces: " + body);
            return "Traces received";
        });

        post("/v1/metrics", (request, response) -> {
            String body = request.body();
            System.out.println("Received metrics: " + body);
            return "Metrics received";
        });

        post("/v1/logs", (request, response) -> {
            String body = request.body();
            System.out.println("Received logs: " + body);
            return "Logs received";
        });

        get("/emit", (request, response) -> {
            Span span = tracer.spanBuilder("binarySearch").startSpan();

            ArrayList<Integer> input = new ArrayList<Integer>();
            for (int i = 0; i < 10; i++) {
                input.add(i);
            }
            ListProcessor lp = new ListProcessor(input, OpenTelemetry.noop());
            Integer target = 4;
            int left = 0;
            int right = input.size()-1;
            Integer result = binarySearch(input, target, left, right);


            try (Scope scope = span.makeCurrent()) {
                System.out.println("bin search");
            } catch(Throwable t) {
                span.recordException(t);
                throw t;
            } finally {
                span.end();
            }

            return "Spans emitted";
        });
    }
}

