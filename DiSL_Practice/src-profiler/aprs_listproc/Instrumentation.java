package aprs_listproc;

// observes aprs_listproc.Main
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
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

    // for variables after the first basic block
    @SyntheticLocal
    static ConcurrentHashMap<String, Object> beforeBodyOutput = new ConcurrentHashMap<String, Object>();

    @SyntheticLocal
    static ConcurrentHashMap<String, Object> afterBodyOutput = new ConcurrentHashMap<String, Object>();

    @SyntheticLocal
    static ConcurrentHashMap<String, Object> varsBeforeBody = new ConcurrentHashMap<String, Object>();

    @SyntheticLocal
    static ConcurrentHashMap<String, Object> varsAfterBody = new ConcurrentHashMap<String, Object>();

    @SyntheticLocal
    static ConcurrentHashMap<String, Object> methodsInvoked = new ConcurrentHashMap<String, Object>();

    @SyntheticLocal
    static ConcurrentHashMap<String, Object> methodInfo = new ConcurrentHashMap<String, Object>();

    @SyntheticLocal
    static ConcurrentHashMap<String, String> jsonBodyOutput = new ConcurrentHashMap<String, String>();

    @SyntheticLocal
    static long nInvocations = 0;

    @SyntheticLocal
    static long nAllocations = 0;

    @SyntheticLocal
    static long nFieldAccesses = 0;

    @SyntheticLocal
    static long nBytecodesExecd = 0;

    // Before entering the body of main method:
    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
    static void onMainEntry(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        System.out.format("Method executed:\n %s\n",
                msc.thisMethodName());
    }

    // After exiting the body of main method:
    @After(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
    static void onMainExit(MethodStaticContext msc, BasicBlockStaticContext bbsc) {

        afterBodyOutput.put("#Objects_allocated", nAllocations);
        afterBodyOutput.put("#Methods_invoked", nInvocations);
        afterBodyOutput.put("#Field_accesses", nFieldAccesses);
        afterBodyOutput.put("Methods_invoked", methodsInvoked);

        System.out.println("============================================");
        System.out.println("End of main:");
        System.out.format("#Methods invoked: %d\n#Objects allocated: %d\n#Field accesses: %d\n",
                nInvocations, nAllocations, nFieldAccesses);
    }

    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.*")
    static void onMethodExit(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        methodInfo.put("descriptor", msc.thisMethodDescriptor());
        methodInfo.put("#Basic_blocks", bbsc.getCount());
        methodsInvoked.put(msc.thisMethodName(), methodInfo);

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
    // this only has access to args
    // if one argument is array, always stores its length at the next index
    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void onBinSearchEntry(MethodStaticContext msc, BasicBlockStaticContext bbsc, DynamicContext dc) {
        String var_0 = dc.getLocalVariableValue(0, Object.class).toString();
        String var_1 = dc.getLocalVariableValue(1, Integer.class).toString();
        String var_2 = dc.getLocalVariableValue(2, int.class).toString();
        int nBasicBlocks = bbsc.getCount();

        beforeBodyOutput.put("#Basic_blocks", nBasicBlocks);

        System.out.println("============================================");
        System.out.println("Variables of method " + msc.thisMethodName() + " before execution:\n"
                + var_0 + "\n" + var_1 + "\n" + var_2);
        System.out.println("#Basic blocks in this method: " + bbsc.getCount());
    }

    // get all variable values after the execution of the first basic block of the instrumented method
    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterBinarySearchBB(DynamicContext dc, MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        if (bbsc.getIndex() == 0) {
            String var_0 = dc.getLocalVariableValue(0, Object.class).toString();
            String var_1 = dc.getLocalVariableValue(1, Integer.class).toString();
            String var_2 = dc.getLocalVariableValue(2, int.class).toString();
            String var_3 = dc.getLocalVariableValue(3, int.class).toString();
            String var_4 = dc.getLocalVariableValue(4, int.class).toString();
            String var_5 = dc.getLocalVariableValue(5, int.class).toString();

            varsBeforeBody.put("var_0", var_0);
            varsBeforeBody.put("var_1", var_1);
            varsBeforeBody.put("var_2", var_2);
            varsBeforeBody.put("var_3", var_3);
            varsBeforeBody.put("var_4", var_4);
            varsBeforeBody.put("var_5", var_5);
            beforeBodyOutput.put("vars", varsBeforeBody);

            System.out.println("VARS : " + var_0 + ", " + var_1 + ", " + var_2 + ", " + var_3 + ", " + var_4 + ", " + var_5);
        }
    }


    // After return of binarySearch method:
    @AfterReturning(marker = BodyMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterBinarySearchMethod(DynamicContext dc, MethodStaticContext msc) {

        String var_0 = dc.getLocalVariableValue(0, Object.class).toString();
        String var_1 = dc.getLocalVariableValue(1, Integer.class).toString();
        String var_2 = dc.getLocalVariableValue(2, int.class).toString();
        String var_3 = dc.getLocalVariableValue(3, int.class).toString();
        String var_4 = dc.getLocalVariableValue(4, int.class).toString();
        String var_5 = dc.getLocalVariableValue(5, int.class).toString();
        String output = dc.getStackValue(0, Integer.class).toString();

        varsAfterBody.put("var_0", var_0);
        varsAfterBody.put("var_1", var_1);
        varsAfterBody.put("var_2", var_2);
        varsAfterBody.put("var_3", var_3);
        varsAfterBody.put("var_4", var_4);
        varsAfterBody.put("var_5", var_5);

        afterBodyOutput.put("vars", varsAfterBody);
        afterBodyOutput.put("output", output);

        System.out.println("============================================");
        System.out.println("Variables of method " + msc.thisMethodName() + " after execution:\n"
                + var_0 + "\n" + var_1 + "\n" + var_2 + "\n" + var_3 + "\n" + var_4 + "\n" + var_5 + "\n"
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
