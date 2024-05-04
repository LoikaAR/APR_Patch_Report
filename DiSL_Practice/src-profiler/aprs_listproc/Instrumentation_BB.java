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
    static ConcurrentHashMap<String, String> LocalVars = new ConcurrentHashMap<String, String>();

    @SyntheticLocal
    static long nBytecodesExecd = 0;

    // Before entering the body of main method:
    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
    static void onMainEntry(MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        System.out.format("Entering method %s\n",
                msc.thisMethodName());
    }

    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterAllBasicBlocks(BasicBlockStaticContext bbsc) {
        Profiler_BB.incrementBytecodeCounterTotal(bbsc.getSize());
    }

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
        Profiler_BB.incrementBytecodeCounterGlobal(bbsc.getSize());
        LocalVars.put("var_0", dc.getLocalVariableValue(0, Object.class).toString());
        LocalVars.put("var_1", dc.getLocalVariableValue(1, Integer.class).toString());
        LocalVars.put("var_2", dc.getLocalVariableValue(2, int.class).toString());
        LocalVars.put("var_3", dc.getLocalVariableValue(3, String.class).toString());
        LocalVars.put("var_4", dc.getLocalVariableValue(4, int.class).toString());
        LocalVars.put("var_5", dc.getLocalVariableValue(5, int.class).toString());
        LocalVars.put("var_6", dc.getLocalVariableValue(6, int.class).toString());
        LocalVars.put("var_7", "out_of_scope");
        LocalVars.put("var_8", "out_of_scope");
;
        BB_Entry bbEntry = new BB_Entry();
        bbEntry.setId(bbsc.getIndex());
        bbEntry.setnBytecodes(bbsc.getSize());
        bbEntry.setLocalVars(LocalVars);

        Profiler_BB.BBTrace.add(bbEntry);
    }

    // get all variable values after the execution of the first basic block of the instrumented method
    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch", guard=BasicBlockGuardInner.class)
    static void afterBinarySearchBBTwo(DynamicContext dc, MethodStaticContext msc, BasicBlockStaticContext bbsc) {
        Profiler_BB.incrementBytecodeCounterLocal(bbsc.getSize());

        LocalVars.put("var_0", dc.getLocalVariableValue(0, Object.class).toString());
        LocalVars.put("var_1", dc.getLocalVariableValue(1, Integer.class).toString());
        LocalVars.put("var_2", dc.getLocalVariableValue(2, int.class).toString());
        LocalVars.put("var_3", dc.getLocalVariableValue(3, String.class).toString());
        LocalVars.put("var_4", dc.getLocalVariableValue(4, int.class).toString());
        LocalVars.put("var_5", dc.getLocalVariableValue(5, int.class).toString());
        LocalVars.put("var_6", dc.getLocalVariableValue(6, int.class).toString());
        LocalVars.put("var_7", dc.getLocalVariableValue(7, int.class).toString());
        LocalVars.put("var_8", dc.getLocalVariableValue(8, String.class).toString());

        BB_Entry bbEntry = new BB_Entry();
        bbEntry.setId(bbsc.getIndex());
        bbEntry.setnBytecodes(bbsc.getSize());
        bbEntry.setLocalVars(LocalVars);

        Profiler_BB.BBTrace.add(bbEntry);
    }
//    ==================================================================================================================
}

