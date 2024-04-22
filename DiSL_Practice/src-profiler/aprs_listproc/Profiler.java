package aprs_listproc;

import java.util.concurrent.atomic.AtomicLong;
import com.google.gson.Gson;

public class Profiler {
    public static AtomicLong nBytecodeExecuted = new AtomicLong(0);
    private static AtomicLong nAllocations;

    // this will run at the end of the program
    static {
        nAllocations = new AtomicLong(0);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Instrumentation.afterBodyOutput.put("#Bytecodes_executed", nBytecodeExecuted.get());
                System.out.println("#Bytecodes executed: " + nBytecodeExecuted.get());
                JsonProcessor.process(Instrumentation.afterBodyOutput);
            }
        });
    }

    public static void incrementBytecodeCounter(int count) {
        nBytecodeExecuted.addAndGet(count);
    }

    public static void newAllocation() {
        nAllocations.incrementAndGet();
    }
}