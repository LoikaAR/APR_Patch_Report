package aprs_listproc;

// observes aprs_listproc.Main
import java.util.HashMap;
import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.ThreadLocal;
import ch.usi.dag.disl.annotation.SyntheticLocal;
import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.marker.BytecodeMarker;
import ch.usi.dag.disl.marker.BasicBlockMarker;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import ch.usi.dag.disl.staticcontext.BasicBlockStaticContext;
import ch.usi.dag.disl.staticcontext.InstructionStaticContext;

public class Instrumentation {
    @SyntheticLocal
    static long entryTime;

//    @SyntheticLocal
//    static HashMap<String, String> BeforeBodyOutput = new HashMap<String, String>();
//
//    @SyntheticLocal
//    static HashMap<String, Object> AfterBodyOutput = new HashMap<String, Object>();
//
//    @SyntheticLocal
//    static HashMap<String, String> JsonBodyOutput = new HashMap<String, String>();

    @SyntheticLocal
    static long nInvocations = 0;

    @SyntheticLocal
    static long nAllocations = 0;

    @SyntheticLocal
    static long nFieldAccesses = 0;

//    // Before entering the body of main method:
//    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
//    static void onMainEntry(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
//        System.out.format("Method executed:\n %s\n",
//                msc.thisMethodName());
//    }

    // After exiting the body of main method:
    @After(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
    static void onMainExit(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        System.out.println("============================================");
        System.out.println("End of main:");
        System.out.format("#Methods invoked: %d\n#Objects allocated: %d\n#Field accesses: %d\n", nInvocations, nAllocations, nFieldAccesses);
    }

    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.*")
    static void onMethodExit(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        System.out.println("============================================");
        System.out.format("Entering method %s \n>Total # of basic blocks in method: %d\n>Method descriptor: %s\n",
                msc.thisMethodName(),
                bbsc.getCount(),
                msc.thisMethodDescriptor());
    }

    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.*")
    static void afterAllBasicBlocks(BasicBlockStaticContext bbsc) {
        Profiler.incrementBytecodeCounter(bbsc.getSize());
    }


    @After(marker = BytecodeMarker.class,
            args = "invokestatic, invokespecial, invokeinterface, invokevirtual, invokedynamic")
    public static void afterMethodInvocation() {
        nInvocations++;
    }

    @After(marker = BytecodeMarker.class,
            args = "new")
    public static void afterObjectAllocation() {
        nAllocations++;
    }

    @After(marker = BytecodeMarker.class,
            args = "getfield, putfield, getstatic, putstatic")
    public static void afterFieldAccess() {
        nFieldAccesses++;
    }

    // At entering the body of binarySearch method:
    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void onMethodExit(MethodStaticContext msc, BasicBlockStaticContext bbsc, DynamicContext dc) {
        String var_1 = dc.getLocalVariableValue(0, Integer.class).toString();
        String var_2 = dc.getLocalVariableValue(1, Integer.class).toString();
        String var_3 = dc.getLocalVariableValue(2, int.class).toString();

        System.out.println("============================================");
        System.out.println("Variables of method " + msc.thisMethodName() + " before execution:\n"
                + var_1 + "\n" + var_2 + "\n" + var_3);
        System.out.println("#Basic blocks in this method: " + bbsc.getCount());

    }

//    TODO: clean this up. @After was for experiment.


//    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
//    static void afterBinarySearchMethod(DynamicContext dc, MethodStaticContext msc) {
//
//        String var_1 = dc.getLocalVariableValue(3, int.class).toString();
//        System.out.println("VAR AFTER: " + var_1);
//    }




    // After return of binarySearch method:
    @AfterReturning(marker = BodyMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterBinarySearchMethod(DynamicContext dc, MethodStaticContext msc) {

        String var_1 = dc.getLocalVariableValue(0, Integer.class).toString();
        String var_2 = dc.getLocalVariableValue(1, Integer.class).toString();
        String var_3 = dc.getLocalVariableValue(2, int.class).toString();
        System.out.println("============================================");
        System.out.println("Variables of method " + msc.thisMethodName() + " after execution:\n"
                + var_1 + "\n" + var_2 + "\n" + var_3 + "\n"
                + "Output of binarySearch(): "
                + dc.getStackValue(0, Integer.class)
        );
    }
}

    // Before entering the body of binarySearch method:
//    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.binarySearch")
//    static void onMethodEntry(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
//        entryTime = System.nanoTime();
//        System.out.format("Entering method %s\n Total # of basic blocks in method: %d\n",
//                msc.thisMethodName(),
//                bbsc.getCount());
//    }


    // Before entering each basic block of binarySearch method:
//    @Before(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
//    static void onBasicBlockEntry(BasicBlockStaticContext bbsc, DynamicContext dc) {
//        System.out.println("Entering basic block " + bbsc.getIndex() + ":\n"
//                + bbsc.getIndex());
//        System.out.println("Local Variables:");
//
//        String var_1 = dc.getLocalVariableValue(0, Integer.class).toString();
//        String var_2 = dc.getLocalVariableValue(1, Integer.class).toString();
//        String var_3 = dc.getLocalVariableValue(2, int.class).toString();
//
//        System.out.println(var_1);
//        System.out.println(var_2);
//        System.out.println(var_3);
//    }
