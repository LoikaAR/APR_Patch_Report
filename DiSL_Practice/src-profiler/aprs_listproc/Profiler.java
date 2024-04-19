package aprs_listproc;

import java.util.concurrent.atomic.AtomicLong;

public class Profiler {
    private static AtomicLong nBytecodeExecuted = new AtomicLong(0);
    private static AtomicLong nAllocations;

    // this will run at the end of the program
    static {
        nAllocations = new AtomicLong(0);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("#Bytecodes executed: " + nBytecodeExecuted.get());
                System.out.println("#Objects allocated: " + nAllocations.get());
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