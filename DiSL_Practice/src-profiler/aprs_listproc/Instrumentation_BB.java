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

public class Instrumentation_BB {
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
        System.out.format("Entering method %s\n",
                msc.thisMethodName());
    }

//    ======================================== Bytecode Execution Tracing ==============================================
    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterAllBasicBlocks(BasicBlockStaticContext bbsc) {
        Profiler_BB.incrementBytecodeCounterTotal(bbsc.getSize());
    }

    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch", guard=BasicBlockGuard.class)
    static void afterGlobalBasicBlocks(BasicBlockStaticContext bbsc) {
        Profiler_BB.incrementBytecodeCounterGlobal(bbsc.getSize());
    }

    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch", guard=BasicBlockGuardInner.class)
    static void afterLocalBasicBlocks(BasicBlockStaticContext bbsc) {
        Profiler_BB.incrementBytecodeCounterLocal(bbsc.getSize());
    }
//    ==================================================================================================================
    @After(marker = BytecodeMarker.class,
            args = "invokestatic, invokespecial, invokeinterface, invokevirtual, invokedynamic")
    public static void afterMethodInvocation() {
        nInvocations++;
    }

    @After(marker = BytecodeMarker.class, args = "new")
    public static void afterObjectAllocation() {
        nAllocations++;
    }

    @After(marker = BytecodeMarker.class, args = "getfield, putfield, getstatic, putstatic")
    public static void afterFieldAccess() {
        nFieldAccesses++;
    }
//    ========================================== Local Variables Tracing ===============================================
    // for basic blocks with global scope
    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch", guard=BasicBlockGuard.class)
    static void afterBinarySearchBB(DynamicContext dc, MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        // TODO: print vars, bytecodes executed
        System.out.println("in basic block " + bbsc.getIndex());
        System.out.println(dc.getLocalVariableValue(0, Object.class).toString());
        System.out.println(dc.getLocalVariableValue(1, Integer.class).toString());
        System.out.println(dc.getLocalVariableValue(2, int.class).toString());
        System.out.println(dc.getLocalVariableValue(3, String.class).toString());
        System.out.println(dc.getLocalVariableValue(4, int.class).toString());
        System.out.println(dc.getLocalVariableValue(5, int.class).toString());
        System.out.println(dc.getLocalVariableValue(6, int.class).toString());
    }

    // get all variable values after the execution of the first basic block of the instrumented method
    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch", guard=BasicBlockGuardInner.class)
    static void afterBinarySearchBBTwo(DynamicContext dc, MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        System.out.println("IN BASIC BLOCK " + bbsc.getIndex());
        System.out.println(dc.getLocalVariableValue(0, Object.class).toString());
        System.out.println(dc.getLocalVariableValue(1, Integer.class).toString());
        System.out.println(dc.getLocalVariableValue(2, int.class).toString());
        System.out.println(dc.getLocalVariableValue(3, String.class).toString());
        System.out.println(dc.getLocalVariableValue(4, int.class).toString());
        System.out.println(dc.getLocalVariableValue(5, int.class).toString());
        System.out.println(dc.getLocalVariableValue(6, int.class).toString());

        System.out.println(dc.getLocalVariableValue(7, int.class).toString());
        System.out.println(dc.getLocalVariableValue(8, String.class).toString());
    }
//    ==================================================================================================================
}

