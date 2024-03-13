package aprs_prog1;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.After;
//import ch.usi.dag.disl.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;

public class Instrumentation {
    long entryTime;
    @Before(marker = BodyMarker.class)
    static void beforeEveryMethod() {
        System.out.println("Instrumentation: A new method has been executed.");
        System.out.println(DynamicContext.getThis());
        System.out.println("Method entry " + System.nanoTime());
    }

//    @After(marker = BodyMarker.class)
//    static void afterEveryMethod() {
//        System.out.println("Method exit:" + System.nanoTime());
//    }

}
