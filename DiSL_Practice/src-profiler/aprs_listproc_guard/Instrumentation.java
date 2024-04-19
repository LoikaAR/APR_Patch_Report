//package aprs_listproc_guard;
//
//// observes aprs_listproc_gaurd.Main
//import java.util.HashMap;
//import ch.usi.dag.disl.annotation.Before;
//import ch.usi.dag.disl.annotation.After;
//import ch.usi.dag.disl.annotation.SyntheticLocal;
//import ch.usi.dag.disl.annotation.AfterReturning;
//import ch.usi.dag.disl.dynamiccontext.DynamicContext;
//import ch.usi.dag.disl.marker.BodyMarker;
//import ch.usi.dag.disl.marker.BasicBlockMarker;
//import ch.usi.dag.disl.staticcontext.MethodStaticContext;
//import ch.usi.dag.disl.staticcontext.BasicBlockStaticContext;
//import ch.usi.dag.disl.staticcontext.InstructionStaticContext;
//public class Instrumentation {
//    @SyntheticLocal
//    static long entryTime;
//
//    @Before(marker = BodyMarker.class, scope = "aprs_listproc_guard.Main.binarySearch")
//    static void onMethodEntry(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
//        entryTime = System.nanoTime();
//        System.out.format("Entering method %s\n Total # of basic blocks in method: %d\n",
//                msc.thisMethodName(),
//                bbsc.getCount());
//    }
//
//    @After(marker = BodyMarker.class, scope = "aprs_listproc_guard.Main.main")
//    static void onMethodExit(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
//        System.out.format("Method executed:\n %s \n Duration:\n %dns\n Total # of basic blocks in method: %d\n",
//                msc.thisMethodName(),
//                System.nanoTime() - entryTime,
//                bbsc.getCount());
//    }
//
//    @Before(marker = BodyMarker.class, guard = BasicBlockGuard.class)
//    static void before(MethodStaticContext msc) {
//        System.out.format("Instrumentation: static method %s has been executed.\n", msc.getUniqueInternalName());
//    }
//
//    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc_guard.Main.binarySearch")
//    static void onBasicBlockExit(BasicBlockStaticContext bbsc) {
//        System.out.println("Exiting basic block " + bbsc.getIndex());
//    }
//
//    @AfterReturning(marker = BodyMarker.class, scope = "aprs_listproc_guard.Main.binarySearch")
//    static void afterSumMethod(DynamicContext dc) {
//        System.out.format("Arguments of binarySearch(): \n target: %d\nResult of binarySearch():\n %d\n",
//                dc.getMethodArgumentValue(1, int.class),
//                dc.getStackValue(0, Integer.class));
//    }
//}