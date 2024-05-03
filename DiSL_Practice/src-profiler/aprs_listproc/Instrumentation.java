package aprs_listproc;

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

        Profiler.afterBodyOutput.put("#Objects_allocated", nAllocations);
        Profiler.afterBodyOutput.put("#Methods_invoked", nInvocations);
        Profiler.afterBodyOutput.put("#Field_accesses", nFieldAccesses);
        Profiler.afterBodyOutput.put("Methods_invoked", Profiler.methodsInvoked);

        System.out.println("============================================");
        System.out.println("End of main:");
        System.out.format("#Methods invoked: %d\n#Objects allocated: %d\n#Field accesses: %d\n",
                nInvocations, nAllocations, nFieldAccesses);
    }

    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.*")
    static void onMethodExit(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        String info = "{ \"descriptor\": " + "\"" + msc.thisMethodDescriptor() + "\", \"#Basic_blocks\": " + "\"" + bbsc.getCount() + "\" }";
        Profiler.methodsInvoked.put(msc.thisMethodName(), info);
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

        Profiler.beforeBodyOutput.put("#Basic_blocks", nBasicBlocks);
        System.out.println("============================================");
        System.out.println("Variables of method " + msc.thisMethodName() + " before execution:\n"
                + var_0 + "\n" + var_1 + "\n" + var_2);
    }

    // get all variable values after the execution of the first basic block of the instrumented method
    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterBinarySearchBB(DynamicContext dc, MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        if (bbsc.getIndex() == 0) {
            Profiler.varsBeforeBody.put("var_0", dc.getLocalVariableValue(0, Object.class).toString());
            Profiler.varsBeforeBody.put("var_1", dc.getLocalVariableValue(1, Integer.class).toString());
            Profiler.varsBeforeBody.put("var_2", dc.getLocalVariableValue(2, int.class).toString());
            Profiler.varsBeforeBody.put("var_3", dc.getLocalVariableValue(3, int.class).toString());
            Profiler.varsBeforeBody.put("var_4", dc.getLocalVariableValue(4, int.class).toString());
            Profiler.varsBeforeBody.put("var_5", dc.getLocalVariableValue(5, int.class).toString());
            Profiler.beforeBodyOutput.put("vars", Profiler.varsBeforeBody);
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

        Profiler.varsAfterBody.put("var_0", var_0);
        Profiler.varsAfterBody.put("var_1", var_1);
        Profiler.varsAfterBody.put("var_2", var_2);
        Profiler.varsAfterBody.put("var_3", var_3);
        Profiler.varsAfterBody.put("var_4", var_4);
        Profiler.varsAfterBody.put("var_5", var_5);

        Profiler.afterBodyOutput.put("vars", Profiler.varsAfterBody);
        Profiler.afterBodyOutput.put("output", output);

        System.out.println("============================================");
        System.out.println("Variables of method " + msc.thisMethodName() + " after execution:\n"
                + var_0 + "\n" + var_1 + "\n" + var_2 + "\n" + var_3 + "\n" + var_4 + "\n" + var_5 + "\n"
                + "Output of binarySearch(): "
                + dc.getStackValue(0, Integer.class)
        );
    }
}