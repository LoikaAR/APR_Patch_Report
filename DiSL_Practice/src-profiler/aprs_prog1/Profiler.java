package aprs_prog1;

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.marker.BodyMarker;

public class Profiler {
    @Before(marker=BodyMarker.class)
    static void onMethodEntry() {
        System.out.println("Method entry " + System.nanoTime());
    }
    @After(marker=BodyMarker.class)
    static void onMethodExit() {
        System.out.println("Method exit " + System.nanoTime());
    }
}