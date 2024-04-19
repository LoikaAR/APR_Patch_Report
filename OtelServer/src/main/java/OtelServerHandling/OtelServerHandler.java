package OtelServerHandling;

import static spark.Spark.*;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Scope;
import io.opentelemetry.api.OpenTelemetry;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import io.opentelemetry.api.trace.Tracer;
//import org.example.*;
public class OtelServerHandler {
//    private static final Logger logger = LoggerFactory.getLogger(ListProcessor.class);
    private Tracer tracer;


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

//        get("/emit", (request, response) -> {
//
//            return "Spans emitted";
//        });
    }
}

