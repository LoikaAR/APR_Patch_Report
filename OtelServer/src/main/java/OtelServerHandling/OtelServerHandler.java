package OtelServerHandling;
import static spark.Spark.*;

public class OtelServerHandler {

    public static void main(String[] args) {
        port(4318);

        post("/v1/traces", (request, response) -> {
            String body = request.body();
            System.out.println("Received traces: " + body);
            return "Traces received";
        });

        post("/v1/metrics", (request, response) -> {
            String body = request.body();
            System.out.println(body.length());
            System.out.println("Received metrics: " + body);
            return "Metrics received";
        });

        post("/v1/logs", (request, response) -> {
            String body = request.body();
            System.out.println("Received logs: " + body);
            return "Logs received";
        });
    }
}

