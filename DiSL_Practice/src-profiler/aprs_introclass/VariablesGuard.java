package aprs_introclass;
import ch.usi.dag.disl.annotation.GuardMethod;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;

public class VariablesGuard {
    @GuardMethod
    public static boolean checkName(DynamicContext dc) {
        return dc.getLocalVariableValue(1, String.class) == Profiler.outName;
    }
}