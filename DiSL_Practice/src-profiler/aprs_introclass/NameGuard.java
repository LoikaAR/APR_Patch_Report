package aprs_introclass;
import ch.usi.dag.disl.annotation.GuardMethod;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;

public class NameGuard {
    @GuardMethod
    public static boolean checkName(DynamicContext dc) {
        return dc.getLocalVariableValue(0, String.class) != null;
    }
}