package aprs_listproc;

import ch.usi.dag.disl.annotation.GuardMethod;
//import ch.usi.dag.disl.guardcontext.*;
import ch.usi.dag.disl.staticcontext.BasicBlockStaticContext;
class BasicBlockGuard {
    @GuardMethod
    static boolean CheckBlock(BasicBlockStaticContext bbsc) {
        return bbsc.getIndex() == 0
                || bbsc.getIndex() == 1
                || bbsc.getIndex() == 2
                || bbsc.getIndex() == 3
                || bbsc.getIndex() == 5
                || bbsc.getIndex() == 7
                || bbsc.getIndex() == 8;
    }
}

class BasicBlockGuardInner {
    // guard to access inner local vars (same scope as "mid")
    @GuardMethod
    static boolean CheckBlock(BasicBlockStaticContext bbsc) {
        return bbsc.getIndex() == 4 || bbsc.getIndex() == 6;
    }

}
