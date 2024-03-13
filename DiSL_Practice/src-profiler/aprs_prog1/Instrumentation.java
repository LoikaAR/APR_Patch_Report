package aprs_prog1;

// observes aprs_prog1.Main

import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.annotation.SyntheticLocal;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class Instrumentation {
    @SyntheticLocal
    static long entryTime;
    @Before(marker = BodyMarker.class, scope = "aprs_prog1.Main.*")
    static void onMethodEntry() {
        entryTime = System.nanoTime();
//        System.out.println("Instrumentation: A new method has been executed.");
//        System.out.println("Method entry " + entryTime);
    }
    @After(marker = BodyMarker.class, scope = "aprs_prog1.Main.*")
    static void onMethodExit(MethodStaticContext msc) {
        System.out.format("Method executed:\n %s \n Duration:\n %dns\n",
                msc.getUniqueInternalName(),
                System.nanoTime() - entryTime);
    }

    @AfterReturning(marker = BodyMarker.class, scope = "aprs_prog1.Main.sum")
    static void afterSumMethod(DynamicContext dc) {
        System.out.format("Instrumentation: Arguments of sum(): \n %d, %d \nResult of sum():\n %d\n",
                dc.getMethodArgumentValue(0, int.class),
                dc.getMethodArgumentValue(1, int.class),
                dc.getStackValue(0, int.class));
    }
}
