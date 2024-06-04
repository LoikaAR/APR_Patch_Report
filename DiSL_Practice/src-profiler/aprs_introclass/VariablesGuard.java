package aprs_introclass;
import ch.usi.dag.disl.annotation.GuardMethod;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.staticcontext.BasicBlockStaticContext;


class VariablesGuard {}

class VarGuardOne {
    @GuardMethod
    public static boolean checkVars(BasicBlockStaticContext bbsc) {
        return  bbsc.getIndex() == 0;
    }
}