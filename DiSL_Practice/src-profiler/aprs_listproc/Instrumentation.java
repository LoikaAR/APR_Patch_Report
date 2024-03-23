package aprs_listproc;

// observes aprs_listproc.Main
import java.util.HashMap;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.SyntheticLocal;
import ch.usi.dag.disl.annotation.AfterReturning;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.marker.BasicBlockMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import ch.usi.dag.disl.staticcontext.AbstractStaticContext;
import ch.usi.dag.disl.staticcontext.BasicBlockStaticContext;
import ch.usi.dag.disl.staticcontext.InstructionStaticContext;
public class Instrumentation {
    @SyntheticLocal
    static long entryTime;

    @SyntheticLocal
    static HashMap<Integer, String> BasicBlocks = new HashMap<Integer, String>();
    BasicBlocks.put(1, "assign l = 0");

    @Before(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
    static void onMethodEntry() {
        entryTime = System.nanoTime();
    }

    @After(marker = BodyMarker.class, scope = "aprs_listproc.Main.main")
    static void onMethodExit(MethodStaticContext msc) {
        System.out.format("Method executed:\n %s \n Duration:\n %dns\n",
                msc.getUniqueInternalName(),
                System.nanoTime() - entryTime);
    }

    @Before(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void onBasicBlockEntry(BasicBlockStaticContext bbsc, DynamicContext dc) {
        System.out.println("Entering basic block " + bbsc.getIndex() + ":\n"
                + "> local var 0:\n" + "    name: " + "l"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(0, Integer.class).toString()
                + "\n"
                + "> local var 1:\n"  + "    name: " + "target.length()"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(1, Integer.class).toString()
                + "\n"
                + "> local var 2:\n"  + "    name: " + "target"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(2, int.class).toString());
    }

    @After(marker = BasicBlockMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void onBasicBlockExit(DynamicContext dc, BasicBlockStaticContext bbsc, InstructionStaticContext isc) {
        System.out.println("Exiting basic block " + bbsc.getIndex() + ":\n"
                + "> local var 0:\n" + "    name: " + "l"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(0, Integer.class).toString()
                + "\n"
                + "> local var 1:\n"  + "    name: " + "target.length()"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(1, Integer.class).toString()
                + "\n"
                + "> local var 2:\n"  + "    name: " + "target"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(2, int.class).toString()
                + "\n"
                + "> local var 3:\n"  + "    name: " + "mid?"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(3, int.class).toString()
                + "\n"
                + "> local var 4:\n"  + "    name: " + "r or l (updated)"
                + "\n"
                + "    value: " + dc.getLocalVariableValue(4, int.class).toString()
                + "\n");
    }

    @AfterReturning(marker = BodyMarker.class, scope = "aprs_listproc.Main.binarySearch")
    static void afterSumMethod(DynamicContext dc) {
        System.out.format("Arguments of binarySearch(): \n target: %d\nResult of binarySearch():\n %d\n",
                dc.getMethodArgumentValue(1, int.class),
                dc.getStackValue(0, Integer.class));
    }
}
