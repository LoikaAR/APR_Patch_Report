package aprs_listproc_rec;

// observes aprs_listproc_rec.Main

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

    @Before(marker = BodyMarker.class, scope = "aprs_listproc_rec.Main.main")
    static void onMethodEntry() {
        entryTime = System.nanoTime();
    }

    @After(marker = BodyMarker.class, scope = "aprs_listproc_rec.Main.main")
    static void onMethodExit(MethodStaticContext msc) {
        System.out.format("Method executed:\n %s \n Duration:\n %dns\n",
                msc.getUniqueInternalName(),
                System.nanoTime() - entryTime);
    }

    @AfterReturning(marker = BodyMarker.class, scope = "aprs_listproc_rec.Main.binarySearch")
    static void afterSumMethod(DynamicContext dc) {
        System.out.format("Arguments of binarySearch(): \n target: %d\n left: %d\n right: %d \nResult of binarySearch():\n %d\n",
                dc.getMethodArgumentValue(1, Integer.class),
                dc.getMethodArgumentValue(2, int.class),
                dc.getMethodArgumentValue(3, int.class),
                dc.getStackValue(0, Integer.class));

//        System.out.println("getThis():"+dc.getThis().toString());
    }
}
